package com.example.controller;

import com.example.domain.Review;
import com.example.repository.ReviewRepository;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


class ReviewControllerTest {

    private ReviewRepository reviewRepository;
    private ReviewController reviewController;

    @BeforeEach
    void setUp() {
        // Use a mock repository so we do not need a real database.
        reviewRepository = mock(ReviewRepository.class);
        reviewController = new ReviewController(reviewRepository);
    }


     // Verifies that listForGame delegates to ReviewRepository.findByGameId
     // and returns the same list of reviews.

    @Test
    void testListReviewsForGame() {
        Long gameId = 42L;

        Review r1 = new Review();
        r1.setId(1L);
        r1.setGameId(gameId);
        r1.setReviewText("Review 1");

        Review r2 = new Review();
        r2.setId(2L);
        r2.setGameId(gameId);
        r2.setReviewText("Review 2");

        List<Review> expected = Arrays.asList(r1, r2);
        when(reviewRepository.findByGameId(gameId)).thenReturn(expected);

        List<Review> actual = reviewController.listForGame(gameId);

        assertEquals(2, actual.size(), "Should return the same number of reviews");
        assertEquals(expected, actual, "Controller should return the list from the repository");
        verify(reviewRepository, times(1)).findByGameId(gameId);
    }


     //  Verifies that create():
     //  - normalises the incoming data,
     //  - sets metadata fields (gameId, userId, date, flags),
     //  - and returns HTTP 201 CREATED with the saved Review.

    @Test
    void testCreateReview() {
        Long gameId = 5L;

        Review incoming = new Review();
        incoming.setHearingRating(4);
        incoming.setVisualRating(3);
        incoming.setCognitiveRating(5);
        incoming.setSpeechRating(2);
        incoming.setMotorRating(1);
        incoming.setGameplayRating(5);
        incoming.setReviewText("  Great accessibility features!  ");

        // When the controller calls save(), we simulate the DB assigning an id.
        when(reviewRepository.save(any(Review.class)))
                .thenAnswer(invocation -> {
                    Review toSave = invocation.getArgument(0);
                    toSave.setId(10L);
                    return toSave;
                });

        HttpResponse<Review> response = reviewController.create(gameId, incoming);

        assertEquals(HttpStatus.CREATED, response.getStatus(), "Should return 201 CREATED");
        Review body = response.body();
        assertNotNull(body, "Response must contain a Review body");

        // Check that ids and metadata are set correctly.
        assertEquals(10L, body.getId(), "Saved review should have an id set by the repository");
        assertEquals(gameId, body.getGameId(), "Game id should come from path variable");
        assertEquals(1L, body.getUserId(), "User id should be the temporary fake user id");

        assertEquals("Great accessibility features!", body.getReviewText(),
                "Review text should be trimmed by the controller");


        assertNotNull(body.getReviewDate(), "Controller should set the review date");
        assertTrue(body.getReviewDate().isBefore(LocalDateTime.now().plusSeconds(1)),
                "Review date should be close to 'now'");

        // Ratings should be preserved here
        assertEquals(4, body.getHearingRating());
        assertEquals(3, body.getVisualRating());
        assertEquals(5, body.getCognitiveRating());
        assertEquals(2, body.getSpeechRating());
        assertEquals(1, body.getMotorRating());
        assertEquals(5, body.getGameplayRating());

        verify(reviewRepository, times(1)).save(any(Review.class));
    }

    // Verifies that create() rejects a completely empty review
    @Test
    void testRejectEmptyReview() {
        Long gameId = 1L;

        Review incoming = new Review();
        // All ratings left null
        incoming.setReviewText("   ");

        HttpResponse<Review> response = reviewController.create(gameId, incoming);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatus(),
                "Empty review should be rejected with BAD_REQUEST");
        assertNull(response.body(), "Rejected request should not have a body");

        // Ensure we did not try to save anything.
        verify(reviewRepository, never()).save(any(Review.class));
    }
}
