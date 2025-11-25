package com.example.controller;

import com.example.repository.FlaggedReviewsRepository;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import java.util.*;

@Controller("/api")
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
}