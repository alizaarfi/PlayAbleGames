package com.example.service;

import com.example.dto.AccessibilityFeedbackDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

class AccessibilityEmailServiceTest {

    private AccessibilityEmailService service;

    @BeforeEach
    void setUp() {
        service = new AccessibilityEmailService();
    }

    @Test
    void testSendFeedbackEmail_WithVisionConcern() {
        AccessibilityFeedbackDTO feedback = new AccessibilityFeedbackDTO();
        feedback.setName("Alice Smith");
        feedback.setEmail("alice@test.com");
        feedback.setConcernType("Vision");
        feedback.setMessage("I have difficulty seeing the text on the homepage.");

        assertDoesNotThrow(() -> service.sendFeedbackEmail(feedback));
    }

    @Test
    void testSendFeedbackEmail_WithHearingConcern() {
        AccessibilityFeedbackDTO feedback = new AccessibilityFeedbackDTO();
        feedback.setName("Bob Jones");
        feedback.setEmail("bob@test.com");
        feedback.setConcernType("Hearing");
        feedback.setMessage("Videos need captions.");

        assertDoesNotThrow(() -> service.sendFeedbackEmail(feedback));
    }

    @Test
    void testSendFeedbackEmail_WithMotorConcern() {
        AccessibilityFeedbackDTO feedback = new AccessibilityFeedbackDTO();
        feedback.setName("Charlie Brown");
        feedback.setEmail("charlie@test.com");
        feedback.setConcernType("Motor");
        feedback.setMessage("Buttons are too small to click easily.");

        assertDoesNotThrow(() -> service.sendFeedbackEmail(feedback));
    }

    @Test
    void testSendFeedbackEmail_WithCognitiveConcern() {
        AccessibilityFeedbackDTO feedback = new AccessibilityFeedbackDTO();
        feedback.setName("Diana Prince");
        feedback.setEmail("diana@test.com");
        feedback.setConcernType("Cognitive");
        feedback.setMessage("The navigation menu is confusing.");

        assertDoesNotThrow(() -> service.sendFeedbackEmail(feedback));
    }

    @Test
    void testSendFeedbackEmail_WithSpeechConcern() {
        AccessibilityFeedbackDTO feedback = new AccessibilityFeedbackDTO();
        feedback.setName("Eve Adams");
        feedback.setEmail("eve@test.com");
        feedback.setConcernType("Speech");
        feedback.setMessage("Need alternatives to voice commands.");

        assertDoesNotThrow(() -> service.sendFeedbackEmail(feedback));
    }

    @Test
    void testSendFeedbackEmail_WithOtherConcern() {
        AccessibilityFeedbackDTO feedback = new AccessibilityFeedbackDTO();
        feedback.setName("Frank Castle");
        feedback.setEmail("frank@test.com");
        feedback.setConcernType("Other");
        feedback.setMessage("General accessibility suggestion.");

        assertDoesNotThrow(() -> service.sendFeedbackEmail(feedback));
    }

    @Test
    void testSendFeedbackEmail_WithLongMessage() {
        AccessibilityFeedbackDTO feedback = new AccessibilityFeedbackDTO();
        feedback.setName("User");
        feedback.setEmail("user@test.com");
        feedback.setConcernType("Vision");
        feedback.setMessage("This is a very long message that contains extensive details " +
                "about various accessibility issues including but not limited to: color contrast " +
                "problems, inadequate font sizes, poor navigation structure, lack of keyboard " +
                "accessibility, missing ARIA labels, confusing form layouts, and several other " +
                "concerns that need comprehensive attention and thoughtful solutions.");

        assertDoesNotThrow(() -> service.sendFeedbackEmail(feedback));
    }

    @Test
    void testSendFeedbackEmail_WithSpecialCharactersInName() {
        AccessibilityFeedbackDTO feedback = new AccessibilityFeedbackDTO();
        feedback.setName("José García-López O'Brien");
        feedback.setEmail("jose@test.com");
        feedback.setConcernType("Vision");
        feedback.setMessage("Test message");

        assertDoesNotThrow(() -> service.sendFeedbackEmail(feedback));
    }

    @Test
    void testSendFeedbackEmail_WithSpecialCharactersInMessage() {
        AccessibilityFeedbackDTO feedback = new AccessibilityFeedbackDTO();
        feedback.setName("User");
        feedback.setEmail("user@test.com");
        feedback.setConcernType("Vision");
        feedback.setMessage("Message with special characters: @#$%^&*()_+-=[]{}|;:',.<>?/`~");

        assertDoesNotThrow(() -> service.sendFeedbackEmail(feedback));
    }

    @Test
    void testSendFeedbackEmail_WithEmailPlusAddressing() {
        AccessibilityFeedbackDTO feedback = new AccessibilityFeedbackDTO();
        feedback.setName("Test User");
        feedback.setEmail("user+accessibility@test.com");
        feedback.setConcernType("Vision");
        feedback.setMessage("Test message");

        assertDoesNotThrow(() -> service.sendFeedbackEmail(feedback));
    }

    @Test
    void testSendFeedbackEmail_WithMultilineMessage() {
        AccessibilityFeedbackDTO feedback = new AccessibilityFeedbackDTO();
        feedback.setName("User");
        feedback.setEmail("user@test.com");
        feedback.setConcernType("Vision");
        feedback.setMessage("Line 1: First issue\nLine 2: Second issue\nLine 3: Third issue");

        assertDoesNotThrow(() -> service.sendFeedbackEmail(feedback));
    }

    @Test
    void testSendFeedbackEmail_DifferentConcernTypes() {
        String[] concernTypes = {"Vision", "Hearing", "Motor", "Cognitive", "Speech", "Other"};

        for (String type : concernTypes) {
            AccessibilityFeedbackDTO feedback = new AccessibilityFeedbackDTO();
            feedback.setName("Test User");
            feedback.setEmail("test@test.com");
            feedback.setConcernType(type);
            feedback.setMessage("Testing " + type + " concern type");

            assertDoesNotThrow(() -> service.sendFeedbackEmail(feedback),
                    "Should handle " + type + " concern type without error");
        }
    }

    @Test
    void testSendFeedbackEmail_WithEmptyStringName() {
        AccessibilityFeedbackDTO feedback = new AccessibilityFeedbackDTO();
        feedback.setName("");
        feedback.setEmail("test@test.com");
        feedback.setConcernType("Vision");
        feedback.setMessage("Test");

        assertDoesNotThrow(() -> service.sendFeedbackEmail(feedback));
    }

    @Test
    void testSendFeedbackEmail_WithWhitespaceInFields() {
        AccessibilityFeedbackDTO feedback = new AccessibilityFeedbackDTO();
        feedback.setName("  User Name  ");
        feedback.setEmail("  user@test.com  ");
        feedback.setConcernType("Vision");
        feedback.setMessage("  Message with spaces  ");

        assertDoesNotThrow(() -> service.sendFeedbackEmail(feedback));
    }

    @Test
    void testSendFeedbackEmail_ValidCompleteSubmission() {
        AccessibilityFeedbackDTO feedback = new AccessibilityFeedbackDTO();
        feedback.setName("Complete User");
        feedback.setEmail("complete@test.com");
        feedback.setConcernType("Cognitive");
        feedback.setMessage("This is a complete and valid feedback submission.");

        assertDoesNotThrow(() -> service.sendFeedbackEmail(feedback),
                "Valid complete submission should not throw exception");
    }
}