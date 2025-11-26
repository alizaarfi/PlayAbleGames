package com.example.controller;

import com.example.security.TokenBlacklist;

import io.micronaut.http.HttpHeaders;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LogoutControllerTest {

    private LogoutController logoutController;
    private TokenBlacklist blacklist;

    @BeforeEach
    void setup() {
        blacklist = mock(TokenBlacklist.class);
        logoutController = new LogoutController();
        logoutController.tokenBlacklist = blacklist;
    }

    @Test
    void testLogoutWithoutToken() {
        HttpRequest<?> request = mock(HttpRequest.class);
        HttpHeaders headers = mock(HttpHeaders.class);

        when(request.getHeaders()).thenReturn(headers);
        when(headers.getAuthorization()).thenReturn(Optional.empty());

        HttpResponse<?> response = logoutController.logout(request);

        assertEquals(401, response.getStatus().getCode());
        assertEquals("No Token provided", response.getBody().get());
    }

    @Test
    void testLogoutWithToken() {
        HttpRequest<?> request = mock(HttpRequest.class);
        HttpHeaders headers = mock(HttpHeaders.class);

        when(request.getHeaders()).thenReturn(headers);
        when(headers.getAuthorization()).thenReturn(Optional.of("Bearer abc123"));

        HttpResponse<?> response = logoutController.logout(request);

        verify(blacklist).blacklist("abc123");
        assertEquals(200, response.getStatus().getCode());
        assertEquals("Logged out successfully", response.getBody().get());
    }
}
