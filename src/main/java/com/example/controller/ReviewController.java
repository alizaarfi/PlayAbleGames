package com.example.controller;

import com.example.domain.Review;
import com.example.repository.ReviewRepository;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

//REST controller for creating and listing reviews for a specific game.

@Controller("/api/games/{gameId}/reviews")
public class ReviewController {

    private final ReviewRepository reviewRepository;


    public ReviewController(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }


    //Returns all reviews for the given game.

    @Get
    public List<Review> listForGame(@PathVariable Long gameId) {
        return reviewRepository.findByGameId(gameId);
    }

    //-------------------------------------------------------------
    // Creates a new review for the given game.
    // The server normalises and checks ratings and text length to
    // avoid storing invalid or malicious data.
    //-------------------------------------------------------------

    @Post
    public HttpResponse<Review> create(@PathVariable Long gameId,
                                       @Body Review incoming) {

        Long fakeUserId = 1L;

        // ---  server-side validation ----------------------------------
        // Ensure we never store null ratings or excessively long text.
        String rawText = incoming.getReviewText();
        String safeText = rawText == null ? "" : rawText.trim();
        if (safeText.length() > 2000) {
            safeText = safeText.substring(0, 2000);
        }

        // Reject completely empty reviews (no text and no ratings).
        boolean allRatingsMissing =
                incoming.getHearingRating()   == null &&
                        incoming.getVisionRating()    == null &&
                        incoming.getCognitiveRating() == null &&
                        incoming.getSpeechRating()    == null &&
                        incoming.getMotorRating()     == null &&
                        incoming.getGameplayRating()  == null;

        if (allRatingsMissing && safeText.isEmpty()) {
            return HttpResponse.badRequest();
        }

        // --- construct persisted review object -----------------------------
        // Only copy the fields that are allowed to be set by the client.
        Review review = new Review();
        review.setGameId(gameId);
        review.setUserId(fakeUserId);

        review.setHearingRating(normaliseRating(incoming.getHearingRating()));
        review.setVisionRating(normaliseRating(incoming.getVisionRating()));
        review.setCognitiveRating(normaliseRating(incoming.getCognitiveRating()));
        review.setSpeechRating(normaliseRating(incoming.getSpeechRating()));
        review.setMotorRating(normaliseRating(incoming.getMotorRating()));
        review.setGameplayRating(normaliseRating(incoming.getGameplayRating()));
        review.setReviewText(safeText);

        review.setReviewDate(LocalDateTime.now());
        review.setFlagged(false);
        review.setRemoved(false);

        Review saved = reviewRepository.save(review);
        return HttpResponse.created(saved);
    }


    // Normalising a rating to the 0–5 range and replaces null with 0.

    private int normaliseRating(Integer value) {
        if (value == null) {
            return 0;
        }
        if (value < 0) {
            return 0;
        }
        if (value > 5) {
            return 5;
        }
        return value;
    }
}
