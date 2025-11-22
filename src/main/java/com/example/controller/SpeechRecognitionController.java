package com.example.controller;

import com.example.domain.VoiceCommand;
import com.example.service.SpeechRecognitionService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import io.micronaut.http.multipart.CompletedFileUpload;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Base64;
import java.util.Optional;

@Controller("/api/speech")
public class SpeechRecognitionController {

    private static final Logger LOG = LoggerFactory.getLogger(SpeechRecognitionController.class);

    @Inject
    private SpeechRecognitionService speechService;

    /**
     * Upload WAV file for recognition
     */
    @Post(value = "/recognize", consumes = MediaType.MULTIPART_FORM_DATA)
    public HttpResponse<RecognitionResponse> recognizeFile(
            @Part("audio") CompletedFileUpload audioFile) {

        LOG.info("Received audio file: {}, size: {} bytes",
                audioFile.getFilename(), audioFile.getSize());

        try {
            Optional<VoiceCommand> result = speechService.recognizeFromWav(
                    audioFile.getInputStream());

            return result
                    .map(cmd -> HttpResponse.ok(new RecognitionResponse(
                            cmd.getTranscribedText(),
                            cmd.getConfidence(),
                            true)))
                    .orElse(HttpResponse.ok(new RecognitionResponse(
                            null, 0, false, "No speech detected")));

        } catch (IOException e) {
            LOG.error("Error reading uploaded file", e);
            return HttpResponse.serverError(new RecognitionResponse(
                    null, 0, false, "Error processing file: " + e.getMessage()));
        }
    }

    /**
     * Recognize from base64-encoded audio (useful for web clients)
     */
    @Post(value = "/recognize/base64", consumes = MediaType.APPLICATION_JSON)
    public HttpResponse<RecognitionResponse> recognizeBase64(@Body Base64AudioRequest request) {

        LOG.info("Received base64 audio, length: {}", request.audioData().length());

        try {
            byte[] audioBytes = Base64.getDecoder().decode(request.audioData());
            Optional<VoiceCommand> result = speechService.recognizeFromBytes(audioBytes);

            return result
                    .map(cmd -> HttpResponse.ok(new RecognitionResponse(
                            cmd.getTranscribedText(),
                            cmd.getConfidence(),
                            true)))
                    .orElse(HttpResponse.ok(new RecognitionResponse(
                            null, 0, false, "No speech detected")));

        } catch (IllegalArgumentException e) {
            return HttpResponse.badRequest(new RecognitionResponse(
                    null, 0, false, "Invalid base64 data"));
        }
    }

    /**
     * Recognize with limited grammar/vocabulary
     */
    @Post(value = "/recognize/command", consumes = MediaType.APPLICATION_JSON)
    public HttpResponse<RecognitionResponse> recognizeCommand(@Body CommandRecognitionRequest request) {

        LOG.info("Recognizing command with grammar: {}", String.join(", ", request.commands()));

        try {
            byte[] audioBytes = Base64.getDecoder().decode(request.audioData());
            Optional<VoiceCommand> result = speechService.recognizeWithGrammar(
                    audioBytes, request.commands());

            return result
                    .map(cmd -> HttpResponse.ok(new RecognitionResponse(
                            cmd.getTranscribedText(),
                            cmd.getConfidence(),
                            true)))
                    .orElse(HttpResponse.ok(new RecognitionResponse(
                            null, 0, false, "Command not recognized")));

        } catch (IllegalArgumentException e) {
            return HttpResponse.badRequest(new RecognitionResponse(
                    null, 0, false, "Invalid request data"));
        }
    }

    /**
     * Health check endpoint
     */
    @Get("/health")
    public HttpResponse<HealthResponse> health() {
        boolean ready = speechService.isInitialized();
        return ready
                ? HttpResponse.ok(new HealthResponse("ready", "Vosk model loaded"))
                : HttpResponse.serverError(new HealthResponse("not_ready", "Model not initialized"));
    }

    // Request/Response DTOs
    @Serdeable
    public record Base64AudioRequest(String audioData) {}

    @Serdeable
    public record CommandRecognitionRequest(String audioData, String[] commands) {}

    @Serdeable
    public record RecognitionResponse(
            String text,
            double confidence,
            boolean success,
            String error
    ) {
        public RecognitionResponse(String text, double confidence, boolean success) {
            this(text, confidence, success, null);
        }
    }

    @Serdeable
    public record HealthResponse(String status, String message) {}
}