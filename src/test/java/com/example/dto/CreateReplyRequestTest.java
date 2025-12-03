package com.example.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * CreateReplyRequestTest validates the CreateReplyRequest DTO class
 * it's similar to the CreateDiscussionRequestTest.
 * Tests:
 * - Getters and setters for all fields
 */

class CreateReplyRequestTest {

    private CreateReplyRequest request;

    @BeforeEach
    void setUp() {
        request = new CreateReplyRequest();
    }

    @Test
    void testReplyTextGetterSetter() {
        request.setReplyText("This is a test reply");

        // assert the getter returns what was set
        assertEquals("This is a test reply", request.getReplyText());
    }

    @Test
    void testNullValues() {
        request.setReplyText(null);

        assertNull(request.getReplyText(), "Should accept null title");
    }

    @Test
    void testEmptyStrings() {
        request.setReplyText("");

        // accepted due to current validation
        assertEquals("", request.getReplyText(), "Should accept empty title");
    }
}
