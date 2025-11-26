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
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
    VoiceControlService holds the business logic for:
    - integrating with the Vosk speech recognition library
    - translating voice commands into actionable instructions for the application.

    Dependencies:
    - ObjectMapper: For parsing JSON responses from Vosk
    - Vosk Model: Pre-trained speech recognition model
    - Configuration: Model path and sample rate injected from application.yml

    Understand the stages here:
     - @PostConstruct loads Vosk model + initialises command patterns
     - At Runtime audio and text commands are processed
     - @PreDestroy cleans up model resources on shutdown
**/

@Singleton //Singleton to ensure only one Vosk model instance
public class VoiceControlService {

    // using logging
    private static final Logger LOG = LoggerFactory.getLogger(VoiceControlService.class);

    private final ObjectMapper objectMapper;
    private Model model;
    private final String modelPath;
    private final float sampleRate;

//    private final Map<Pattern, CommandType> commandPatterns = new HashMap<>();
    private final Map<Pattern, CommandType> commandPatterns = new LinkedHashMap<>();

    /* Constructor injection is preferred over field injection in Micronaut for
    better testability and immutability **/
    public VoiceControlService(
            ObjectMapper objectMapper,
            @Value("${vosk.model.path:/path/to/vosk-model}") String modelPath,
            @Value("${vosk.sample-rate:16000}") float sampleRate) {
        this.objectMapper = objectMapper;
        this.modelPath = modelPath;
        this.sampleRate = sampleRate;
    }

    // Auto called by Micronaut after DI
    // performs expensive one-time initialization that should not happen in constructor.
    @PostConstruct
    public void initialize() {
        try {
            LOG.info("Initializing Vosk model from: {}", modelPath);
            this.model = new Model(modelPath);
            initializeCommandMappings();
            LOG.info("Voice command service initialized successfully");
        } catch (IOException e) {
            LOG.error("Failed to initialize Vosk model", e);
            throw new RuntimeException("Failed to initialize voice recognition", e);
        }
    }

    // Auto called by Micronaut during graceful application shutdown
    // Properly releases the Vosk model from memory to prevent resource leaks.
    @PreDestroy
    public void cleanup() {
        if (model != null) {
            model.close();
            LOG.info("Vosk model closed");
        }
    }

    // Convert audio bytes into a parsed command
    @NonNull
    public ParsedCommand processAudio(@NonNull byte[] audioData) {

        Recognizer recognizer = null;

        try {
            // checking if empty BUT i dont think this should be here
            if (audioData == null || audioData.length == 0) {
                LOG.error("Received empty or null audio data");
                throw new IllegalArgumentException("Audio data cannot be empty");
            }

            LOG.info("Processing audio data: {} bytes", audioData.length);

            // Step 1: Transcribe audio using Vosk
            recognizer = new Recognizer(model, sampleRate);
            recognizer.acceptWaveForm(audioData, audioData.length);
            String result = recognizer.getFinalResult();

            // Parse Vosk JSON response
            JsonNode jsonNode = objectMapper.readTree(result);
            String transcription = jsonNode.get("text").asText();
            double confidence = jsonNode.has("confidence") ?
                    jsonNode.get("confidence").asDouble() : 0.0;

            LOG.debug("Transcribed audio: '{}' (confidence: {})", transcription, confidence);

            // Validate transcription
            if (transcription == null || transcription.trim().isEmpty()) {
                LOG.warn("Empty transcription received from Vosk");
                return new ParsedCommand(CommandType.UNKNOWN, null, "", confidence, Instant.now());
            }

            // Step 2: Parse the transcription to identify command type and target
            ParsedCommand parsedCommand = parseCommand(transcription);

            LOG.info("Successfully processed audio: '{}' -> Type: {}, Target: {}",
                    transcription, parsedCommand.getType(), parsedCommand.getTarget());

            return parsedCommand;

        } catch (IOException e) {
            LOG.error("Error transcribing audio", e);
            throw new RuntimeException("Audio transcription failed", e);
        } finally {
            // Always close the recognizer to prevent resource leaks
            if (recognizer != null) {
                recognizer.close();
                LOG.trace("Recognizer closed");
            }
        }
    }

    // Step 2: Parses the transcribed text into a ParsedCommand Entity
    @NonNull
    public ParsedCommand parseCommand(@NonNull String transcription) {
        // Formatting text before operation
        String normalized = transcription.toLowerCase().trim();

        for (Map.Entry<Pattern, CommandType> entry : commandPatterns.entrySet()) {
            Matcher matcher = entry.getKey().matcher(normalized);
            if (matcher.find()) {
                CommandType commandType = entry.getValue();
                String target = matcher.groupCount() > 0 ? matcher.group(1) : null;

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


//    Sets up the regex patterns that map natural language phrases to structured commands.
//   - forms the vocabulary of voice commands
private void initializeCommandMappings() {
    // Specific pages with capture groups
    commandPatterns.put(
            Pattern.compile("go to (accessibility)(?:\\s+page)?"),
            CommandType.NAVIGATE
    );

    commandPatterns.put(
            Pattern.compile("go to (home)\\s*(?:page)?"),
            CommandType.NAVIGATE
    );

    commandPatterns.put(
            Pattern.compile("go to (settings)"),
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

    LOG.info("Initialized {} command patterns", commandPatterns.size());
}
    }
