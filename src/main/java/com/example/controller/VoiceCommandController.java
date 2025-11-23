package com.example.controller;

import com.example.domain.ParsedCommand;
import com.example.service.VoiceControlService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// Controller class for HTTP endpoints for voice control
// Leaves all business logic to VoiceControlService
// Two parts: process and parse
@Controller()
public class VoiceCommandController {

    private static final Logger LOG = LoggerFactory.getLogger(VoiceCommandController.class);

    private final VoiceControlService voiceControlService;

    public VoiceCommandController(VoiceControlService voiceControlService) {
        this.voiceControlService = voiceControlService;
    }


    @Post(value = "/process", consumes = MediaType.APPLICATION_OCTET_STREAM)
    public HttpResponse<ParsedCommand> processAudio(@Body byte[] audioData) {
        try {
            LOG.info("Received audio data: {} bytes", audioData.length);

            ParsedCommand command = voiceControlService.processAudio(audioData);

            return HttpResponse.ok(command);

        } catch (Exception e) {
            LOG.error("Error processing audio", e);
            return HttpResponse.serverError();
        }
    }

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