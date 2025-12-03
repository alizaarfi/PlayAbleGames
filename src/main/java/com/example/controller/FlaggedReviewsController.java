package com.example.controller;

import com.example.repository.FlaggedReviewsRepository;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Put;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;

import java.util.*;

/**
 * Controller that exposes endpoints for managing flagged reviews.
 *
 * Responsibilities:
 * - Delegate data access and actions to FlaggedReviewsRepository.
 * - Build simple Map-based responses that Micronaut will serialize to JSON.
 * - Keep endpoints unsecured for tests (annotated as anonymous).
 */

@Controller("/api")
public class FlaggedReviewsController {

    private final FlaggedReviewsRepository repository;

    public FlaggedReviewsController(FlaggedReviewsRepository repository) {
        this.repository = repository;
    }


    /**
     * HTTP GET /api/flaggedReviews
     *
     * - Retrieves a list of flagged reviews from the repository.
     * - Wraps the list in a top-level Map with key "reviews" so the response is a JSON object.
     */

    @Get("/flaggedReviews")
    @Secured("ADMIN")
    public Map<String, Object> getFlaggedReviews() {
        List<Map<String, Object>> reviews = repository.getFlaggedReviews();

        Map<String, Object> response = new HashMap<>();
        response.put("reviews", reviews);
        return response;
    }


    /**
     * HTTP PUT /api/reviews/{reviewId}/accept
     *
     * - Attempts to accept (approve) the specified review via repository.acceptReview.
     * - Returns a Map indicating success, the reviewId, and an action string.
     * - If the operation failed (e.g., review not found), includes a human-readable message.
     */

    @Put("/reviews/{reviewId}/accept")
    @Secured("ADMIN")
    public Map<String, Object> acceptReview(@PathVariable int reviewId) {
        boolean success = repository.acceptReview(reviewId);

        Map<String, Object> response = new HashMap<>();
        response.put("success", success);
        response.put("reviewId", reviewId);
        response.put("action", "accepted");

        if (!success) {
            response.put("message", "Review not found");
        }

        return response;
    }


    /**
     * HTTP PUT /api/reviews/{reviewId}/decline
     *
     * - Attempts to decline (remove) the specified review via repository.declineReview.
     * - Returns a Map indicating success, the reviewId, and an action string.
     * - If the operation failed (e.g., review not found), includes a human-readable message.
     */

    @Put("/reviews/{reviewId}/decline")
    @Secured("ADMIN")
    public Map<String, Object> declineReview(@PathVariable int reviewId) {
        boolean success = repository.declineReview(reviewId);

        Map<String, Object> response = new HashMap<>();
        response.put("success", success);
        response.put("reviewId", reviewId);
        response.put("action", "declined");

        if (!success) {
            response.put("message", "Review not found");
        }

        return response;
    }
}