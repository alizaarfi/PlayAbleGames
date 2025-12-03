package com.example.controller;

import com.example.api.LoginRequest;
import com.example.api.LoginResponse;
import com.example.service.UserService;
import com.example.security.TokenBlacklist;
import io.micronaut.http.HttpResponse;
import io.micronaut.security.token.jwt.generator.JwtTokenGenerator;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@MicronautTest
class LoginControllerTest {

    private UserService userService;
    private JwtTokenGenerator jwtTokenGenerator;
    private LoginController loginController;

    @BeforeEach
    void setup() {
        userService = mock(UserService.class);
        jwtTokenGenerator = mock(JwtTokenGenerator.class);
        loginController = new LoginController(userService, jwtTokenGenerator);
    }

    @Test
    void testSuccessfulLogin() {
        LoginRequest req = new LoginRequest();
        req.setUsername("user");
        req.setPassword("Password1");

        // Mock user object with ID and username
        com.example.domain.User mockUser = new com.example.domain.User();
        mockUser.setUserID(1L);
        mockUser.setUsername("user");

        when(userService.authenticate("user", "Password1"))
                .thenReturn(Optional.of(mockUser));

        // Mock user roles
        when(userService.getUserRoles(1L))
                .thenReturn(List.of("BASIC"));

        // Mock token generation
        when(jwtTokenGenerator.generateToken(anyMap()))
                .thenReturn(Optional.of("mocktoken"));

        HttpResponse<?> response = loginController.login(req);

        assertEquals(200, response.getStatus().getCode());
        LoginResponse body = (LoginResponse) response.getBody().get();

        assertEquals("mocktoken", body.getToken());
        assertEquals("Login Successful", body.getMessage());
        assertEquals("BASIC", body.getRole());
    }

    @Test
    void testInvalidUsernameOrPassword() {
        LoginRequest req = new LoginRequest();
        req.setUsername("wrong");
        req.setPassword("Password1");

        when(userService.authenticate("wrong", "Password1"))
                .thenReturn(Optional.empty());

        HttpResponse<?> response = loginController.login(req);

        assertEquals(400, response.getStatus().getCode());
        LoginResponse body = (LoginResponse) response.getBody().get();
        assertEquals(null, body.getToken());
        assertEquals("Invalid username or password. Please try again.", body.getMessage());
    }

    @Test
    void testInvalidPasswordThrows() {
        LoginRequest req = new LoginRequest();
        req.setUsername("user");
        req.setPassword("WrongPass");

        when(userService.authenticate("user", "WrongPass"))
                .thenThrow(new RuntimeException("Invalid password"));

        HttpResponse<?> response = loginController.login(req);

        assertEquals(400, response.getStatus().getCode());
        LoginResponse body = (LoginResponse) response.getBody().get();
        assertEquals(null, body.getToken());
        assertEquals("Invalid username or password. Please try again.", body.getMessage());
    }
}
