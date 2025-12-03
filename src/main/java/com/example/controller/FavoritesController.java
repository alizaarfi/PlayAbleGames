package com.example.controller;

import com.example.service.UserFavoriteService;
import io.micronaut.http.*;
import io.micronaut.http.annotation.*;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import io.micronaut.security.authentication.Authentication;

// Controller for listing, adding and removing user favourites.
@Secured(SecurityRule.IS_AUTHENTICATED)
@Controller("/api/favorites")
public class FavoritesController {

    private final UserFavoriteService service;

    // Inject the service that handles database logic for user favorites
    public FavoritesController(UserFavoriteService service) {
        this.service = service;
    }

    // ---------------------------------------------------------------------
    // Returns a list of game IDs the  user has favorited.
    // ---------------------------------------------------------------------
    @Get
    public HttpResponse<?> list(Authentication authentication) {
        String username = authentication.getName(); // from JWT "sub"
        return HttpResponse.ok(service.getFavoritesForUsername(username));
    }

    // ---------------------------------------------------------------------
    // Adds the given game to the current user's favorites.
    // If it already exists, the service ignores the duplicate.
    // ---------------------------------------------------------------------
    @Post("/{gameId}")
    public HttpResponse<?> add(Long gameId, Authentication authentication) {
        String username = authentication.getName();
        service.addFavoriteForUsername(username, gameId);
        return HttpResponse.ok();
    }

    // ---------------------------------------------------------------------
    // Removes the game from the user's favorites.
    // If no record exists, nothing happens
    // ---------------------------------------------------------------------
    @Delete("/{gameId}")
    public HttpResponse<?> remove(Long gameId, Authentication authentication) {
        String username = authentication.getName();
        service.removeFavoriteForUsername(username, gameId);
        return HttpResponse.ok();
    }
}
