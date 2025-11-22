package com.example.service;

//
// Speech recogniser
import com.example.domain.VoiceCommand;
import io.micronaut.context.annotation.Value;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vosk.LibVosk;
import org.vosk.LogLevel;
import org.vosk.Model;
import org.vosk.Recognizer;

import javax.sound.sampled.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

@Singleton
public class SpeechRecognitionService {

    private static final Logger LOG = LoggerFactory.getLogger(SpeechRecognitionService.class);

    // Vosk requires 16kHz sample rate for best results
    private static final float SAMPLE_RATE = 16000f;
    private static final int SAMPLE_SIZE_BITS = 16;
    private static final int CHANNELS = 1; // Mono
    private static final int BUFFER_SIZE = 4096;

    @Value("${vosk.model.path:model}")
    private String modelPath;

    @Value("${vosk.log.level:INFO}")
    private String logLevel;

    private Model model;
    private volatile boolean initialized = false;

    @PostConstruct
    public void initialize() {
        try {
            LOG.info("Initializing Vosk speech recognition with model: {}", modelPath);

            // Set Vosk log level
            LibVosk.setLogLevel(parseLogLevel(logLevel));

            // Load the model - this can take a few seconds
            model = new Model(modelPath);
            initialized = true;

            LOG.info("Vosk model loaded successfully");
        } catch (Exception e) {
            LOG.error("Failed to initialize Vosk model: {}", e.getMessage(), e);
            throw new RuntimeException("Could not initialize speech recognition", e);
        }
    }

    @PreDestroy
    public void cleanup() {
        if (model != null) {
            model.close();
            LOG.info("Vosk model closed");
        }
    }

    /**
     * Recognize speech from raw audio bytes (16kHz, 16-bit, mono PCM)
     */
    public Optional<VoiceCommand> recognizeFromBytes(byte[] audioData) {
        if (!initialized) {
            LOG.warn("Vosk model not initialized");
            return Optional.empty();
        }

        try (Recognizer recognizer = new Recognizer(model, SAMPLE_RATE)) {
            // Process audio in chunks
            int offset = 0;
            while (offset < audioData.length) {
                int length = Math.min(BUFFER_SIZE, audioData.length - offset);
                byte[] chunk = new byte[length];
                System.arraycopy(audioData, offset, chunk, 0, length);

                recognizer.acceptWaveForm(chunk, length);
                offset += length;
            }

            // Get final result
            String result = recognizer.getFinalResult();
            return parseResult(result);

        } catch (Exception e) {
            LOG.error("Error during speech recognition: {}", e.getMessage(), e);
            return Optional.empty();
        }
    }

    /**
     * Recognize speech from a WAV file input stream
     */
    public Optional<VoiceCommand> recognizeFromWav(InputStream wavInputStream) {
        if (!initialized) {
            LOG.warn("Vosk model not initialized");
            return Optional.empty();
        }

        try (AudioInputStream ais = AudioSystem.getAudioInputStream(wavInputStream);
             Recognizer recognizer = new Recognizer(model, SAMPLE_RATE)) {

            // Convert to proper format if needed
            AudioInputStream convertedAis = convertToVoskFormat(ais);

            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead;

            while ((bytesRead = convertedAis.read(buffer)) >= 0) {
                if (recognizer.acceptWaveForm(buffer, bytesRead)) {
                    // Partial result available
                    LOG.debug("Partial result: {}", recognizer.getPartialResult());
                }
            }

            String result = recognizer.getFinalResult();
            return parseResult(result);

        } catch (Exception e) {
            LOG.error("Error recognizing WAV file: {}", e.getMessage(), e);
            return Optional.empty();
        }
    }

