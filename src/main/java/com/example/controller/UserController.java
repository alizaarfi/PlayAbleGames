package com.example.controller;

import com.example.api.ChangePasswordRequest;
import com.example.domain.User;
import com.example.service.UserService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
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

    @Secured(SecurityRule.IS_AUTHENTICATED)
    @Post("/change-password")
    public HttpResponse<?> changePassword(@Body ChangePasswordRequest request,
                                          Authentication authentication) {

        // get username from JWT
        String username = authentication.getName();

        // fetch user from DB
        User user = userService.getUserByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        try {
            userService.changePassword(
                    user.getUserID(),
                    request.getOldPassword(),
                    request.getNewPassword()
            );
            return HttpResponse.ok(Map.of("message", "Password changed successfully"));
        } catch (Exception e) {
            return HttpResponse.badRequest(Map.of("error", e.getMessage()));
        }
    }
}