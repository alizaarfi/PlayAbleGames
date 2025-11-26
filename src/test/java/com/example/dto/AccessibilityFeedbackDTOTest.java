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
}
