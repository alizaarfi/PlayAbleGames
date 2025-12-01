package com.example.controller;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import jakarta.inject.Inject;
import com.example.domain.Discussion;
import com.example.domain.Reply;
import com.example.repository.DiscussionRepository;
import com.example.repository.ReplyRepository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * DiscussionController handles the community page's discussions feature
 * Three endpoints that retrieve all discussions, discussions by id and retrieve all replies for a discussion
 *
 * Architecture & Design Patterns:
 * - RESTful API controller using Micronaut framework
 * - DI for repository access
 * - Repository layer pattern for data access abstraction
 */

@Controller("/api/discussions")
public class DiscussionController {

    @Inject
    private DiscussionRepository discussionRepository;

    @Inject
    private ReplyRepository replyRepository;

    @Get
    public HttpResponse<List<Discussion>> getAllDiscussions() {
        return HttpResponse.ok(discussionRepository.findAllOrderByDateCreatedDesc());
    }

    @Get("/{id}")
    public HttpResponse<Discussion> getDiscussion(Long id) {
        return discussionRepository.findById(id)
                .map(HttpResponse::ok)
                .orElse(HttpResponse.notFound());
    }

    @Get("/{id}/replies")
    public HttpResponse<List<Reply>> getReplies(Long id) {
        return HttpResponse.ok(
                replyRepository.findByDiscussionDiscussionIDOrderByDateCreatedAsc(id)
        );
    }

    // To actually create new discussions
    @Post
    public HttpResponse<Discussion> createDiscussion(@Body Discussion discussion) {
        discussion.setDateCreated(LocalDateTime.now());
        Discussion saved = discussionRepository.save(discussion);
        return HttpResponse.created(saved);
    }
}