    /**
     * Recognize speech with a custom grammar (limited vocabulary)
     */
    public Optional<VoiceCommand> recognizeWithGrammar(byte[] audioData, String[] grammar) {
        if (!initialized) {
            LOG.warn("Vosk model not initialized");
            return Optional.empty();
        }

        // Build grammar JSON: ["word1", "word2", "[unk]"]
        StringBuilder grammarJson = new StringBuilder("[");
        for (int i = 0; i < grammar.length; i++) {
            grammarJson.append("\"").append(grammar[i]).append("\"");
            if (i < grammar.length - 1) grammarJson.append(", ");
        }
        grammarJson.append(", \"[unk]\"]"); // [unk] for unknown words

        try (Recognizer recognizer = new Recognizer(model, SAMPLE_RATE, grammarJson.toString())) {
            int offset = 0;
            while (offset < audioData.length) {
                int length = Math.min(BUFFER_SIZE, audioData.length - offset);
                byte[] chunk = new byte[length];
                System.arraycopy(audioData, offset, chunk, 0, length);
                recognizer.acceptWaveForm(chunk, length);
                offset += length;
            }

            return parseResult(recognizer.getFinalResult());

        } catch (Exception e) {
            LOG.error("Error with grammar recognition: {}", e.getMessage(), e);
            return Optional.empty();
        }
    }

    /**
     * Convert audio to Vosk-compatible format (16kHz, 16-bit, mono)
     */
    private AudioInputStream convertToVoskFormat(AudioInputStream sourceAis) {
        AudioFormat sourceFormat = sourceAis.getFormat();

        AudioFormat targetFormat = new AudioFormat(
                AudioFormat.Encoding.PCM_SIGNED,
                SAMPLE_RATE,
                SAMPLE_SIZE_BITS,
                CHANNELS,
                CHANNELS * (SAMPLE_SIZE_BITS / 8),
                SAMPLE_RATE,
                false // little-endian
        );

        if (!sourceFormat.matches(targetFormat)) {
            LOG.debug("Converting audio from {} to {}", sourceFormat, targetFormat);
            return AudioSystem.getAudioInputStream(targetFormat, sourceAis);
        }

        return sourceAis;
    }

    /**
     * Parse Vosk JSON result into VoiceCommand
     */
    private Optional<VoiceCommand> parseResult(String jsonResult) {
        // Vosk returns JSON like: {"text": "hello world"}
        // or with alternatives: {"alternatives": [{"text": "...", "confidence": 0.95}]}

        if (jsonResult == null || jsonResult.isEmpty()) {
            return Optional.empty();
        }

        try {
            // Simple JSON parsing - consider using Jackson for production
            String text = extractTextFromJson(jsonResult);

            if (text == null || text.trim().isEmpty()) {
                return Optional.empty();
            }

            VoiceCommand command = new VoiceCommand(text.trim());

            // Extract confidence if available
            Double confidence = extractConfidenceFromJson(jsonResult);
            if (confidence != null) {
                command.setConfidence(confidence);
            }

            return Optional.of(command);

        } catch (Exception e) {
            LOG.error("Error parsing result JSON: {}", jsonResult, e);
            return Optional.empty();
        }
    }

    private String extractTextFromJson(String json) {
        // Simple extraction - use Jackson in production
        int textStart = json.indexOf("\"text\"");
        if (textStart == -1) return null;

        int colonPos = json.indexOf(":", textStart);
        int firstQuote = json.indexOf("\"", colonPos);
        int secondQuote = json.indexOf("\"", firstQuote + 1);

        if (firstQuote != -1 && secondQuote != -1) {
            return json.substring(firstQuote + 1, secondQuote);
        }
        return null;
    }

    private Double extractConfidenceFromJson(String json) {
        int confStart = json.indexOf("\"confidence\"");
        if (confStart == -1) return null;

        int colonPos = json.indexOf(":", confStart);
        int commaOrBrace = Math.min(
                json.indexOf(",", colonPos) == -1 ? Integer.MAX_VALUE : json.indexOf(",", colonPos),
                json.indexOf("}", colonPos) == -1 ? Integer.MAX_VALUE : json.indexOf("}", colonPos)
        );

        if (colonPos != -1 && commaOrBrace != Integer.MAX_VALUE) {
            try {
                return Double.parseDouble(json.substring(colonPos + 1, commaOrBrace).trim());
            } catch (NumberFormatException e) {
                return null;
            }
        }
        return null;
    }

    private LogLevel parseLogLevel(String level) {
        return switch (level.toUpperCase()) {
            case "DEBUG" -> LogLevel.DEBUG;
            case "INFO" -> LogLevel.INFO;
//            case "WARNING" -> LogLevel.WARNING;
//            case "ERROR" -> LogLevel.ERROR;
            default -> LogLevel.INFO;
        };
    }

    public boolean isInitialized() {
        return initialized;
    }
}