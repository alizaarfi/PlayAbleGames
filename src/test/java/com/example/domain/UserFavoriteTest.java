package com.example.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserFavoriteTest {

    // Verifies constructor initializes fields.
    @Test
    void constructor_initializesFields() {
        UserFavorite fav = new UserFavorite(1L, 2L);

        assertEquals(1L, fav.getUserId());
        assertEquals(2L, fav.getGameId());
    }

    // Verifies setter and getter behavior.
    @Test
    void settersAndGetters_functionCorrectly() {
        UserFavorite fav = new UserFavorite(1L, 2L);

        fav.setId(10L);
        fav.setUserId(20L);
        fav.setGameId(30L);

        assertEquals(10L, fav.getId());
        assertEquals(20L, fav.getUserId());
        assertEquals(30L, fav.getGameId());
    }
}
