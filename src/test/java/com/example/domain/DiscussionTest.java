package com.example.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;


/**
 * DiscussionTest tests the Discussion entity's behaviour, getters, setters
 * Tests: the constructor, fields, handling for null strings, empty strings, long strings, special characters and for the username field behaviour
 * Keeps with Arrange, Act, Assert pattern/structure to testing.
 */
public class DiscussionTest {

    private Discussion discussion;
    private LocalDateTime testDate;

    @BeforeEach
    void setUp() {
        discussion = new Discussion();
        testDate = LocalDateTime.of(2024, 12, 1, 10, 30);
    }

    @Test
    void testDefaultConstructor() {
        Discussion newDiscussion = new Discussion();
        assertNotNull(newDiscussion, "Discussion object should be created");
        assertNull(newDiscussion.getDiscussionID(), "DiscussionID should be null initially");
        assertNull(newDiscussion.getUserID(), "UserID should be null initially");
        assertNull(newDiscussion.getDiscussionTitle(), "DiscussionTitle should be null initially");
        assertNull(newDiscussion.getDateCreated(), "DateCreated should be null initially");
        assertNull(newDiscussion.getDiscussionText(), "DiscussionText should be null initially");
        assertNull(newDiscussion.getUsername(), "Username should be null initially");
    }

    @Test
    void testSetAndGetDiscussionID() {
        Long expectedID = 1L;
        discussion.setDiscussionID(expectedID);
        assertEquals(expectedID, discussion.getDiscussionID(), "DiscussionID should match the set value");
    }

    @Test
    void testSetAndGetUserID() {
        Long expectedUserID = 100L;
        discussion.setUserID(expectedUserID);
        assertEquals(expectedUserID, discussion.getUserID(), "UserID should match the set value");
    }

    @Test
    void testSetAndGetDiscussionTitle() {
        String expectedTitle = "How to test Micronaut applications?";
        discussion.setDiscussionTitle(expectedTitle);
        assertEquals(expectedTitle, discussion.getDiscussionTitle(), "DiscussionTitle should match the set value");
    }

    @Test
    void testSetAndGetDateCreated() {
        discussion.setDateCreated(testDate);
        assertEquals(testDate, discussion.getDateCreated(), "DateCreated should match the set value");
    }

    @Test
    void testSetAndGetDiscussionText() {
        String expectedText = "I'm having trouble writing tests for my Micronaut app. Any suggestions?";
        discussion.setDiscussionText(expectedText);
        assertEquals(expectedText, discussion.getDiscussionText(), "DiscussionText should match the set value");
    }

    @Test
    void testSetAndGetUsername() {
        String expectedUsername = "testUser123";
        discussion.setUsername(expectedUsername);
        assertEquals(expectedUsername, discussion.getUsername(), "Username should match the set value");
    }

    @Test
    void testAllFieldsTogether() {
        Long discussionID = 5L;
        Long userID = 42L;
        String title = "Integration Testing Best Practices";
        String text = "What are the best practices for integration testing in microservices?";
        String username = "techGuru";
        LocalDateTime dateCreated = LocalDateTime.now();

        discussion.setDiscussionID(discussionID);
        discussion.setUserID(userID);
        discussion.setDiscussionTitle(title);
        discussion.setDiscussionText(text);
        discussion.setUsername(username);
        discussion.setDateCreated(dateCreated);

        assertEquals(discussionID, discussion.getDiscussionID());
        assertEquals(userID, discussion.getUserID());
        assertEquals(title, discussion.getDiscussionTitle());
        assertEquals(text, discussion.getDiscussionText());
        assertEquals(username, discussion.getUsername());
        assertEquals(dateCreated, discussion.getDateCreated());
    }

    @Test
    void testNullValues() {
        discussion.setDiscussionID(1L);
        discussion.setUserID(10L);
        discussion.setDiscussionTitle("Title");

        // Set to null
        discussion.setDiscussionID(null);
        discussion.setUserID(null);
        discussion.setDiscussionTitle(null);

        assertNull(discussion.getDiscussionID());
        assertNull(discussion.getUserID());
        assertNull(discussion.getDiscussionTitle());
    }

    @Test
    void testEmptyStrings() {
        discussion.setDiscussionTitle("");
        discussion.setDiscussionText("");
        discussion.setUsername("");

        assertEquals("", discussion.getDiscussionTitle());
        assertEquals("", discussion.getDiscussionText());
        assertEquals("", discussion.getUsername());
    }


    // unlikely but needed
    @Test
    void testLongTextContent() {
        String longText = "a".repeat(10000);
        discussion.setDiscussionText(longText);
        assertEquals(longText, discussion.getDiscussionText(), "Should handle long text content");
        assertEquals(10000, discussion.getDiscussionText().length());
    }

    @Test
    void testSpecialCharactersInText() {
        String specialChars = "Test with special chars: @#$%^&*()_+{}|:<>?~`-=[]\\;',./";
        discussion.setDiscussionTitle(specialChars);
        discussion.setDiscussionText(specialChars);

        assertEquals(specialChars, discussion.getDiscussionTitle());
        assertEquals(specialChars, discussion.getDiscussionText());
    }

    @Test
    void testUsernameIsTransient() {
        // Username i made @Transient, so it won't be persisted to DB
        //verifies it works as a regular field in the object
        String username = "transientUser";
        discussion.setUsername(username);
        assertEquals(username, discussion.getUsername(), "Transient username should still work in memory");
    }
}
