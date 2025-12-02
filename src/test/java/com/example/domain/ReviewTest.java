package com.example.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Checks that Review behaves as a well-formed data container.
 */
class ReviewTest {

    @Test
    void gettersAndSettersWorkAsExpected() {
        Review review = new Review();

        LocalDateTime now = LocalDateTime.now();

        review.setId(10L);
        review.setUserId(2L);
        review.setGameId(1L);
        review.setHearingRating(5);
        review.setVisionRating(3);
        review.setCognitiveRating(4);
        review.setSpeechRating(0);
        review.setMotorRating(4);
        review.setGameplayRating(5);
        review.setReviewText("Great accessibility.");
        review.setReviewDate(now);
        review.setFlagged(Boolean.TRUE);
        review.setRemoved(Boolean.FALSE);

        assertEquals(10L, review.getId());
        assertEquals(2L, review.getUserId());
        assertEquals(1L, review.getGameId());
        assertEquals(5, review.getHearingRating());
        assertEquals(3, review.getVisionRating());
        assertEquals(4, review.getCognitiveRating());
        assertEquals(0, review.getSpeechRating());
        assertEquals(4, review.getMotorRating());
        assertEquals(5, review.getGameplayRating());
        assertEquals("Great accessibility.", review.getReviewText());
        assertEquals(now, review.getReviewDate());
        assertTrue(review.getFlagged());
        assertFalse(review.getRemoved());
    }
}
