package com.example.controller;

import com.example.domain.ParsedCommand;
import com.example.service.VoiceControlService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;

import static io.micronaut.context.BeanResolutionTraceMode.LOG;

@Controller
public class VoiceCommandController {

    // make this use micronauts DI stuff
    private final VoiceControlService voiceControlService;

    public VoiceControlController(VoiceControlService voiceCommandService) {
        this.voiceControlService = voiceControlService;
    }

    /**
     * Process audio and return parsed command
     * POST /api/voice/process
     * Content-Type: application/octet-stream
     */
    @Post(value = "/process", consumes = MediaType.APPLICATION_OCTET_STREAM)
    public HttpResponse<ParsedCommand> processAudio(@Body byte[] audioData) {
        try {

            ParsedCommand command = voiceControlService.processAudio(audioData);

            return HttpResponse.ok(command);

        } catch (Exception e) {
            return HttpResponse.serverError();
        }
    }

    /**
     * Test endpoint: parse text directly (for testing without audio)
     * POST /api/voice/parse
     * Content-Type: text/plain
     */
    @Post(value = "/parse", consumes = MediaType.TEXT_PLAIN)
    public HttpResponse<ParsedCommand> parseText(@Body String text) {
        try {

            ParsedCommand command = voiceControlService.processAudio(text);

            return HttpResponse.ok(command);

        } catch (Exception e) {

            return HttpResponse.serverError();
        }
    }
}
