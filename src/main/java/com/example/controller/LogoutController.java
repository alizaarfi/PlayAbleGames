package com.example.controller;

import com.example.security.TokenBlacklist;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import jakarta.inject.Inject;

/**
 * Controller handing user logout
 * blacklists JWT tokens so they cant be reused
 */
@Controller("/user")
public class LogoutController {

    @Inject
    TokenBlacklist tokenBlacklist;

    /**
     * endpoint for logging out a user
     * @param request HTTP request containing Authorization header
     * @return HTTP response confirming logout or unauthorized access
     */
    @Post("/logout")
    public HttpResponse<?> logout(HttpRequest<?> request) {

        //get authorization header
        String authHeader = request.getHeaders().getAuthorization().orElse(null);

        //validate header presence and bearer format
        if (authHeader==null|| !authHeader.startsWith("Bearer ")) {
            return HttpResponse.status( HttpStatus.UNAUTHORIZED )
                    .body("No Token provided");
        }

        //extract token (remove "bearer" prefix)
        String token = authHeader.substring(7);

        // Blacklist the token
        tokenBlacklist.blacklist(token);

        return HttpResponse.ok("Logged out successfully");

    }
}
