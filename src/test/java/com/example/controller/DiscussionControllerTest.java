package com.example.controller;

import com.example.domain.Discussion;
import com.example.domain.Reply;
import com.example.domain.User;
import com.example.dto.CreateReplyRequest;
import com.example.repository.DiscussionRepository;
import com.example.repository.ReplyRepository;
import com.example.repository.UserRepository;
import io.micronaut.core.type.Argument;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * DiscussionControllerTest tests the controllers REST endpoints.
 * Note: Uses rollback=false to allow HTTP client to see test data.
 */

@MicronautTest(rollback = false)
class DiscussionControllerTest {

    @Inject
    @Client("/")
    HttpClient client;

    @Inject
    DiscussionRepository discussionRepository;

    @Inject
    ReplyRepository replyRepository;

    @Inject
    UserRepository userRepository;

    private Discussion testDiscussion;
    private User testUser;

    @BeforeEach
    void setUp() {
        replyRepository.deleteAll();
        discussionRepository.deleteAll();

        testUser = userRepository.findAll().iterator().next();

        testDiscussion = new Discussion();
        testDiscussion.setUserID(testUser.getUserID());
        testDiscussion.setDiscussionTitle("Test Discussion");
        testDiscussion.setDiscussionText("This is a test discussion");
        testDiscussion.setDateCreated(LocalDateTime.now());
        testDiscussion = discussionRepository.save(testDiscussion);
    }

    @Test
    void testGetAllDiscussions_Success() {
        HttpResponse<List<Discussion>> response = client.toBlocking().exchange(
                HttpRequest.GET("/api/discussions"),
                Argument.listOf(Discussion.class)
        );

        assertEquals(HttpStatus.OK, response.getStatus());
        assertNotNull(response.body());
        assertFalse(response.body().isEmpty());
    }

    @Test
    void testGetDiscussion_Success() {
        HttpResponse<Discussion> response = client.toBlocking().exchange(
                HttpRequest.GET("/api/discussions/" + testDiscussion.getDiscussionID()),
                Discussion.class
        );

        assertEquals(HttpStatus.OK, response.getStatus());
        assertNotNull(response.body());
        assertEquals(testDiscussion.getDiscussionID(), response.body().getDiscussionID());
    }

    @Test
    void testGetDiscussion_NotFound() {
        HttpClientResponseException exception = assertThrows(
                HttpClientResponseException.class,
                () -> client.toBlocking().exchange(
                        HttpRequest.GET("/api/discussions/99999"),
                        Discussion.class
                )
        );

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    void testGetReplies_EmptyList() {
        HttpResponse<List<Reply>> response = client.toBlocking().exchange(
                HttpRequest.GET("/api/discussions/" + testDiscussion.getDiscussionID() + "/replies"),
                Argument.listOf(Reply.class)
        );

        assertEquals(HttpStatus.OK, response.getStatus());
        assertNotNull(response.body());
    }

    @Test
    void testGetReplies_NonExistentDiscussion() {
        HttpResponse<List<Reply>> response = client.toBlocking().exchange(
                HttpRequest.GET("/api/discussions/99999/replies"),
                Argument.listOf(Reply.class)
        );

        assertEquals(HttpStatus.OK, response.getStatus());
        assertNotNull(response.body());
    }


}