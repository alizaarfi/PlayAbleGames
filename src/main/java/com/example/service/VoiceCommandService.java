package com.example.service;

import com.example.domain.CommandType;
import com.example.domain.ParsedCommand;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micronaut.context.annotation.Value;
import io.micronaut.core.annotation.NonNull;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vosk.Model;
import org.vosk.Recognizer;

import java.io.IOException;
import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
    VoiceCommandService holds the business logic for:
    - integrating with the Vosk speech recognition library
    - translating voice commands into actions for the application.

    Architecture & Design Patterns:
    - Service layer ensures all business logic is separate from HTTP handling
    - Singleton pattern ensures one instance of Vosk model
    - Strategy pattern in commandPatterns maps speech to structured commands
    - Factory Pattern in creating ParsedCommand objects

    Dependencies:
    - ObjectMapper: For parsing JSON responses from Vosk
    - Vosk Model: Pre-trained speech recognition model
    - Configuration: Model path and sample rate injected from application.yml

    To understand the stages here:
     - @PostConstruct loads Vosk model + initialises command patterns
     - At Runtime audio and text commands are processed
     - @PreDestroy cleans up model resources on shutdown

    Scalability:
    - Currently using hard-coded pattern alternatives in initialiseCommandMappings based on testing
    - When scaling site, can download a larger Vosk Model and add complex fuzzy matching to maintain current accuracy
    - All business logic can still remain here

**/

@Singleton //Singleton to ensure only one Vosk model instance
public class VoiceCommandService {

    // Using logging
    private static final Logger LOG = LoggerFactory.getLogger(VoiceCommandService.class);

    private final ObjectMapper objectMapper;
    private Model model;
    private final String modelPath;
    private final float sampleRate;

    // CommandPatterns linked hash map, stores the voice command patterns by type
    // Updated it from a hash map to store more options for each pattern
    private final Map<Pattern, CommandType> commandPatterns = new LinkedHashMap<>();

    /* Constructor injection is preferred over field injection in Micronaut for
    better testability and immutability **/
    public VoiceCommandService(
            ObjectMapper objectMapper,
            @Value("${vosk.model.path:/path/to/vosk-model}") String modelPath,
            @Value("${vosk.sample-rate:16000}") float sampleRate) {
        this.objectMapper = objectMapper;
        this.modelPath = modelPath;
        this.sampleRate = sampleRate;
    }

    // Auto called by Micronaut after DI
    // performs expensive one-time initialisation that should not happen in constructor.

    /**
     * After DI, auto called by Micronaut
     * Not in the constructor as loading the vosk model is expensive and will be even more if using a larger model in future
     * Allows micronaut is catch any initialisation errors separate to others - better error handling
     */
    @PostConstruct
    public void initialise() {
        try {
            LOG.info("Initialising Vosk model from: {}", modelPath);
            this.model = new Model(modelPath);
            initialiseCommandMappings();
            LOG.info("Voice command service initialised successfully");
        } catch (IOException e) {
            LOG.error("Failed to initialise Vosk model", e);
            throw new RuntimeException("Failed to initialise voice recognition", e);
        }
    }

    /**
     * Very important to better handle resources (Vosk)
     * Its model's memory isn't handled by JVM garbage collection so essential to be called automatically
     * Prevents memory leaks
     */
    @PreDestroy
    public void cleanup() {
        if (model != null) {
            model.close();
            LOG.info("Vosk model closed");
        }
    }


