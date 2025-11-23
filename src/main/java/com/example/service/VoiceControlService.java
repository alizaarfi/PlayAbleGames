package com.example.service;

import io.micronaut.context.annotation.Value;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.serde.ObjectMapper;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vosk.Model;
import org.vosk.Recognizer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class VoiceControlService {

    private final ObjectMapper objectMapper;
    private final Recognizer recognizer;
    private final Model model;
    private final String modelPath;
    private final float sampleRate;

    // Command patterns for mapping
    private final Map<Pattern, CommandAction> commandPatterns = new HashMap<>();

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
        Recognizer recognizer = new Recognizer(model, sampleRate);

        // process audio data
        recognizer.acceptWaveForm(audioData, audioData.length);
        String result = recognizer.getFinalResult();

        // Parse JSON result
        JsonNode jsonNode = objectMapper.readTree(result);
        String transcription = jsonNode.get("text").asText();


        return transcription;
    }

    //convert text into a command action - create method

    // initilase mapping to the command patterns
    private void initializeCommandMappings() {

    }
}
