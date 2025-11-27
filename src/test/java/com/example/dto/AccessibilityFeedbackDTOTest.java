package com.example.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccessibilityFeedbackDTOTest {

    @Test
    void testGettersAndSetters() {
        AccessibilityFeedbackDTO dto = new AccessibilityFeedbackDTO();

        dto.setName("Alice");
        dto.setEmail("alice@example.com");
        dto.setConcernType("Usability");
        dto.setMessage("I had trouble using the signup form.");

        assertEquals("Alice", dto.getName(), "name should match");
        assertEquals("alice@example.com", dto.getEmail(), "email should match");
        assertEquals("Usability", dto.getConcernType(), "concernType should match");
        assertEquals("I had trouble using the signup form.", dto.getMessage(), "message should match");
    }

    @Test
    void testEmptyConstructor() {
        AccessibilityFeedbackDTO dto = new AccessibilityFeedbackDTO();

        assertNotNull(dto, "DTO should not be null");
        assertNull(dto.getName(), "name should be null initially");
        assertNull(dto.getEmail(), "email should be null initially");
        assertNull(dto.getConcernType(), "concernType should be null initially");
        assertNull(dto.getMessage(), "message should be null initially");
    }

    @Test
    void testSetName() {
        AccessibilityFeedbackDTO dto = new AccessibilityFeedbackDTO();
        dto.setName("Bob Johnson");
        assertEquals("Bob Johnson", dto.getName(), "name should be set correctly");
    }

    @Test
    void testSetEmail() {
        AccessibilityFeedbackDTO dto = new AccessibilityFeedbackDTO();
        dto.setEmail("bob.johnson@test.com");
        assertEquals("bob.johnson@test.com", dto.getEmail(), "email should be set correctly");
    }

    @Test
    void testSetConcernType() {
        AccessibilityFeedbackDTO dto = new AccessibilityFeedbackDTO();
        dto.setConcernType("Cognitive");
        assertEquals("Cognitive", dto.getConcernType(), "concernType should be set correctly");
    }

    @Test
    void testSetMessage() {
        AccessibilityFeedbackDTO dto = new AccessibilityFeedbackDTO();
        dto.setMessage("The text is too small to read.");
        assertEquals("The text is too small to read.", dto.getMessage(), "message should be set correctly");
    }

    @Test
    void testSetNameToNull() {
        AccessibilityFeedbackDTO dto = new AccessibilityFeedbackDTO();
        dto.setName("John");
        dto.setName(null);
        assertNull(dto.getName(), "name should be null after setting to null");
    }

    @Test
    void testSetEmailToNull() {
        AccessibilityFeedbackDTO dto = new AccessibilityFeedbackDTO();
        dto.setEmail("test@test.com");
        dto.setEmail(null);
        assertNull(dto.getEmail(), "email should be null after setting to null");
    }

    @Test
    void testSetConcernTypeToNull() {
        AccessibilityFeedbackDTO dto = new AccessibilityFeedbackDTO();
        dto.setConcernType("Vision");
        dto.setConcernType(null);
        assertNull(dto.getConcernType(), "concernType should be null after setting to null");
    }

    @Test
    void testSetMessageToNull() {
        AccessibilityFeedbackDTO dto = new AccessibilityFeedbackDTO();
        dto.setMessage("Some message");
        dto.setMessage(null);
        assertNull(dto.getMessage(), "message should be null after setting to null");
    }

    @Test
    void testAllConcernTypes() {
        String[] concernTypes = {"Vision", "Hearing", "Motor", "Cognitive", "Speech", "Other"};

        for (String type : concernTypes) {
            AccessibilityFeedbackDTO dto = new AccessibilityFeedbackDTO();
            dto.setConcernType(type);
            assertEquals(type, dto.getConcernType(), "concernType should handle " + type);
        }
    }

    @Test
    void testLongMessage() {
        AccessibilityFeedbackDTO dto = new AccessibilityFeedbackDTO();
        String longMessage = "This is a very long message that describes multiple accessibility issues " +
                "including problems with color contrast, font sizes, navigation structure, " +
                "and overall usability concerns that need to be addressed.";
        dto.setMessage(longMessage);
        assertEquals(longMessage, dto.getMessage(), "should handle long messages");
    }

    @Test
    void testEmptyStringValues() {
        AccessibilityFeedbackDTO dto = new AccessibilityFeedbackDTO();
        dto.setName("");
        dto.setEmail("");
        dto.setConcernType("");
        dto.setMessage("");

        assertEquals("", dto.getName(), "name should be empty string");
        assertEquals("", dto.getEmail(), "email should be empty string");
        assertEquals("", dto.getConcernType(), "concernType should be empty string");
        assertEquals("", dto.getMessage(), "message should be empty string");
    }

    @Test
    void testSpecialCharactersInName() {
        AccessibilityFeedbackDTO dto = new AccessibilityFeedbackDTO();
        dto.setName("José García-López");
        assertEquals("José García-López", dto.getName(), "should handle special characters in name");
    }

    @Test
    void testEmailWithPlusSign() {
        AccessibilityFeedbackDTO dto = new AccessibilityFeedbackDTO();
        dto.setEmail("user+test@example.com");
        assertEquals("user+test@example.com", dto.getEmail(), "should handle plus sign in email");
    }

    @Test
    void testMultipleFieldUpdates() {
        AccessibilityFeedbackDTO dto = new AccessibilityFeedbackDTO();

        // First update
        dto.setName("Alice");
        dto.setEmail("alice@test.com");
        assertEquals("Alice", dto.getName());
        assertEquals("alice@test.com", dto.getEmail());

        // Second update
        dto.setName("Bob");
        dto.setEmail("bob@test.com");
        assertEquals("Bob", dto.getName());
        assertEquals("bob@test.com", dto.getEmail());
    }

    @Test
    void testWhitespaceInFields() {
        AccessibilityFeedbackDTO dto = new AccessibilityFeedbackDTO();
        dto.setName("  Alice  ");
        dto.setEmail("  alice@test.com  ");
        dto.setMessage("  Message with spaces  ");

        assertEquals("  Alice  ", dto.getName(), "should preserve whitespace");
        assertEquals("  alice@test.com  ", dto.getEmail(), "should preserve whitespace");
        assertEquals("  Message with spaces  ", dto.getMessage(), "should preserve whitespace");
    }

    @Test
    void testNewlineCharactersInMessage() {
        AccessibilityFeedbackDTO dto = new AccessibilityFeedbackDTO();
        String messageWithNewlines = "Line 1\nLine 2\nLine 3";
        dto.setMessage(messageWithNewlines);
        assertEquals(messageWithNewlines, dto.getMessage(), "should handle newline characters");
    }
}