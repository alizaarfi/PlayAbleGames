package com.example.controller;

import com.example.service.UserFavoriteService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.security.authentication.Authentication;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

// Unit tests for the favourites controller.
@ExtendWith(MockitoExtension.class)
class FavoritesControllerTest {

    @Mock
    UserFavoriteService service;

    @InjectMocks
    FavoritesController controller;

    @Mock
    Authentication authentication;

    // Verifies service delegation for adding a favourite.
    @Test
    void add_callsServiceAndReturnsOk() {
        Long gameId = 42L;

        when(authentication.getName()).thenReturn("testuser");

        HttpResponse<?> response = controller.add(gameId, authentication);

        verify(service).addFavoriteForUsername("testuser", gameId);
        assertEquals(HttpStatus.OK, response.getStatus());
    }

    // Verifies service delegation and HTTP 200 response for removal.
    @Test
    void remove_callsServiceAndReturnsOk() {
        Long gameId = 42L;

        when(authentication.getName()).thenReturn("testuser");

        HttpResponse<?> response = controller.remove(gameId, authentication);

        verify(service).removeFavoriteForUsername("testuser", gameId);
        assertEquals(HttpStatus.OK, response.getStatus());
    }

    // Verifies GET endpoint loads favourites for the authenticated user.
    @Test
    void list_returnsOkAndDelegatesToService() {
        when(authentication.getName()).thenReturn("testuser");
        when(service.getFavoritesForUsername("testuser"))
                .thenReturn(Collections.singletonList(5L));

        HttpResponse<?> response = controller.list(authentication);

        verify(service).getFavoritesForUsername("testuser");
        assertEquals(HttpStatus.OK, response.getStatus());
        assertEquals(Collections.singletonList(5L), response.getBody().orElse(null));
    }
}
