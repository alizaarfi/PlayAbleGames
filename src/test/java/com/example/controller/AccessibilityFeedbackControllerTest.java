package com.example.controller;

import com.example.dto.AccessibilityFeedbackDTO;
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

@SuppressWarnings({"rawtypes", "unchecked"})
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

    @Test
    void testSubmitFeedback_WithLongMessage() {
        AccessibilityFeedbackDTO feedback = new AccessibilityFeedbackDTO();
        feedback.setName("Test User");
        feedback.setEmail("test@test.com");
        feedback.setConcernType("Vision");
        feedback.setMessage("This is a very long message that contains extensive details " +
                "about various accessibility issues including but not limited to: color contrast " +
                "problems, inadequate font sizes, poor navigation structure, lack of keyboard " +
                "accessibility, missing ARIA labels, confusing form layouts, and several other " +
                "concerns that need comprehensive attention and thoughtful solutions.");

        HttpResponse<Map> response = client.toBlocking().exchange(
                HttpRequest.POST("/api/accessibility/feedback", feedback),
                Map.class
        );

        assertEquals(HttpStatus.OK, response.getStatus());
        assertEquals("Feedback sent successfully", response.body().get("message"));
    }

    @Test
    void testSubmitFeedback_WithSpecialCharactersInName() {
        AccessibilityFeedbackDTO feedback = new AccessibilityFeedbackDTO();
        feedback.setName("José García-López O'Brien");
        feedback.setEmail("jose@test.com");
        feedback.setConcernType("Vision");
        feedback.setMessage("Test message with special name");

        HttpResponse<Map> response = client.toBlocking().exchange(
                HttpRequest.POST("/api/accessibility/feedback", feedback),
                Map.class
        );

        assertEquals(HttpStatus.OK, response.getStatus());
        assertEquals("Feedback sent successfully", response.body().get("message"));
    }

    @Test
    void testSubmitFeedback_WithSpecialCharactersInMessage() {
        AccessibilityFeedbackDTO feedback = new AccessibilityFeedbackDTO();
        feedback.setName("Test User");
        feedback.setEmail("user@test.com");
        feedback.setConcernType("Vision");
        feedback.setMessage("Message with special characters: @#$%^&*()_+-=[]{}|;:',.<>?/`~");

        HttpResponse<Map> response = client.toBlocking().exchange(
                HttpRequest.POST("/api/accessibility/feedback", feedback),
                Map.class
        );

        assertEquals(HttpStatus.OK, response.getStatus());
        assertEquals("Feedback sent successfully", response.body().get("message"));
    }

    @Test
    void testSubmitFeedback_WithEmailPlusAddressing() {
        AccessibilityFeedbackDTO feedback = new AccessibilityFeedbackDTO();
        feedback.setName("Plus Test User");
        feedback.setEmail("user+accessibility@test.com");
        feedback.setConcernType("Motor");
        feedback.setMessage("Testing email plus addressing");

        HttpResponse<Map> response = client.toBlocking().exchange(
                HttpRequest.POST("/api/accessibility/feedback", feedback),
                Map.class
        );

        assertEquals(HttpStatus.OK, response.getStatus());
        assertEquals("Feedback sent successfully", response.body().get("message"));
    }

    @Test
    void testSubmitFeedback_WithMultilineMessage() {
        AccessibilityFeedbackDTO feedback = new AccessibilityFeedbackDTO();
        feedback.setName("Multiline Tester");
        feedback.setEmail("multiline@test.com");
        feedback.setConcernType("Cognitive");
        feedback.setMessage("Line 1: First issue\nLine 2: Second issue\nLine 3: Third issue");

        HttpResponse<Map> response = client.toBlocking().exchange(
                HttpRequest.POST("/api/accessibility/feedback", feedback),
                Map.class
        );

        assertEquals(HttpStatus.OK, response.getStatus());
        assertEquals("Feedback sent successfully", response.body().get("message"));
    }

    @Test
    void testSubmitFeedback_ResponseContainsMessage() {
        AccessibilityFeedbackDTO feedback = new AccessibilityFeedbackDTO();
        feedback.setName("Response Test");
        feedback.setEmail("response@test.com");
        feedback.setConcernType("Hearing");
        feedback.setMessage("Testing response structure");

        HttpResponse<Map> response = client.toBlocking().exchange(
                HttpRequest.POST("/api/accessibility/feedback", feedback),
                Map.class
        );

        Map body = response.body();
        assertNotNull(body);
        assertTrue(body.containsKey("message"));
        assertEquals("Feedback sent successfully", body.get("message"));
    }

    @Test
    void testSubmitFeedback_WithWhitespaceInFields() {
        AccessibilityFeedbackDTO feedback = new AccessibilityFeedbackDTO();
        feedback.setName("  User Name  ");
        feedback.setEmail("  user@test.com  ");
        feedback.setConcernType("Vision");
        feedback.setMessage("  Message with spaces  ");

        HttpResponse<Map> response = client.toBlocking().exchange(
                HttpRequest.POST("/api/accessibility/feedback", feedback),
                Map.class
        );

        assertEquals(HttpStatus.OK, response.getStatus());
        assertEquals("Feedback sent successfully", response.body().get("message"));
    }

    @Test
    void testSubmitFeedback_ValidCompleteSubmission() {
        AccessibilityFeedbackDTO feedback = new AccessibilityFeedbackDTO();
        feedback.setName("Complete User");
        feedback.setEmail("complete@test.com");
        feedback.setConcernType("Speech");
        feedback.setMessage("This is a complete and valid feedback submission.");

        HttpResponse<Map> response = client.toBlocking().exchange(
                HttpRequest.POST("/api/accessibility/feedback", feedback),
                Map.class
        );

        assertEquals(HttpStatus.OK, response.getStatus());
        assertNotNull(response.body());
        assertEquals("Feedback sent successfully", response.body().get("message"));
    }
}