package com.example.controller;

import com.example.api.GameDetailsDto;
import com.example.domain.Game;
import com.example.domain.GalleryImage;
import com.example.domain.Review;
import com.example.repository.GameRepository;
import com.example.repository.GalleryRepository;
import com.example.repository.ReviewRepository;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.exceptions.HttpStatusException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * GameController Tests using mocked repositories.
 */
class GameControllerTest {

    private GameRepository gameRepository;
    private GalleryRepository galleryRepository;
    private ReviewRepository reviewRepository;

    private GameController controller;

    @BeforeEach
    void setUp() {
        // -- create mocks and controller under test --
        gameRepository = mock(GameRepository.class);
        galleryRepository = mock(GalleryRepository.class);
        reviewRepository = mock(ReviewRepository.class);

        controller = new GameController(gameRepository, galleryRepository, reviewRepository);
    }

    @Test
    void getGame_returnsDtoWhenGameExists() {
        // -- arrange game entity --
        Game game = new Game();
        game.setId(1L);
        game.setName("Minecraft");
        game.setDeveloper("Mojang Studios");
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

        // -- arrange reviews and gallery --
        Review review = new Review();
        review.setGameplayRating(5);
        List<Review> reviews = List.of(review);

        GalleryImage image = new GalleryImage();
        image.setId(10L);
        image.setGameId(1L);
        image.setImageUrl("url");
        image.setAltText("alt");
        List<GalleryImage> gallery = List.of(image);

        // -- stub repository behaviour --
        when(gameRepository.findById(1L)).thenReturn(Optional.of(game));
        when(reviewRepository.findByGameId(1L)).thenReturn(reviews);
        when(galleryRepository.findByGameId(1L)).thenReturn(gallery);

        // -- act --
        GameDetailsDto dto = controller.getGame(1L);

        // -- assert core mapping --
        assertEquals(1L, dto.id());
        assertEquals("Minecraft", dto.name());
        assertEquals("Mojang Studios", dto.developer());
        assertEquals("PEGI 7", dto.ageRating());
        assertEquals("icon", dto.iconUrl());
        assertEquals("desc", dto.description());
        assertEquals("2011-08-16", dto.releaseDate());
        assertEquals("vision", dto.aboutVision());
        assertEquals("hearing", dto.aboutHearing());
        assertEquals("cognitive", dto.aboutCognitive());
        assertEquals("speech", dto.aboutSpeech());
        assertEquals("motor", dto.aboutMotor());
        assertEquals(1, dto.reviews().size());
        assertEquals(1, dto.gallery().size());

        // -- verify repository calls --
        verify(gameRepository).findById(1L);
        verify(reviewRepository).findByGameId(1L);
        verify(galleryRepository).findByGameId(1L);
    }

    @Test
    void getGame_throwsNotFoundWhenGameMissing() {
        // -- arrange missing game --
        when(gameRepository.findById(99L)).thenReturn(Optional.empty());

        // -- act + assert --
        HttpStatusException ex = assertThrows(
                HttpStatusException.class,
                () -> controller.getGame(99L)
        );

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
        assertTrue(ex.getMessage().contains("Game not found"));

        verifyNoInteractions(galleryRepository, reviewRepository);
    }
}
