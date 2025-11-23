package com.example.service;

import com.example.domain.CommandType;
import com.example.domain.ParsedCommand;
import com.example.domain.VoiceCommand;
import io.micronaut.context.annotation.Value;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.serde.ObjectMapper;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.vosk.Model;
import org.vosk.Recognizer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class VoiceControlService {

    private final ObjectMapper objectMapper;
    private final Recognizer recognizer;
    private final Model model;
    private final String modelPath;
    private final float sampleRate;

    // Command patterns for mapping
    private final Map<Pattern, VoiceCommand> commandPatterns = new HashMap<>();

    // Constructor for dependency injection
    public VoiceControlService(
            ObjectMapper objectMapper,
            @Value("${vosk.model.path:/path/to/vosk-model}") String modelPath,
            @Value("${vosk.sample-rate:16000}") float sampleRate) {
        this.objectMapper = objectMapper;
        this.modelPath = modelPath;
        this.sampleRate = sampleRate;
    }

    @PostConstruct
    public void initialize() {
        try {
            this.model = new Model(modelPath);
            initializeCommandMappings();
        } catch (IOException e) {
            throw new RuntimeException("Failed to initialize voice recognition", e);
        }
    }

    @PreDestroy
    public void cleanup() {
        if (model != null) {
            model.close();
        }
    }

    // Converting audio to text
    @NonNull
    public String convertAudio(@NonNull byte[] audioData) {
        try {
            Recognizer recognizer = new Recognizer(model, sampleRate);

            // process audio data
            recognizer.acceptWaveForm(audioData, audioData.length);
            String result = recognizer.getFinalResult();

            // Parse JSON result
            JsonNode jsonNode = objectMapper.readTree(result);
            String transcription = jsonNode.get("text").asText();


            return transcription;
        } catch (IOException e){
            throw new RuntimeException("Audio transcription failed", e);
        }
    }

    //convert text into a command action - create method
    @NonNull
    public ParsedCommand parseCommand(@NonNull String transcription) {
        String normalized = transcription.toLowerCase().trim();

        // Try to match against known command patterns
        for (Map.Entry<Pattern, ParsedCommand> entry : commandPatterns.entrySet()) {
            Matcher matcher = entry.getKey().matcher(normalized);
            if (matcher.find()) {
                ParsedCommand command = entry.getValue();

                // Extract parameters if any
                if (matcher.groupCount() > 0) {
                    command = command.withTarget(matcher.group(1));
                }

                return command;
            }
        }

        return new ParsedCommand(CommandType.UNKNOWN, transcription);
    }

    /**
     * Full pipeline: transcribe audio and parse command
     *
     * @param audioData PCM audio data
     * @return ParsedCommand ready for execution
     */
    @NonNull
    public ParsedCommand processAudio(@NonNull byte[] audioData) {
        VoiceCommand voiceCommand = transcribeAudio(audioData);
        ParsedCommand parsed = parseCommand(voiceCommand.getTranscribedText());

        // Update domain entity with parsed info
        voiceCommand.setCommand(parsed.getType().name());
        voiceCommand.setCommandTarget(parsed.getTarget());
        voiceCommand.setProcessed(true);

        return parsed;
    }

    // initilase mapping to the command patterns
    private void initializeCommandMappings() {
        // Navigation commands
        commandPatterns.put(
                Pattern.compile("(?:go to|open|navigate to)\\s+(\\w+(?:\\s+\\w+)?)\\s+page"),
                new VoiceCommand(CommandType.NAVIGATE, null)
        );

        commandPatterns.put(
                Pattern.compile("go to accessibility page"),
                new VoiceCommand(CommandType.NAVIGATE, "accessibility")
        );

        commandPatterns.put(
                Pattern.compile("go to home page"),
                new VoiceCommand(CommandType.NAVIGATE, "home")
        );

        commandPatterns.put(
                Pattern.compile("go to settings"),
                new VoiceCommand(CommandType.NAVIGATE, "settings")
        );

        // Action commands
        commandPatterns.put(
                Pattern.compile("click (\\w+(?:\\s+\\w+)?)"),
                new VoiceCommand(CommandType.CLICK, null)
        );

        commandPatterns.put(
                Pattern.compile("submit|send|save"),
                new VoiceCommand(CommandType.SUBMIT, null)
        );

        commandPatterns.put(
                Pattern.compile("cancel|close|back"),
                new VoiceCommand(CommandType.CANCEL, null)
        );

        // Search commands
        commandPatterns.put(
                Pattern.compile("search for (.+)"),
                new VoiceCommand(CommandType.SEARCH, null)
        );

        // Scroll commands
        commandPatterns.put(
                Pattern.compile("scroll (up|down|top|bottom)"),
                new VoiceCommand(CommandType.SCROLL, null)
        );

    }
}
