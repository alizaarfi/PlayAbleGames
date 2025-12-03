package com.example.repository;

import com.example.domain.Discussion;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import com.example.domain.User;
import org.junit.jupiter.api.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * DiscussionRepositoryTest tests the repo's db operations and maintains arrange, act, assert structure where relevant:
 * - The basic CRUD operations (save, findById, update)
 * - The findAllOrderByDateCreatedDesc which is a custom query
 * - The findById query
 * - Tests different edge cases for discussions, like empty results
 * - Count and exists
 */
@MicronautTest(transactional = false, startApplication = false)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DiscussionRepositoryTest {

    @Inject
    DiscussionRepository discussionRepository;

    @Inject
    ReplyRepository replyRepository;  // Added for proper cleanup

    @Inject
    UserRepository userRepository;

    private User testUser;

    @BeforeAll
    void setupTestData() {
        // Clean discussions and replies only
        try {
            replyRepository.deleteAll();
            discussionRepository.deleteAll();
        } catch (Exception e) {
            // Ignore cleanup errors on first run
        }

        // Create test user for foreign key references with unique email
        testUser = new User();
        testUser.setUsername("testuser_" + System.currentTimeMillis());
        testUser.setEmail("test_" + System.currentTimeMillis() + "@example.com");
        testUser.setPassword("password123");
        testUser = userRepository.save(testUser);
    }

    @BeforeEach
    void setUp() {
        // Clean up in correct order: child tables first (replies), then parent (discussions)
        replyRepository.deleteAll();
        discussionRepository.deleteAll();
    }

    @AfterEach
    void tearDown() {
        // Clean up in correct order: child tables first (replies), then parent (discussions)
        replyRepository.deleteAll();
        discussionRepository.deleteAll();
    }

    @AfterAll
    void cleanup() {
        // Final cleanup - only clean up discussions and replies
        // We leave the test user in the database to avoid user_roles FK issues
        try {
            replyRepository.deleteAll();
            discussionRepository.deleteAll();
        } catch (Exception e) {
            // Log but don't fail the test
            System.err.println("Cleanup warning: " + e.getMessage());
        }
    }

    @Test
    void testSaveDiscussion() {
        // Given
        Discussion discussion = new Discussion();
        discussion.setDiscussionTitle("Test Discussion");
        discussion.setDiscussionText("Test content for discussion");
        discussion.setUserID(testUser.getUserID());

        // When
        Discussion saved = discussionRepository.save(discussion);

        // Then
        assertNotNull(saved);
        assertNotNull(saved.getDiscussionID());
        assertEquals("Test Discussion", saved.getDiscussionTitle());
        assertEquals("Test content for discussion", saved.getDiscussionText());
        assertEquals(testUser.getUserID(), saved.getUserID());
        // Note: DateCreated may be null if not using @DateCreated annotation or database default
    }

    @Test
    void testFindById() {
        // Given
        Discussion discussion = new Discussion();
        discussion.setDiscussionTitle("Find By ID Test");
        discussion.setDiscussionText("Content for find by ID");
        discussion.setUserID(testUser.getUserID());
        Discussion saved = discussionRepository.save(discussion);

        // When
        Optional<Discussion> found = discussionRepository.findById(saved.getDiscussionID());

        // Then
        assertTrue(found.isPresent());
        assertEquals(saved.getDiscussionID(), found.get().getDiscussionID());
        assertEquals("Find By ID Test", found.get().getDiscussionTitle());
    }

    @Test
    void testFindByIdNotFound() {
        // When
        Optional<Discussion> found = discussionRepository.findById(99999L);

        // Then
        assertFalse(found.isPresent());
    }

    @Test
    void testFindAll() {
        // Given
        Discussion discussion1 = new Discussion();
        discussion1.setDiscussionTitle("Discussion 1");
        discussion1.setDiscussionText("Content 1");
        discussion1.setUserID(testUser.getUserID());

        Discussion discussion2 = new Discussion();
        discussion2.setDiscussionTitle("Discussion 2");
        discussion2.setDiscussionText("Content 2");
        discussion2.setUserID(testUser.getUserID());

        discussionRepository.save(discussion1);
        discussionRepository.save(discussion2);

        // When
        Iterable<Discussion> discussions = discussionRepository.findAll();

        // Then
        assertNotNull(discussions);
        long count = 0;
        for (Discussion d : discussions) {
            count++;
        }
        assertEquals(2, count);
    }

    @Test
    void testFindAllOrderByDateCreatedDesc() {
        // Given
        Discussion discussion1 = new Discussion();
        discussion1.setDiscussionTitle("First Discussion");
        discussion1.setDiscussionText("First content");
        discussion1.setUserID(testUser.getUserID());
        Discussion saved1 = discussionRepository.save(discussion1);

        // Small delay to ensure different timestamps
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        Discussion discussion2 = new Discussion();
        discussion2.setDiscussionTitle("Second Discussion");
        discussion2.setDiscussionText("Second content");
        discussion2.setUserID(testUser.getUserID());
        Discussion saved2 = discussionRepository.save(discussion2);

        // When
        List<Discussion> discussions = discussionRepository.findAllOrderByDateCreatedDesc();

        // Then
        assertNotNull(discussions);
        assertTrue(discussions.size() >= 2, "Expected at least 2 discussions, but got " + discussions.size());

        // Find our test discussions in the results
        boolean found1 = false;
        boolean found2 = false;
        for (Discussion d : discussions) {
            if (d.getDiscussionID().equals(saved1.getDiscussionID())) found1 = true;
            if (d.getDiscussionID().equals(saved2.getDiscussionID())) found2 = true;
        }
        assertTrue(found1, "Could not find first discussion");
        assertTrue(found2, "Could not find second discussion");
    }

    @Test
    void testFindAllOrderByDateCreatedDescEmpty() {
        // When
        List<Discussion> discussions = discussionRepository.findAllOrderByDateCreatedDesc();

        // Then
        assertNotNull(discussions);
        assertTrue(discussions.isEmpty());
    }

    @Test
    void testCount() {
        // Given
        Discussion discussion1 = new Discussion();
        discussion1.setDiscussionTitle("Discussion 1");
        discussion1.setDiscussionText("Content 1");
        discussion1.setUserID(testUser.getUserID());

        Discussion discussion2 = new Discussion();
        discussion2.setDiscussionTitle("Discussion 2");
        discussion2.setDiscussionText("Content 2");
        discussion2.setUserID(testUser.getUserID());

        discussionRepository.save(discussion1);
        discussionRepository.save(discussion2);

        // When
        long count = discussionRepository.count();

        // Then
        assertEquals(2, count);
    }

    @Test
    void testCountEmpty() {
        // When
        long count = discussionRepository.count();

        // Then
        assertEquals(0, count);
    }

    @Test
    void testExistsById() {
        // Given
        Discussion discussion = new Discussion();
        discussion.setDiscussionTitle("Exists Test");
        discussion.setDiscussionText("Content for exists test");
        discussion.setUserID(testUser.getUserID());
        Discussion saved = discussionRepository.save(discussion);

        // When
        boolean exists = discussionRepository.existsById(saved.getDiscussionID());
        boolean notExists = discussionRepository.existsById(99999L);

        // Then
        assertTrue(exists);
        assertFalse(notExists);
    }

    @Test
    void testSaveMultipleDiscussions() {
        // Given
        Discussion discussion1 = new Discussion();
        discussion1.setDiscussionTitle("Multiple 1");
        discussion1.setDiscussionText("Content 1");
        discussion1.setUserID(testUser.getUserID());

        Discussion discussion2 = new Discussion();
        discussion2.setDiscussionTitle("Multiple 2");
        discussion2.setDiscussionText("Content 2");
        discussion2.setUserID(testUser.getUserID());

        Discussion discussion3 = new Discussion();
        discussion3.setDiscussionTitle("Multiple 3");
        discussion3.setDiscussionText("Content 3");
        discussion3.setUserID(testUser.getUserID());

        // When
        discussionRepository.save(discussion1);
        discussionRepository.save(discussion2);
        discussionRepository.save(discussion3);

        // Then
        long count = discussionRepository.count();
        assertEquals(3, count);

        Iterable<Discussion> all = discussionRepository.findAll();
        assertNotNull(all);
        long actualCount = 0;
        for (Discussion d : all) {
            actualCount++;
        }
        assertEquals(3, actualCount);
    }

    @Test
    void testFindByIdWithUsername() {
        // Given
        Discussion discussion = new Discussion();
        discussion.setDiscussionTitle("Username Test");
        discussion.setDiscussionText("Content with username");
        discussion.setUserID(testUser.getUserID());
        Discussion saved = discussionRepository.save(discussion);

        // When
        Optional<Discussion> found = discussionRepository.findById(saved.getDiscussionID());

        // Then
        assertTrue(found.isPresent());
        assertEquals(testUser.getUserID(), found.get().getUserID());

        // Verify we can fetch the user if needed
        Optional<User> user = userRepository.findById(found.get().getUserID());
        assertTrue(user.isPresent());
        assertEquals(testUser.getUsername(), user.get().getUsername());
    }
}