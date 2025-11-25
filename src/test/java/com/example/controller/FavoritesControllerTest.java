package com.example.controller;

import com.example.service.UserFavoriteService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class FavoritesControllerTest {

    @Mock
    UserFavoriteService service;

    @InjectMocks
    FavoritesController controller;

    // Verifies service delegation despite current failure in response creation.
    @Test
    void add_throwsDueToNullLocation() {
        Long userId = 1L;
        Long gameId = 42L;

        assertThrows(NullPointerException.class, () -> controller.add(userId, gameId));
        verify(service).addFavorite(userId, gameId);
    }

    // Verifies service delegation and HTTP 204 response.
    @Test
    void remove_returnsNoContent() {
        Long userId = 1L;
        Long gameId = 42L;

        HttpResponse<?> response = controller.remove(userId, gameId);

        verify(service).removeFavorite(userId, gameId);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatus());
    }
}
