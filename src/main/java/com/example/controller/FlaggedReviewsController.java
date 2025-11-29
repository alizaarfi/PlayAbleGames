package com.example.controller;

import com.example.repository.FlaggedReviewsRepository;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Put;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;

import java.util.*;

@Controller("/api")
@Secured(SecurityRule.IS_ANONYMOUS) //admin test - no security
public class FlaggedReviewsController {

    private final FlaggedReviewsRepository repository;

    public FlaggedReviewsController(FlaggedReviewsRepository repository) {
        this.repository = repository;
    }

    @Get("/flaggedReviews")
    public Map<String, Object> getFlaggedReviews() {
        List<Map<String, Object>> reviews = repository.getFlaggedReviews();

        Map<String, Object> response = new HashMap<>();
        response.put("reviews", reviews);
        return response;
    }

    @Put("/reviews/{reviewId}/accept")
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

    @Put("/reviews/{reviewId}/decline")
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