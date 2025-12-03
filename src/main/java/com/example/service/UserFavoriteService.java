package com.example.service;

import com.example.domain.User;
import com.example.domain.UserFavorite;
import com.example.repository.UserFavoriteRepository;
import com.example.repository.UserRepository;
import jakarta.inject.Singleton;

import java.util.List;
import java.util.stream.Collectors;


@Singleton
public class UserFavoriteService {


    // Repository that handles all database operations
    // for inserting, deleting, and retrieving favorites.
    private final UserFavoriteRepository repo;

    private final UserRepository userRepository;

    // constructor injection
    public UserFavoriteService(UserFavoriteRepository repo,
                               UserRepository userRepository) {
        this.repo = repo;
        this.userRepository = userRepository;
    }

    // helper: resolve userId from username
    private Long resolveUserId(String username) {
        return userRepository
                .findByUsername(username)
                .map(User::getUserID)
                .orElseThrow(() ->
                        new IllegalArgumentException("User not found for username: " + username));
    }

    // Returns a list of all favorite GameIDs for the given user
    public List<Long> getFavorites(Long userId) {
        return repo.findByUserId(userId)
                .stream()
                .map(UserFavorite::getGameId)
                .collect(Collectors.toList());
    }

    // Adds a game to the user's favorites (ignores duplicates)
    public void addFavorite(Long userId, Long gameId) {
        boolean exists = repo
                .findByUserIdAndGameId(userId, gameId)
                .isPresent();

        if (!exists) {
            UserFavorite fav = new UserFavorite(userId, gameId);
            repo.save(fav);
        }
    }

    // Removes a game from the user's favorites
    // If the record does not exist, the delete operation does nothing
    public void removeFavorite(Long userId, Long gameId) {
        repo.deleteByUserIdAndGameId(userId, gameId);
    }

    // username-based methods used by the controller
    public List<Long> getFavoritesForUsername(String username) {
        Long userId = resolveUserId(username);
        return getFavorites(userId);
    }

    public void addFavoriteForUsername(String username, Long gameId) {
        Long userId = resolveUserId(username);
        addFavorite(userId, gameId);
    }

    public void removeFavoriteForUsername(String username, Long gameId) {
        Long userId = resolveUserId(username);
        removeFavorite(userId, gameId);
    }
}
