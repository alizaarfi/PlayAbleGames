package com.example.controller;

import com.example.domain.UserFavorite;
import com.example.repository.UserFavoriteRepository;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller("/api/users/{userId}/favorites")
public class UserFavoriteController {

    private final UserFavoriteRepository repository;

    public UserFavoriteController(UserFavoriteRepository repository) {
        this.repository = repository;
    }

    @Get
    public List<Long> getFavorites(@PathVariable("userId") Long userId) {
        return repository.findByUserId(userId)
                .stream()
                .map(UserFavorite::getGameId)
                .collect(Collectors.toList());
    }

    @Post("/{gameId}")
    public HttpResponse<?> addFavorite(@PathVariable("userId") Long userId,
                                       @PathVariable("gameId") Long gameId) {

        return repository.findByUserIdAndGameId(userId, gameId)
                .<HttpResponse<?>>map(existing -> HttpResponse.ok())
                .orElseGet(() -> {
                    UserFavorite favorite = new UserFavorite();
                    favorite.setUserId(userId);
                    favorite.setGameId(gameId);
                    repository.save(favorite);
                    return HttpResponse.created("created");
                });
    }

    @Delete("/{gameId}")
    public HttpResponse<?> removeFavorite(@PathVariable("userId") Long userId,
                                          @PathVariable("gameId") Long gameId) {

        repository.findByUserIdAndGameId(userId, gameId)
                .ifPresent(repository::delete);

        return HttpResponse.noContent();
    }
}
