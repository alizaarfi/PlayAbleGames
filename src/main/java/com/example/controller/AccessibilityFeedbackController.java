package com.example.controller;

import com.example.dto.AccessibilityFeedbackDTO;
import com.example.service.AccessibilityEmailService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import java.util.Map;

@Controller("/api/accessibility")
public class AccessibilityFeedbackController {

    private final AccessibilityEmailService emailService;

    public AccessibilityFeedbackController(AccessibilityEmailService emailService) {
        this.emailService = emailService;
    }

    @Post("/feedback")
    public HttpResponse<Map<String, String>> submitFeedback(@Body AccessibilityFeedbackDTO feedback) {
        try {
            emailService.sendFeedbackEmail(feedback);
            return HttpResponse.ok(Map.of("message", "Feedback sent successfully"));
        } catch (Exception e) {
            return HttpResponse.serverError(Map.of("message", "Failed to send feedback"));
        }
    }
}