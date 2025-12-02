package com.example.controller;

import com.example.domain.User;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import io.micronaut.security.authentication.Authentication;
import jakarta.inject.Inject;
import com.example.domain.Discussion;
import com.example.domain.Reply;
import com.example.repository.DiscussionRepository;
import com.example.repository.ReplyRepository;
import com.example.repository.UserRepository;
import com.example.dto.CreateDiscussionRequest;
import com.example.dto.CreateReplyRequest;

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

    @Inject
    private UserRepository userRepository;

    @Get
    @Secured(SecurityRule.IS_ANONYMOUS) // Anyone can view
    public HttpResponse<List<Discussion>> getAllDiscussions() {
        return HttpResponse.ok(discussionRepository.findAllOrderByDateCreatedDesc());
    }

    @Get("/{id}")
    @Secured(SecurityRule.IS_ANONYMOUS) // Anyone can view
    public HttpResponse<Discussion> getDiscussion(Long id) {
        return discussionRepository.findById(id)
                .map(HttpResponse::ok)
                .orElse(HttpResponse.notFound());
    }

    @Get("/{id}/replies")
    @Secured(SecurityRule.IS_ANONYMOUS) // Anyone can view
    public HttpResponse<List<Reply>> getReplies(Long id) {
        return HttpResponse.ok(
                replyRepository.findByDiscussionIDOrderByDateCreatedAsc(id)
        );
    }

    @Post
    @Secured(SecurityRule.IS_AUTHENTICATED) // Must be logged in
    public HttpResponse<Discussion> createDiscussion(
            @Body CreateDiscussionRequest request,
            Authentication authentication) {

//        // Get user ID from authentication
//        Long userId = Long.parseLong(authentication.getName()); // Adjust based on your User setup

        String username = authentication.getName();
        User user = userRepository.findByUsername(username).orElseThrow();
        Long userId = user.getUserID();

        Discussion discussion = new Discussion();
        discussion.setUserID(userId);
        discussion.setDiscussionTitle(request.getDiscussionTitle());
        discussion.setDiscussionText(request.getDiscussionText());
        discussion.setDateCreated(LocalDateTime.now());

        Discussion saved = discussionRepository.save(discussion);
        return HttpResponse.created(saved);
    }

    @Post("/{id}/replies")
    @Secured(SecurityRule.IS_AUTHENTICATED) // Must be logged in
    public HttpResponse<Reply> createReply(
            Long id,
            @Body CreateReplyRequest request,
            Authentication authentication) {

//        // Get user ID from authentication
//        Long userId = Long.parseLong(authentication.getName()); // Adjust based on your User setup

        String username = authentication.getName();
        User user = userRepository.findByUsername(username).orElseThrow();
        Long userId = user.getUserID();

        Reply reply = new Reply();
        reply.setUserID(userId);
        reply.setDiscussionID(id);
        reply.setReplyText(request.getReplyText());
        reply.setDateCreated(LocalDateTime.now());

        Reply saved = replyRepository.save(reply);
        return HttpResponse.created(saved);
    }
}