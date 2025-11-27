package com.example.controller;

import com.example.dto.AccessibilityFeedbackDTO;
import com.example.service.AccessibilityEmailService;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import io.micronaut.context.annotation.Property;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@MicronautTest
@Property(name = "micronaut.server.port", value = "-1")
class AccessibilityFeedbackControllerTest {

    @Inject
    @Client("/")
    HttpClient client;

    @Test
    void testSubmitFeedback_WithVisionConcern() {
        AccessibilityFeedbackDTO feedback = new AccessibilityFeedbackDTO();
        feedback.setName("Alice Smith");
        feedback.setEmail("alice@test.com");
        feedback.setConcernType("Vision");
        feedback.setMessage("I have difficulty seeing the text on the homepage.");

        HttpResponse<Map> response = client.toBlocking().exchange(
                HttpRequest.POST("/api/accessibility/feedback", feedback),
                Map.class
        );

        assertEquals(HttpStatus.OK, response.getStatus());
        assertNotNull(response.body());
        assertEquals("Feedback sent successfully", response.body().get("message"));
    }

    @Test
    void testSubmitFeedback_WithHearingConcern() {
        AccessibilityFeedbackDTO feedback = new AccessibilityFeedbackDTO();
        feedback.setName("Bob Jones");
        feedback.setEmail("bob@test.com");
        feedback.setConcernType("Hearing");
        feedback.setMessage("Videos need captions.");

        HttpResponse<Map> response = client.toBlocking().exchange(
                HttpRequest.POST("/api/accessibility/feedback", feedback),
                Map.class
        );

        assertEquals(HttpStatus.OK, response.getStatus());
        assertEquals("Feedback sent successfully", response.body().get("message"));
    }

    @Test
    void testSubmitFeedback_WithMotorConcern() {
        AccessibilityFeedbackDTO feedback = new AccessibilityFeedbackDTO();
        feedback.setName("Charlie Brown");
        feedback.setEmail("charlie@test.com");
        feedback.setConcernType("Motor");
        feedback.setMessage("Buttons are too small to click easily.");

        HttpResponse<Map> response = client.toBlocking().exchange(
                HttpRequest.POST("/api/accessibility/feedback", feedback),
                Map.class
        );

        assertEquals(HttpStatus.OK, response.getStatus());
        assertEquals("Feedback sent successfully", response.body().get("message"));
    }

    @Test
    void testSubmitFeedback_WithCognitiveConcern() {
        AccessibilityFeedbackDTO feedback = new AccessibilityFeedbackDTO();
        feedback.setName("Diana Prince");
        feedback.setEmail("diana@test.com");
        feedback.setConcernType("Cognitive");
        feedback.setMessage("The navigation menu is confusing.");

        HttpResponse<Map> response = client.toBlocking().exchange(
                HttpRequest.POST("/api/accessibility/feedback", feedback),
                Map.class
        );

        assertEquals(HttpStatus.OK, response.getStatus());
        assertEquals("Feedback sent successfully", response.body().get("message"));
    }

    @Test
    void testSubmitFeedback_WithSpeechConcern() {
        AccessibilityFeedbackDTO feedback = new AccessibilityFeedbackDTO();
        feedback.setName("Eve Adams");
        feedback.setEmail("eve@test.com");
        feedback.setConcernType("Speech");
        feedback.setMessage("Need alternatives to voice commands.");

        HttpResponse<Map> response = client.toBlocking().exchange(
                HttpRequest.POST("/api/accessibility/feedback", feedback),
                Map.class
        );

        assertEquals(HttpStatus.OK, response.getStatus());
        assertEquals("Feedback sent successfully", response.body().get("message"));
    }

    @Test
    void testSubmitFeedback_WithOtherConcern() {
        AccessibilityFeedbackDTO feedback = new AccessibilityFeedbackDTO();
        feedback.setName("Frank Castle");
        feedback.setEmail("frank@test.com");
        feedback.setConcernType("Other");
        feedback.setMessage("General accessibility suggestion.");

        HttpResponse<Map> response = client.toBlocking().exchange(
                HttpRequest.POST("/api/accessibility/feedback", feedback),
                Map.class
        );

        assertEquals(HttpStatus.OK, response.getStatus());
        assertEquals("Feedback sent successfully", response.body().get("message"));
    }
}