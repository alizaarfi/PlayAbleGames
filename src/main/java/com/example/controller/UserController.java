package com.example.controller;

import com.example.domain.User;
import com.example.service.UserService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.rules.SecurityRule;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Controller("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Get current user's profile information
     * Available to all authenticated users
     */
    @Secured(SecurityRule.IS_AUTHENTICATED)
    @Get("/profile")
    public HttpResponse<?> getProfile(Authentication authentication) {
        String username = authentication.getName();

        Optional<User> userOpt = userService.getUserByUsername(username);

        if (userOpt.isEmpty()) {
            return HttpResponse.notFound("User not found");
        }

        User user = userOpt.get();

        Map<String, Object> profile = new HashMap<>();
        profile.put("userId", user.getUserID());
        profile.put("username", user.getUsername());
        profile.put("email", user.getEmail());
        profile.put("enabled", user.getEnabled());
        profile.put("profilePictureURL", user.getProfilePictureURL());

        return HttpResponse.ok(profile);
    }
}