package com.example.controller;

import com.example.domain.ParsedCommand;
import com.example.domain.CommandType;
import com.example.service.VoiceCommandService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body; //Bound from HTTP body
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.Instant;

/**
 * VoiceCommandController handles HTTP requests and responses for voice commands in frontend
 * Two Endpoints: processAudio and parseText that calls VoiceCommandService to handle business logic
 * Receives audio byte data from frontend and returns relevant JSON response
 *
 * Architecture & Design Patterns:
 *  - RESTful API, Controller-Service pattern separates logic in overall layered architecture
 *  - Dependency Injection, guard clause pattern, automatic JSON serialistation (Micronaut) from ParsedCommand
 *
 */

@Controller("/api/v1/transcription") //base path can use v2 for other versions
public class VoiceCommandController {

    private static final Logger LOG = LoggerFactory.getLogger(VoiceCommandController.class);

    // DI: Service is injected as a Singleton (so the same instance used for all requests)
    private final VoiceCommandService voiceCommandService;

    // For DI
    public VoiceCommandController(VoiceCommandService voiceCommandService) {
        this.voiceCommandService = voiceCommandService;
    }


    /**
     * processAudio() endpoint takes and validates the raw audio and returns a ParsedCommand JSON response
     * ParsedCommand is returned after calling service and it is serialised to JSON here for the Http response
     * ParsedCommand is a custom object made and JSON response (not from Vosk library)
     * @param audioData - bytes sent periodically for continuous streaming
     * @return ParsedCommand as JSON (with a specific type for valid or NO_SPEECH)
     *
     * Design Pattern: Guard clauses (ensure keep this if modifying)
     * - Validates the audio early as failed checks are returned immediately
     * - Is more readable than prev if/else nested statements
     */
    @Post(value = "/process", consumes = MediaType.APPLICATION_OCTET_STREAM)
    public HttpResponse<ParsedCommand> processAudio(@Body byte[] audioData) {
        // Ensure you use guard clauses for clean code if modifying
        try {
            // Validate input before calling service
            // check empty - could check for min size also
            if (audioData == null || audioData.length == 0) {
                LOG.warn("audioData is empty/null");
                return HttpResponse.badRequest();
            }

            // Check min size for a valid WAV audio (44)
            if (audioData.length < 44) {
                LOG.warn("audioData too small to be valid audio: {} bytes", audioData.length);
                return HttpResponse.badRequest();
            }

            LOG.info("Received audioData: {} bytes", audioData.length);

            // Now process audio
            // Service returns a complete ParsedCommand with all fields populated
            ParsedCommand parsedCommand = voiceCommandService.processAudio(audioData);
            String transcribedText = parsedCommand.getTranscribedText();

            // Check if no speech was detected (empty/too short string)
            if (transcribedText == null || transcribedText.trim().length() < 2) {
                LOG.info("No speech detected in audio byte");

                // Create a NO_SPEECH response
                ParsedCommand noSpeechCommand = new ParsedCommand(
                        CommandType.NO_SPEECH,
                        null,
                        "",
                        0.0,
                        Instant.now()
                );
                return HttpResponse.ok(noSpeechCommand);
            }

            // Only returns ParsedCommand if speech detected
            LOG.info("Speech detected - Type: {}, Target: {}, Transcription: '{}', Confidence: {}, Timestamp: {}",
                    parsedCommand.getType(),
                    parsedCommand.getTarget(),
                    parsedCommand.getTranscribedText(),
                    parsedCommand.getConfidence(),
                    parsedCommand.getTimestamp());

            // Scalability: Check if confidence is too low for future analysis
//                if (parsedCommand.getConfidence() < 0.3) {
//                    LOG.warn("Low confidence transcription ({}): '{}'",
//                            parsedCommand.getConfidence(),
//                            transcribedText);
//                }

            // Return the valid ParsedCommand - Micronaut serialises it to JSON
            return HttpResponse.ok(parsedCommand);


        } catch (IllegalArgumentException e) {
            LOG.error("Invalid audio data : {}", e.getMessage());
            return HttpResponse.badRequest();
        } catch (Exception e) {
            LOG.error("Error processing audio ", e);
            return HttpResponse.serverError();
        }
    }


    /**
     * parseText: parses text into a command without transcription.
     *  CURRENTLY UNUSED! I kept it for future accessibility
     * - Future text-based command input features for keyboard access
     * - Debugging without recording audio
     * - Potential change to a diff controller if text input is added
     */
    @Post(value = "/parse", consumes = MediaType.TEXT_PLAIN)
    public HttpResponse<ParsedCommand> parseText(@Body String text) {
        try {
            if (text == null || text.trim().length() < 2) {
                LOG.info("Empty or too short text provided");

                ParsedCommand noSpeechCommand = new ParsedCommand(
                        CommandType.NO_SPEECH,
                        null,
                        "",
                        0.0,
                        Instant.now()
                );

                return HttpResponse.ok(noSpeechCommand);
            }
            LOG.info("Parsing text : '{}'", text);
            ParsedCommand command = voiceCommandService.parseCommand(text);
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