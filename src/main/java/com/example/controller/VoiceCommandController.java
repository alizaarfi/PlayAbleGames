package com.example.controller;

import com.example.domain.ParsedCommand;
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

            // Get transcribed.text
            ParsedCommand parsedCommand = voiceControlService.
           // Get confidence, target and type, and timestamp then do a log

            // Return the complete VoiceCommand entity with CORS headers
            // or return just parsedcommand
            return parsedCommand;
        } catch (Exception e) {
            LOG.error("Error processing audio", e);
            return HttpResponse.serverError();
        }
    }


    // parseText() takes plain text and returns a parsed command but is
    // for testing, debugging, and when you already have transcribed text

    //Testing! Use this endpoint to verify all command patterns work
    //E.g test for empty string, special characters, very long text
    //Validate that UNKNOWN is returned for unrecognized commands
    @Post(value = "/parse", consumes = MediaType.TEXT_PLAIN)
    public HttpResponse<ParsedCommand> parseText(@Body String text) {
        try {
            LOG.info("Parsing text: '{}'", text);
            ParsedCommand command = voiceControlService.parseCommand(text);
            return HttpResponse.ok(command);

        } catch (Exception e) {
            LOG.error("Error parsing text", e);
            return HttpResponse.serverError();
        }
    }
}