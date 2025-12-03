package com.example.dto;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

/**
 * CreateDiscussionRequestTest validates the CreateDiscussionRequest DTO.
 * Tests:
 * - Getters and setters for all fields
 * - Null values handling
 * - Empty strings handling
 * - Special characters and long text
 */
class CreateDiscussionRequestTest {

    private CreateDiscussionRequest request;

    @BeforeEach
    void setUp() {
        request = new CreateDiscussionRequest();
    }

    @Test
    void testDefaultConstructor() {
        CreateDiscussionRequest newRequest = new CreateDiscussionRequest();
        assertNotNull(newRequest, "CreateDiscussionRequest should be created");
        assertNull(newRequest.getDiscussionTitle(), "Title should be null initially");
        assertNull(newRequest.getDiscussionText(), "Text should be null initially");
    }

    @Test
    void testSetAndGetDiscussionTitle() {
        String expectedTitle = "How to write unit tests?";
        request.setDiscussionTitle(expectedTitle);
        assertEquals(expectedTitle, request.getDiscussionTitle(), "Title should match set value");
    }

    @Test
    void testSetAndGetDiscussionText() {
        String expectedText = "I need help understanding how to write effective unit tests.";
        request.setDiscussionText(expectedText);
        assertEquals(expectedText, request.getDiscussionText(), "Text should match set value");
    }

    @Test
    void testAllFieldsTogether() {
        // Arrange
        String title = "Best practices for Micronaut";
        String text = "What are the community's recommended best practices?";

        // Act
        request.setDiscussionTitle(title);
        request.setDiscussionText(text);

        // Assert
        assertEquals(title, request.getDiscussionTitle());
        assertEquals(text, request.getDiscussionText());
    }

    @Test
    void testNullValues() {
        // Act - Set to null
        request.setDiscussionTitle(null);
        request.setDiscussionText(null);

        // Assert - Nulls should be accepted (no validation yet)
        assertNull(request.getDiscussionTitle(), "Should accept null title");
        assertNull(request.getDiscussionText(), "Should accept null text");
    }

    @Test
    void testEmptyStrings() {
        // Act
        request.setDiscussionTitle("");
        request.setDiscussionText("");

        // Assert - Empty strings should be accepted (no validation yet)
        assertEquals("", request.getDiscussionTitle(), "Should accept empty title");
        assertEquals("", request.getDiscussionText(), "Should accept empty text");
    }

    @Test
    void testWhitespaceOnlyStrings() {
        // Act
        request.setDiscussionTitle("   ");
        request.setDiscussionText("   ");

        // Assert
        assertEquals("   ", request.getDiscussionTitle(), "Should accept whitespace-only title");
        assertEquals("   ", request.getDiscussionText(), "Should accept whitespace-only text");
    }

    @Test
    void testLongText() {
        String longTitle = "a".repeat(500);
        String longText = "b".repeat(10000);
        request.setDiscussionTitle(longTitle);
        request.setDiscussionText(longText);

        assertEquals(500, request.getDiscussionTitle().length());
        assertEquals(10000, request.getDiscussionText().length());
    }

    @Test
    void testSpecialCharacters() {
        String specialCharsTitle = "Test with special chars: @#$%^&*()_+";
        String specialCharsText = "Text with symbols: <>?{}|~`[];',./";

        request.setDiscussionTitle(specialCharsTitle);
        request.setDiscussionText(specialCharsText);

        assertEquals(specialCharsTitle, request.getDiscussionTitle());
        assertEquals(specialCharsText, request.getDiscussionText());
    }

    @Test
    void testUnicodeCharacters() {
        String unicodeTitle = "Test with émojis 🎉🚀";
        String unicodeText = "Unicode text: 你好世界 مرحبا العالم";

        request.setDiscussionTitle(unicodeTitle);
        request.setDiscussionText(unicodeText);
        assertEquals(unicodeTitle, request.getDiscussionTitle());
        assertEquals(unicodeText, request.getDiscussionText());
    }

    @Test
    void testNewlineCharacters() {
        String titleWithNewline = "Title\nwith newline";
        String textWithNewlines = "Text\nwith\nmultiple\nlines";

        request.setDiscussionTitle(titleWithNewline);
        request.setDiscussionText(textWithNewlines);
        assertEquals(titleWithNewline, request.getDiscussionTitle());
        assertEquals(textWithNewlines, request.getDiscussionText());
    }

    @Test
    void testRealisticDiscussionRequest() {
        String title = "Need help with Micronaut Data queries";
        String text = "I'm trying to create a custom query that joins two tables. " +
                "Has anyone done this before? Any examples would be helpful!";
        request.setDiscussionTitle(title);
        request.setDiscussionText(text);

        assertEquals(title, request.getDiscussionTitle());
        assertEquals(text, request.getDiscussionText());
        assertTrue(request.getDiscussionTitle().length() > 0);
        assertTrue(request.getDiscussionText().length() > 0);
    }
}