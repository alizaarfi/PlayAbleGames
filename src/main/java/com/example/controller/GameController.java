package com.example.controller;

import com.example.api.GameDetailsDto;
import com.example.domain.Game;
import com.example.domain.GalleryImage;
import com.example.domain.Review;
import com.example.repository.GameRepository;
import com.example.repository.GalleryRepository;
import com.example.repository.ReviewRepository;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.exceptions.HttpStatusException;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;

import java.util.List;

@Secured(SecurityRule.IS_ANONYMOUS)
@Controller("/api/games")
public class GameController {

    private final GameRepository gameRepository;
    private final GalleryRepository galleryRepository;
    private final ReviewRepository reviewRepository;

    //Constructor injection supplies the controller with the required repositories.

    public GameController(GameRepository gameRepository,
                          GalleryRepository galleryRepository,
                          ReviewRepository reviewRepository) {
        this.gameRepository = gameRepository;
        this.galleryRepository = galleryRepository;
        this.reviewRepository = reviewRepository;
    }

    @Get("/{id}")
    public GameDetailsDto getGame(@PathVariable Long id) {

        // Fetch the game or return a 404 if it does not exist
        Game game = gameRepository.findById(id)
                .orElseThrow(() -> new HttpStatusException(HttpStatus.NOT_FOUND, "Game not found"));

        // Retrieve all related gallery images and reviews for this game
        List<GalleryImage> images = galleryRepository.findByGameId(id);
        List<Review> reviews      = reviewRepository.findByGameId(id);

        // Combine the game entity and related data into a single DTO for output
        return GameDetailsDto.from(game, reviews, images);
    }
}
