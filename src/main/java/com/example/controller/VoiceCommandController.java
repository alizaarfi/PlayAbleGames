package com.example.controller;

import com.example.domain.ParsedCommand;
import com.example.domain.CommandType;
import com.example.domain.VoiceCommand;
import com.example.service.VoiceControlService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Options;
import io.micronaut.http.annotation.Post;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.Instant;

// (Thin) Controller class for HTTP endpoints for voice control
// Receives HTTP requests from frontend/react and returns JSON responses
// Leaves all business logic to VoiceControlService
// Two endpoints: process and parse

@Controller("/api/v1/transcription") //base path can use v2 for other versions
public class VoiceCommandController {

    private static final Logger LOG = LoggerFactory.getLogger(VoiceCommandController.class);

    // DI: Service is injected as a Singleton (so the same instance used for all requests)
    private final VoiceControlService voiceControlService;

    // For DI
    public VoiceCommandController(VoiceControlService voiceControlService) {
        this.voiceControlService = voiceControlService;
    }


    // processAudio() takes the raw audio and returns a 'parsed command'.
    // ParsedCommand is a custom object made and JSON response (not Vosk)
    @Post(value = "/process", consumes = MediaType.APPLICATION_OCTET_STREAM)
    public HttpResponse<ParsedCommand> processAudio(@Body byte[] audioData) {
        try {
            LOG.info("Received audio data: {} bytes", audioData.length);

            // Service returns a complete ParsedCommand with all fields populated
            ParsedCommand parsedCommand = voiceControlService.processAudio(audioData);

            // get the text
            String transcribedText = parsedCommand.getTranscribedText();

            // Check if no speech was detected (empty or very short text)
            if (transcribedText == null || transcribedText.trim().length() < 2) {
                LOG.info("No speech detected in audio chunk");

                // Create NO_SPEECH response using constructor
                ParsedCommand noSpeechCommand = new ParsedCommand(
                        CommandType.NO_SPEECH,  // Use enum
                        null,                    // target can be null
                        "",                      // empty transcription
                        0.0,                     // confidence as double
                        Instant.now()            // timestamp as Instant
                );

                return HttpResponse.ok(noSpeechCommand);
            }

            // only process commands that are valuid
            // Log the parsed command details only if speech was detected
            LOG.info("Speech detected - Type: {}, Target: {}, Transcription: '{}', Confidence: {}, Timestamp: {}",
                    parsedCommand.getType(),
                    parsedCommand.getTarget(),
                    parsedCommand.getTranscribedText(),
                    parsedCommand.getConfidence(),
                    parsedCommand.getTimestamp());

            // Additional validation: Check if confidence is too low for future analysis
            if (parsedCommand.getConfidence() < 0.3) {
                LOG.warn("Low confidence transcription ({}): '{}'",
                        parsedCommand.getConfidence(),
                        transcribedText);
            }

            // Return the valid ParsedCommand - Micronaut will serialize it to JSON
            return HttpResponse.ok(parsedCommand);

        } catch (IllegalArgumentException e) {
            LOG.error("Invalid audio data: {}", e.getMessage());
            return HttpResponse.badRequest();
        } catch (Exception e) {
            LOG.error("Error processing audio", e);
            return HttpResponse.serverError();
        }
    }

    @Post(value = "/parse", consumes = MediaType.TEXT_PLAIN)
    public HttpResponse<ParsedCommand> parseText(@Body String text) {
        try {
            LOG.info("Parsing text: '{}'", text);

            // Also add speech detection check here for consistency
            if (text == null || text.trim().length() < 2) {
                LOG.info("Empty or too short text provided");

                ParsedCommand noSpeechCommand = new ParsedCommand(
                        CommandType.NO_SPEECH,  // Use enum
                        null,                    // target can be null
                        "",                      // empty transcription
                        0.0,                     // confidence as double
                        Instant.now()            // timestamp as Instant
                );

                return HttpResponse.ok(noSpeechCommand);
            }

            ParsedCommand command = voiceControlService.parseCommand(text);

            LOG.info("Parsed command - Type: {}, Target: '{}'",
                    command.getType(),
                    command.getTarget());

            return HttpResponse.ok(command);

        } catch (Exception e) {
            LOG.error("Error parsing text", e);
            return HttpResponse.serverError();
        }
    }
}