    /**
     * processAudio() actually converts the bytes into a ParsedCommand to return to the controller endpoint
     * @param audioData - byte
     * @return ParsedCommand
     *
     * Design Pattern: Template Method
     * - Multiple steps to processing audio but allow for some to be extended when scaling/refactoring
     */
    @NonNull
    public ParsedCommand processAudio(@NonNull byte[] audioData) {

        // Each request has a recognizer instance that is always closed at the end
        Recognizer recognizer = null;

        try {
            LOG.info("Processing audio data: {} bytes", audioData.length);

            // Transcribe audio using Vosk's recognizer
            recognizer = new Recognizer(model, sampleRate);
            recognizer.acceptWaveForm(audioData, audioData.length);
            String result = recognizer.getFinalResult();

            // Parse Vosk JSON response using Jackson
            JsonNode jsonNode = objectMapper.readTree(result);
            String transcription = jsonNode.get("text").asText();
            // confidence is important for future analysis of recognition accuracy
            double confidence = jsonNode.has("confidence") ?
                    jsonNode.get("confidence").asDouble() : 0.0;

            LOG.debug("Transcribed audio: '{}' (confidence: {})", transcription, confidence);

            // Validate transcription is this needed?????
            if (transcription == null || transcription.trim().isEmpty()) {
                LOG.warn("Empty transcription received from Vosk");
                return new ParsedCommand(CommandType.UNKNOWN, null, "", confidence, Instant.now());
            }

            // calls parseCommand to parse the transcription to identify command type and target
            ParsedCommand parsedCommand = parseCommand(transcription);

            LOG.info("Successfully processed audio: '{}' -> Type: {}, Target: {}",
                    transcription, parsedCommand.getType(), parsedCommand.getTarget());

            return parsedCommand;

        } catch (IOException e) {
            LOG.error("Error transcribing audio", e);
            throw new RuntimeException("Audio transcription failed", e);
        } finally {
            // Always close the recognizer
            if (recognizer != null) {
                recognizer.close();
                LOG.trace("Recognizer closed");
            }
        }
    }

    // Step 2: Parses the transcribed text into a ParsedCommand Entity

    /**
     * parseCommand takes the transcription text handles mapping to an equivalent type and target from commandPatterns
     * @param transcription - string from processAudio method
     * @return ParsedCommand (updated with the target and type)
     *
     * Design Pattern: Strategy as it matches patterns to speech (specific ones before generic)
     * Scalability:
     * - Add new patterns in initialiseCommandMappings
     * - Add new fuzzy matching in assignTarget
     * - When dataset increases, can simply add helper functions with the larger Vosk model
     * - Also, can then utilise confidence to enhance recognition
     *
     */
    @NonNull
    public ParsedCommand parseCommand(@NonNull String transcription) {
        // Formatting text before operation
        String normalized = transcription.toLowerCase().trim();

        for (Map.Entry<Pattern, CommandType> entry : commandPatterns.entrySet()) {
            Matcher matcher = entry.getKey().matcher(normalized);
            if (matcher.find()) {
                CommandType commandType = entry.getValue();
                String target = matcher.groupCount() > 0 ? matcher.group(1) : null;

                // based on varied commands
                if (target != null) {
                    target = assignTarget(commandType, normalized, target);
                } else {

                    // For patterns without capture groups (like NAVIGATE alternatives)
                    target = assignTarget(commandType, normalized, null);
                }


                ParsedCommand command = new ParsedCommand(
                        commandType,
                        target,
                        transcription,
                        0.0,  // No confidence available in this method
                        Instant.now()
                );

                LOG.info("Matched command: '{}' -> {}", transcription, command);
                return command;
            }
        }

        LOG.warn("No matching command found for: '{}'", transcription);
        return new ParsedCommand(CommandType.UNKNOWN, null, transcription, 0.0, Instant.now());
    }


