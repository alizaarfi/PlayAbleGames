package com.example.domain;

import io.micronaut.data.annotation.*;
import io.micronaut.data.model.naming.NamingStrategies;

@MappedEntity(value = "user_favorites", namingStrategy = NamingStrategies.Raw.class)
public class UserFavorite {

    @Id
    @GeneratedValue
    private Long id;

    @MappedProperty("UserID")
    private Long userId;

    @MappedProperty("GameID")
    private Long gameId;

    public UserFavorite() {}

    public UserFavorite(Long userId, Long gameId) {
        this.userId = userId;
        this.gameId = gameId;
    }


    // -------------------------------------------------------------------------
    // Getters and setters — required by Micronaut Data for persistence mapping
    // -------------------------------------------------------------------------

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Long getGameId() { return gameId; }
    public void setGameId(Long gameId) { this.gameId = gameId; }
}
