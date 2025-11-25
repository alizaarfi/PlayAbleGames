package com.example.service;

import com.example.domain.UserFavorite;
import com.example.repository.UserFavoriteRepository;
import jakarta.inject.Singleton;

import java.util.List;

@Singleton
public class UserFavoriteService {

    // ----- Repository that handles all database operations
    // for inserting, deleting, and retrieving favorites.
    private final UserFavoriteRepository repo;

    // ----- Inject the repository via constructor injection
    public UserFavoriteService(UserFavoriteRepository repo) {
        this.repo = repo;
    }

    // ----- Returns a list of all favorite GameIDs for the given user
    // Converts the list of UserFavorite entities into a simple List<Long>
    public List<Long> getFavorites(Long userId) {
        return repo.findByUserId(userId)
                .stream()
                .map(UserFavorite::getGameId)
                .toList();
    }

    // ----- Adds a game to the user's favorites
    // If the record already exists, nothing is saved (prevents duplicates)
    public void addFavorite(Long userId, Long gameId) {
        repo.findByUserIdAndGameId(userId, gameId)
                .orElseGet(() -> repo.save(new UserFavorite(userId, gameId)));
    }

    // ----- Removes a game from the user's favorites
    // If the record does not exist, the delete operation simply does nothing
    public void removeFavorite(Long userId, Long gameId) {
        repo.deleteByUserIdAndGameId(userId, gameId);
    }
}