    /**
     * assignTarget allows to input common speech recognition errors at this scale
     * Vosk may mishear "accessibility" as "access lousy" so this improves performance without a larger model
     * @param commandType - based on the picked up type
     * @param fullText - the full transcription
     * @param target - the  target
     * @return - a string for the target
     * Scalability:
     *  - Current: Add to the hardcoded alternatives for small Vosk model
     *  - Future: Use Levenshtein distance -> https://www.baeldung.com/java-levenshtein-distance
     */
    @NonNull
    private String assignTarget(CommandType commandType, String fullText, String target) {
        switch (commandType) {
            case NAVIGATE:
                // Check fullText for navigation targets
                if (fullText.contains("access")) return "accessibility";
                if (fullText.contains("home")) return "home";
                if (fullText.contains("setting")) return "settings";
                break;

            case FILTER:
                // Check target for filter keywords
                if (target != null) {
                    if (target.contains("puz")) return "puzzle";
                    if (target.contains("action")) return "action";
                    if (target.contains("adventure")) return "adventure";
                    if (target.contains("strategy") || target.contains("strategic")) return "strategy";
                    if (target.contains("casual")) return "casual";
                }

                // not sure if needed atm
//                if (fullText.contains("puz")) return "puzzle";
//                if (fullText.contains("vision") || fullText.contains("visual")) return "vision support";
//                if (fullText.contains("hearing") || fullText.contains("audio")) return "hearing support";
//                if (fullText.contains("motor") || fullText.contains("movement")) return "motor support";
//                if (fullText.contains("cognitive") || fullText.contains("mental")) return "cognitive support";
//                break;

            case SORT:
                // Check target for sort keywords
                if (target != null) {
                    if (target.contains("rat")) return "rating";
                    if (target.contains("alpha") || target.contains("abc")) return "alphabetical";
                    if (target.contains("review")) return target; // "most reviewed", "least reviewed"
                }
                break;

            case FAVOURITE:
                // Check both target and fullText for game names
                String text = (target != null ? target : fullText).toLowerCase();
                if (text.contains("minecraft") || text.contains("mine craft")) return "minecraft";
                if (text.contains("monument")) return "monument valley";
                if (text.contains("among") || text.contains("amongst")) return "among us";
                if (text.contains("candy")) return "candy crush saga";
                if (text.contains("call of") || text.contains("cod")) return "call of duty";
                if (text.contains("pokemon") || text.contains("poke mon")) return "pokemon go";
                if (text.contains("subway")) return "subway surfers";
                if (text.contains("clash")) return "clash of clans";
                if (text.contains("jigsaw")) return "jigsawscapes";
                if (text.contains("oasis")) return "my oasis";
                if (text.contains("alto")) return "altos odyssey";
                if (text.contains("kami")) return "kami 2";
                if (text.contains("sky") || text.contains("children")) return "sky children of light";
                if (text.contains("star") || text.contains("valley")) return "stardew valley";
                if (text.contains("blind") || text.contains("legend")) return "a blind legend";
                break;

            case REVIEW_RATING:
                // Extract category and rating from the matched pattern
                // Pattern captures: "rate hearing 5" -> groups (hearing, 5)
                // We need to return: "hearing:5"
                Pattern ratingPattern = Pattern.compile("(hearing|motor|cognitive|vision|speech|gameplay)\\s+(\\d)");
                Matcher m = ratingPattern.matcher(fullText);
                if (m.find()) {
                    String category = m.group(1);
                    String rating = m.group(2);

                    // Validate rating is 1-5
                    int ratingValue = Integer.parseInt(rating);
                    if (ratingValue >= 1 && ratingValue <= 5) {
                        return category + ":" + rating;  // Returns "hearing:5"
                    }
                }

                // If pattern doesn't match or rating invalid, log warning
                LOG.warn("Could not parse rating from: '{}'", fullText);
                return target != null ? target : fullText;

            case REVIEW_START:
                return "start";

            case REVIEW_SUBMIT:
                return "submit";

            case REVIEW_CANCEL:
                return "cancel";

            case REVIEW_COMMENT:
                return target != null ? target.trim() : "activate_text_mode";
        }


        return target != null ? target : fullText;
    }


