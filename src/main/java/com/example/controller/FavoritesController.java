package com.example.controller;

import com.example.service.UserFavoriteService;
import io.micronaut.http.*;
import io.micronaut.http.annotation.*;


@Controller("/api/users/{userId}/favorites")
public class FavoritesController {

    private final UserFavoriteService service;

    // Inject the service that handles database logic for user favorites
    public FavoritesController(UserFavoriteService service) {
        this.service = service;
    }

    // ---------------------------------------------------------------------
    // GET /api/users/{userId}/favorites
    // Returns a list of game IDs the user has favorited.
    // ---------------------------------------------------------------------
    @Get
    public HttpResponse<?> list(Long userId) {
        return HttpResponse.ok(service.getFavorites(userId));
    }

    // ---------------------------------------------------------------------
    // POST /api/users/{userId}/favorites/{gameId}
    // Adds the given game to the user's favorites.
    // If it already exists, the service ignores the duplicate.
    // ---------------------------------------------------------------------
    @Post("/{gameId}")
    public HttpResponse<?> add(Long userId, Long gameId) {
        service.addFavorite(userId, gameId);
        return HttpResponse.created(null);
    }

    // ---------------------------------------------------------------------
    // DELETE /api/users/{userId}/favorites/{gameId}
    // Removes the game from the user's favorites.
    // If no record exists, nothing happens.
    // ---------------------------------------------------------------------
    @Delete("/{gameId}")
    public HttpResponse<?> remove(Long userId, Long gameId) {
        service.removeFavorite(userId, gameId);
        return HttpResponse.noContent();
    }
}
