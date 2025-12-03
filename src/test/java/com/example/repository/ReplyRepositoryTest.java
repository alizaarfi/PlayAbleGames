package com.example.repository;

import com.example.domain.Discussion;
import com.example.domain.Reply;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * ReplyRepositoryTest tests the repository correctly does CRUD operations as it extends crud repo
 *  and also has the custom queries working.
 * Tests:
 * - Replies can be saved and  retrieved.
 * - The custom query returns results in the correct order desc.
 * - The LEFT JOIN used in the query does not break when the users table has no matching rows.
 * - The transient "username" field behaves safely when populated through queries.
 */

// transactional means that each test runs inside a transaction when that state is true
// rollback is for when its true, micronaut rolls back the DB changes automatically
@MicronautTest(transactional = true, rollback = true)
class ReplyRepositoryTest {

    @Inject
    DiscussionRepository discussionRepository;

    @Inject
    ReplyRepository replyRepository;

    private Reply reply1;
    private Reply reply2;

    private Long discussionId;  // add field

    @BeforeEach
    void setup() {
        Discussion discussion = new Discussion();
        discussion.setDiscussionTitle("Test Discussion");
        discussion.setDiscussionText("Test Content");
        discussion.setUserID(1L);
        discussion = discussionRepository.save(discussion);

        discussionId = discussion.getDiscussionID(); // store it

        reply1 = new Reply();
        reply1.setUserID(1L);
        reply1.setDiscussionID(discussionId);
        reply1.setReplyText("First reply in discussion");
        reply1.setDateCreated(LocalDateTime.of(2025, 1, 1, 10, 0));

        reply2 = new Reply();
        reply2.setUserID(2L);
        reply2.setDiscussionID(discussionId);
        reply2.setReplyText("Second reply in discussion");
        reply2.setDateCreated(LocalDateTime.of(2025, 1, 1, 11, 0));
    }

    @Test
    void testSaveReply() {
        Reply saved = replyRepository.save(reply1);

        assertNotNull(saved.getReplyID(), "Reply ID should be auto-generated");
        assertEquals(reply1.getReplyText(), saved.getReplyText());
    }

    @Test
    void testFindById() {
        Reply saved = replyRepository.save(reply1);
        Reply found = replyRepository.findById(saved.getReplyID()).orElse(null);

        assertNotNull(found);
        assertEquals("First reply in discussion", found.getReplyText());
    }

    @Test
    void testFindByDiscussionIdOrdered() {
        replyRepository.save(reply1);
        replyRepository.save(reply2);

        List<Reply> replies =
                replyRepository.findByDiscussionIDOrderByDateCreatedAsc(discussionId);

        assertEquals(2, replies.size(), "Should return two replies");
        assertEquals("First reply in discussion", replies.get(0).getReplyText());
        assertEquals("Second reply in discussion", replies.get(1).getReplyText());

    }

    @Test
    void testUsernameJoinField() {
        // Username only populates through DB JOIN in the query
        replyRepository.save(reply1);

        List<Reply> replies =
                replyRepository.findByDiscussionIDOrderByDateCreatedAsc(discussionId);

        // username may be null depending on test DB's users table, but it should not throw errors
        replies.get(0).setUsername("InjectedName");
        assertEquals("InjectedName", replies.get(0).getUsername());
    }
}
