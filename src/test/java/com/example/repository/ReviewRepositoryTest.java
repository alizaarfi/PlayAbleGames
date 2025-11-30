//package com.example.repository;
//
//import com.example.domain.Review;
//import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
//import jakarta.inject.Inject;
//import org.junit.jupiter.api.Test;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
///**
// * Integration tests
// */
//@MicronautTest(startApplication = false)
//class ReviewRepositoryTest {
//
//    @Inject
//    ReviewRepository reviewRepository;
//
//    @Test
//    void findByGameId_returnsReviewsForMinecraft() {
//        // --- act ---
//        List<Review> reviews = reviewRepository.findByGameId(1L);
//
//        // --- assert: at least one review is seeded for game 1 ---
//        assertFalse(reviews.isEmpty());
//        assertEquals(1L, reviews.get(0).getGameId());
//        assertNotNull(reviews.get(0).getReviewText());
//    }
//}