    /**
     * initialiseCommandMappings maps the set/equivalent spoken phrases to actions within the app
     * has  specific patterns as priority
     * Uses regex patterns for different options for each pattern
     * Editing this:
     * - Add common misheard phrases that you identify from logs
     * - Keep patterns in priority order from specific to generic command types
     */
    private void initialiseCommandMappings() {
        // Specific pages with capture groups

        // REVIEW COMMANDS moved to  top

        // starts the review process
        commandPatterns.put(
                Pattern.compile("(?:write|leave|create|start)\\s+(?:a\\s+)?review"),
                CommandType.REVIEW_START
        );

        // for each rating
        commandPatterns.put(
                Pattern.compile("(?:rate|rating)?\\s*(hearing|motor|cognitive|vision|speech|gameplay)\\s+(?:rating\\s+)?(\\d)"),
                CommandType.REVIEW_RATING
        );

        // review comment for comment box
        commandPatterns.put(
                Pattern.compile("(?:review\\s+text|comment|review\\s+comment)\\s+(.+)"),
                CommandType.REVIEW_COMMENT
        );


        commandPatterns.put(
                Pattern.compile("submit\\s+(?:review|my\\s+review)?"),
                CommandType.REVIEW_SUBMIT
        );

        commandPatterns.put(
                Pattern.compile("(?:cancel|stop|exit)\\s+review"),
                CommandType.REVIEW_CANCEL
        );

        commandPatterns.put(
                Pattern.compile("(?:go to |open |navigate to )?(?:accessibility|access ability|access lousy|access in|accessability)(?:\\s+page)?"),
                CommandType.NAVIGATE
        );

        commandPatterns.put(
                Pattern.compile("(?:go to |open |navigate to )?(?:home|homepage)(?:\\s+page)?"),
                CommandType.NAVIGATE
        );

        commandPatterns.put(
                Pattern.compile("(?:go to |open |navigate to )?settings"),
                CommandType.NAVIGATE
        );

        // Add after the existing settings pattern:

        commandPatterns.put(
                Pattern.compile("(?:go to |open |navigate to )?(?:community|communities)(?:\\s+page)?"),
                CommandType.NAVIGATE
        );

        commandPatterns.put(
                Pattern.compile("(?:go to |open |navigate to )?(?:favou?rites?|favou?rite)(?:\\s+page)?"),
                CommandType.NAVIGATE
        );

        commandPatterns.put(
                Pattern.compile("(?:go to |open |navigate to )?(?:my account|account|profile)(?:\\s+page)?"),
                CommandType.NAVIGATE
        );

    // For individual game pages - add this AFTER specific patterns but BEFORE the generic navigate pattern
        commandPatterns.put(
                Pattern.compile("(?:go to |open |navigate to )?(.+?)\\s+(?:game\\s+)?page"),
                CommandType.NAVIGATE
        );

        // Generic navigation with capture group
        commandPatterns.put(
                Pattern.compile("(?:go to|open|navigate to)\\s+(\\w+(?:\\s+\\w+)?)\\s*(?:page)?"),
                CommandType.NAVIGATE
        );

        commandPatterns.put(
                Pattern.compile("click\\s+(\\w+(?:\\s+\\w+)?)"),
                CommandType.CLICK
        );

        commandPatterns.put(
                Pattern.compile("search\\s+(?:for\\s+)?(.+)"),
                CommandType.SEARCH
        );

        commandPatterns.put(
                Pattern.compile("scroll\\s+(up|down|top|bottom)"),
                CommandType.SCROLL
        );
        commandPatterns.put(
                Pattern.compile("favou?rite\\s+(.+)"),  // Captures everything after "favourite"
                CommandType.FAVOURITE
        );
        // FILTER commands - "filter by [accessibility/genre]"
        commandPatterns.put(
                Pattern.compile("filter(?:\\s+by)?\\s+(.+)"),
                CommandType.FILTER
        );

    // SORT commands - "sort by [option]"
        commandPatterns.put(
                Pattern.compile("sort(?:\\s+by)?\\s+(.+)"),
                CommandType.SORT
        );



        LOG.info("Initialised {} command patterns", commandPatterns.size());
    }
    }
