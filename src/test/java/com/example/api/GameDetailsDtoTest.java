package com.example.api;

import com.example.domain.Game;
import com.example.domain.GalleryImage;
import com.example.domain.Review;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the GameDetailsDto factory method and rating calculations.
 */
class GameDetailsDtoTest {

    @Test
    void from_buildsDtoAndComputesAverages() {
        // --- arrange game ---
        Game game = new Game();
        game.setId(1L);
        game.setName("Minecraft");
        game.setDeveloper("Mojang");
        game.setAgeRating("PEGI 7");
        game.setLinkToStore("link");
        game.setGameIconUrl("icon");
        game.setGameDescription("desc");
        game.setReleaseDate(LocalDate.of(2011, 8, 16));
        game.setVersionNumber("1.0");
        game.setAboutVision("vision");
        game.setAboutHearing("hearing");
        game.setAboutCognitive("cognitive");
        game.setAboutSpeech("speech");
        game.setAboutMotor("motor");

        // --- arrange reviews---
        Review r1 = new Review();
        r1.setVisionRating(3);
        r1.setHearingRating(5);
        r1.setCognitiveRating(4);
        r1.setSpeechRating(0);
        r1.setMotorRating(4);
        r1.setGameplayRating(5);

        Review r2 = new Review();
        r2.setVisionRating(5);
        r2.setHearingRating(5);
        r2.setCognitiveRating(3);
        r2.setSpeechRating(null);
        r2.setMotorRating(4);
        r2.setGameplayRating(4);

        List<Review> reviews = List.of(r1, r2);

        // --- arrange gallery ---
        GalleryImage img = new GalleryImage();
        img.setId(10L);
        img.setGameId(1L);
        img.setImageUrl("url");
        img.setAltText("alt");

        List<GalleryImage> gallery = List.of(img);

        // --- act ---
        GameDetailsDto dto = GameDetailsDto.from(game, reviews, gallery);

        // --- assert basic mapping ---
        assertEquals(1L, dto.id());
        assertEquals("Minecraft", dto.name());
        assertEquals("Mojang", dto.developer());
        assertEquals("PEGI 7", dto.ageRating());
        assertEquals("1.0", dto.versionNumber());
        assertEquals("vision", dto.aboutVision());
        assertEquals("hearing", dto.aboutHearing());
        assertEquals("cognitive", dto.aboutCognitive());
        assertEquals("speech", dto.aboutSpeech());
        assertEquals("motor", dto.aboutMotor());
        assertEquals("2011-08-16", dto.releaseDate());

        // --- assert averages (with small delta for doubles) ---
        assertEquals(4.0, dto.avgVisualRating(), 0.0001);
        assertEquals(5.0, dto.avgHearingRating(), 0.0001);
        assertEquals(3.5, dto.avgCognitiveRating(), 0.0001);
        assertEquals(0.0, dto.avgSpeechRating(), 0.0001);     // (0 + 0) / 2
        assertEquals(4.0, dto.avgMotorRating(), 0.0001);
        assertEquals(4.5, dto.avgGameplayRating(), 0.0001);

        assertEquals(2, dto.reviews().size());
        assertEquals(1, dto.gallery().size());
    }

    @Test
    void from_handlesNoReviewsWithZeroAverages() {
        Game game = new Game();
        game.setId(2L);
        game.setName("No Reviews Game");

        GameDetailsDto dto = GameDetailsDto.from(game, List.of(), List.of());

        // all averages should default to 0 when there are no reviews
        assertEquals(0.0, dto.avgVisualRating());
        assertEquals(0.0, dto.avgHearingRating());
        assertEquals(0.0, dto.avgCognitiveRating());
        assertEquals(0.0, dto.avgSpeechRating());
        assertEquals(0.0, dto.avgMotorRating());
        assertEquals(0.0, dto.avgGameplayRating());
    }
}
