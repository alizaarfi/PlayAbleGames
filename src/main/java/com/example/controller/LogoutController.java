package com.example.controller;

import com.example.security.TokenBlacklist;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import jakarta.inject.Inject;

@Controller("/user")
public class LogoutController {

    @Inject
    TokenBlacklist tokenBlacklist;

    @Post("/logout")
    public HttpResponse<?> logout(HttpRequest<?> request) {

        String authHeader = request.getHeaders().getAuthorization().orElse(null);

        if (authHeader==null|| !authHeader.startsWith("Bearer ")) {
            return HttpResponse.status( HttpStatus.UNAUTHORIZED )
                    .body("No Token provided");
        }

        String token = authHeader.substring(7);

        // Blacklist the token
        tokenBlacklist.blacklist(token);

        return HttpResponse.ok("Logged out successfully");

    }
}
