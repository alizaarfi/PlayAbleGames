package com.example.service;

import com.example.domain.UserFavorite;
import com.example.repository.UserFavoriteRepository;
import jakarta.inject.Singleton;

import java.util.List;
import java.util.stream.Collectors;

@Singleton
public class UserFavoriteService {

    // Repository that handles all database operations
    // for inserting, deleting, and retrieving favorites.
    private final UserFavoriteRepository repo;

    // constructor injection
    public UserFavoriteService(UserFavoriteRepository repo) {
        this.repo = repo;
    }

    // Returns a list of all favorite GameIDs for the given user
    // Converts the list of UserFavorite entities into a simple List<Long>
    public List<Long> getFavorites(Long userId) {
        return repo.findByUserId(userId)
                .stream()
                .map(UserFavorite::getGameId)
                .collect(Collectors.toList());
    }

    // Adds a game to the user's favorites
    // If the record already exists, nothing is saved (prevents duplicates)
    public void addFavorite(Long userId, Long gameId) {
        boolean exists = repo
                .findByUserIdAndGameId(userId, gameId)
                .isPresent();

        if (!exists) {
            UserFavorite fav = new UserFavorite(userId, gameId);
            repo.save(fav);
        }
    }

    // ----- Removes a game from the user's favorites
    // If the record does not exist, the delete operation simply does nothing
    public void removeFavorite(Long userId, Long gameId) {
        repo.deleteByUserIdAndGameId(userId, gameId);
    }
}
