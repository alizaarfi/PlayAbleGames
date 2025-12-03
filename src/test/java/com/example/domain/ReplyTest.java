package com.example.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * ReplyTest validates the Reply entity.
 * Tests:
 * - Default constructor
 * - Getters and setters for all fields
 * - Null valuse handling
 * - Empty string handling
 * - Special characters and long text
 * - Transient username field behavior
 */
class ReplyTest {

    private Reply reply;
    private LocalDateTime testDate;

    @BeforeEach
    void setUp() {
        reply = new Reply();
        testDate = LocalDateTime.of(2024, 12, 2, 14, 30);
    }

    @Test
    void testDefaultConstructor() {
        Reply newReply = new Reply();
        assertNotNull(newReply, "Reply object should be created");
        assertNull(newReply.getReplyID(), "ReplyID should be null initially");
        assertNull(newReply.getUserID(), "UserID should be null initially");
        assertNull(newReply.getDiscussionID(), "DiscussionID should be null initially");
        assertNull(newReply.getReplyText(), "ReplyText should be null initially");
        assertNull(newReply.getDateCreated(), "DateCreated should be null initially");
        assertNull(newReply.getUsername(), "Username should be null initially");
    }

    @Test
    void testSetAndGetReplyID() {
        Long expectedID = 1L;
        reply.setReplyID(expectedID);
        assertEquals(expectedID, reply.getReplyID(), "ReplyID should match the set value");
    }

    @Test
    void testSetAndGetUserID() {
        Long expectedUserID = 100L;
        reply.setUserID(expectedUserID);
        assertEquals(expectedUserID, reply.getUserID(), "UserID should match the set value");
    }

    @Test
    void testSetAndGetDiscussionID() {
        Long expectedDiscussionID = 50L;
        reply.setDiscussionID(expectedDiscussionID);
        assertEquals(expectedDiscussionID, reply.getDiscussionID(), "DiscussionID should match the set value");
    }

    @Test
    void testSetAndGetReplyText() {
        String expectedText = "This is a test reply";
        reply.setReplyText(expectedText);
        assertEquals(expectedText, reply.getReplyText(), "ReplyText should match the set value");
    }

    @Test
    void testSetAndGetDateCreated() {
        reply.setDateCreated(testDate);
        assertEquals(testDate, reply.getDateCreated(), "DateCreated should match the set value");
    }

    @Test
    void testSetAndGetUsername() {
        String expectedUsername = "replyUser123";
        reply.setUsername(expectedUsername);
        assertEquals(expectedUsername, reply.getUsername(), "Username should match the set value");
    }

    @Test
    void testAllFieldsTogether() {
        // Arrange
        Long replyID = 10L;
        Long userID = 42L;
        Long discussionID = 5L;
        String text = "Great discussion! I totally agree with your points.";
        String username = "activeUser";
        LocalDateTime dateCreated = LocalDateTime.now();

        // Act
        reply.setReplyID(replyID);
        reply.setUserID(userID);
        reply.setDiscussionID(discussionID);
        reply.setReplyText(text);
        reply.setUsername(username);
        reply.setDateCreated(dateCreated);

        // Assert
        assertEquals(replyID, reply.getReplyID());
        assertEquals(userID, reply.getUserID());
        assertEquals(discussionID, reply.getDiscussionID());
        assertEquals(text, reply.getReplyText());
        assertEquals(username, reply.getUsername());
        assertEquals(dateCreated, reply.getDateCreated());
    }

    @Test
    void testNullValues() {
        // Set some values first
        reply.setReplyID(1L);
        reply.setUserID(10L);
        reply.setReplyText("Text");

        // Set to null
        reply.setReplyID(null);
        reply.setUserID(null);
        reply.setReplyText(null);

        // then assert nulls are accepted
        assertNull(reply.getReplyID());
        assertNull(reply.getUserID());
        assertNull(reply.getReplyText());
    }

    @Test
    void testEmptyStrings() {
        reply.setReplyText("");
        reply.setUsername("");

        assertEquals("", reply.getReplyText());
        assertEquals("", reply.getUsername());
    }

    @Test
    void testLongTextContent() {
        String longText = "a".repeat(5000);
        reply.setReplyText(longText);
        assertEquals(longText, reply.getReplyText(), "Should handle long text content");
        assertEquals(5000, reply.getReplyText().length());
    }

    @Test
    void testSpecialCharactersInText() {
        String specialChars = "Reply with special chars: @#$%^&*()_+{}|:<>?~`-=[]\\;',./";
        reply.setReplyText(specialChars);

        assertEquals(specialChars, reply.getReplyText());
    }

    @Test
    void testUsernameIsTransient() {
        // Username is @Transient, so it won't be persisted to DB
        // This test verifies it works as a regular field in the object
        String username = "transientReplyUser";
        reply.setUsername(username);
        assertEquals(username, reply.getUsername(), "Transient username should still work in memory");
    }

    @Test
    void testRealisticReplyScenario() {
        // Arrange - A realistic reply
        Long discussionID = 15L;
        Long userID = 25L;
        String text = "Thanks for sharing! I found this very helpful for my project.";

        // Act
        reply.setDiscussionID(discussionID);
        reply.setUserID(userID);
        reply.setReplyText(text);
        reply.setDateCreated(LocalDateTime.now());

        // Assert
        assertEquals(discussionID, reply.getDiscussionID());
        assertEquals(userID, reply.getUserID());
        assertEquals(text, reply.getReplyText());
        assertNotNull(reply.getDateCreated());
    }

    @Test
    void testMultilineReplyText() {
        String multilineText = "This is line 1\nThis is line 2\nThis is line 3";
        reply.setReplyText(multilineText);

        assertEquals(multilineText, reply.getReplyText());
        assertTrue(reply.getReplyText().contains("\n"));
    }
}