package com.example.service;

import com.example.domain.CommandType;
import com.example.domain.ParsedCommand;
import com.example.domain.VoiceCommand;
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
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



// Purpose: Business logic for for integrating with the Vosk speech recognition library
//  and translating voice commands into actionable instructions for the application.

//3 main tasks: speech-to-text transcription using Vosk library, natural language command
// parsing using regex pattern matching and command mapping to application-specific actions

//Lifecycle:
// @PostConstruct: Loads Vosk model and initializes command patterns on startup
// Runtime: Processes audio and text commands
// @PreDestroy: Cleans up model resources on shutdown

//Dependencies:
// ObjectMapper: For parsing JSON responses from Vosk
// Vosk Model: Pre-trained speech recognition model
// Configuration: Model path and sample rate injected from application.yml


@Singleton //Singleton to ensure only one Vosk model instance
public class VoiceControlService {

    // using logging
    private static final Logger LOG = LoggerFactory.getLogger(VoiceControlService.class);

    private final ObjectMapper objectMapper;
    private Model model;
    private final String modelPath;
    private final float sampleRate;

    private final Map<Pattern, ParsedCommand> commandPatterns = new HashMap<>();

    /** Constructor injection is preferred over field injection in Micronaut for
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

    //Converts the raw audio chunks into a VoiceCommand domain entity with transcribed text.
    @NonNull
    public VoiceCommand transcribeAudio(@NonNull byte[] audioData) {
        try (Recognizer recognizer = new Recognizer(model, sampleRate)) {

            recognizer.acceptWaveForm(audioData, audioData.length);
            String result = recognizer.getFinalResult();

            JsonNode jsonNode = objectMapper.readTree(result);
            String transcription = jsonNode.get("text").asText();
            double confidence = jsonNode.has("confidence") ?
                    jsonNode.get("confidence").asDouble() : 0.0;

            LOG.debug("Transcribed audio: '{}' (confidence: {})", transcription, confidence);

            VoiceCommand voiceCommand = new VoiceCommand(transcription);
            voiceCommand.setConfidence(confidence);

            return voiceCommand;

        } catch (IOException e) {
            LOG.error("Error transcribing audio", e);
            throw new RuntimeException("Audio transcription failed", e);
        }
    }

    // converts the transcribed text into a parsed command
    @NonNull
    public ParsedCommand parseCommand(@NonNull String transcription) {
        String normalized = transcription.toLowerCase().trim();

        for (Map.Entry<Pattern, ParsedCommand> entry : commandPatterns.entrySet()) {
            Matcher matcher = entry.getKey().matcher(normalized);
            if (matcher.find()) {
                ParsedCommand command = entry.getValue();

                if (matcher.groupCount() > 0) {
                    command = command.withTarget(matcher.group(1));
                }

                LOG.info("Matched command: '{}' -> {}", transcription, command);
                return command;
            }
        }

        LOG.warn("No matching command found for: '{}'", transcription);
        return new ParsedCommand(CommandType.UNKNOWN, transcription);
    }


    // Accepts raw audio bytes and returns a parsed command
    @NonNull
    public ParsedCommand processAudio(@NonNull byte[] audioData) {
        VoiceCommand voiceCommand = transcribeAudio(audioData);
        ParsedCommand parsed = parseCommand(voiceCommand.getTranscribedText());

        voiceCommand.setCommand(parsed.getType().name());
        voiceCommand.setCommandTarget(parsed.getTarget());
        voiceCommand.setProcessed(true);

        LOG.info("Processed audio: {} -> {}", voiceCommand.getTranscribedText(), parsed);

        return parsed;
    }

//    Sets up the regex patterns that map natural language phrases to structured commands.
//   - forms the vocabulary of voice commands

    private void initializeCommandMappings() {
        commandPatterns.put(
                Pattern.compile("go to accessibility page"),
                new ParsedCommand(CommandType.NAVIGATE, "accessibility")
        );

        commandPatterns.put(
                Pattern.compile("go to home\\s*(?:page)?"),
                new ParsedCommand(CommandType.NAVIGATE, "home")
        );

        commandPatterns.put(
                Pattern.compile("go to settings"),
                new ParsedCommand(CommandType.NAVIGATE, "settings")
        );

        commandPatterns.put(
                Pattern.compile("(?:go to|open|navigate to)\\s+(\\w+(?:\\s+\\w+)?)\\s*(?:page)?"),
                new ParsedCommand(CommandType.NAVIGATE, null)
        );

        commandPatterns.put(
                Pattern.compile("click\\s+(\\w+(?:\\s+\\w+)?)"),
                new ParsedCommand(CommandType.CLICK, null)
        );

        commandPatterns.put(
                Pattern.compile("(?:submit|send|save)"),
                new ParsedCommand(CommandType.SUBMIT, null)
        );

        commandPatterns.put(
                Pattern.compile("(?:cancel|close|back)"),
                new ParsedCommand(CommandType.CANCEL, null)
        );

        commandPatterns.put(
                Pattern.compile("search\\s+(?:for\\s+)?(.+)"),
                new ParsedCommand(CommandType.SEARCH, null)
        );

        commandPatterns.put(
                Pattern.compile("scroll\\s+(up|down|top|bottom)"),
                new ParsedCommand(CommandType.SCROLL, null)
        );

        LOG.info("Initialized {} command patterns", commandPatterns.size());
    }

//    public void addCommandPattern(String regex, ParsedCommand command) {
//        commandPatterns.put(Pattern.compile(regex), command);
//        LOG.debug("Added custom command pattern: {}", regex);
    }
