package com.example.controller;

import com.example.api.ChangePasswordRequest;
import com.example.api.UserProfileUpdateDTO;
import com.example.domain.User;
import com.example.service.UserService;
import com.example.repository.UserRepository;
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
    private final UserRepository userRepository;

    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
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

    @Post("/profile")
    @Secured(SecurityRule.IS_AUTHENTICATED)
    public HttpResponse<?> updateProfile(@Body UserProfileUpdateDTO updateDTO, Authentication authentication) {
        try {
            String currentUsername = authentication.getName();
            User user = userRepository.findByUsername(currentUsername)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            // Validate that new username is not taken (if changed)
            if (!user.getUsername().equals(updateDTO.getUsername())) {
                if (userRepository.findByUsername(updateDTO.getUsername()).isPresent()) {
                    return HttpResponse.badRequest(Map.of("error", "Username already taken"));
                }
            }

            // Check if there are any actual changes
            if (user.getUsername().equals(updateDTO.getUsername())) {
                return HttpResponse.badRequest(Map.of("error", "No changes to save"));
            }

            // Update user
            user.setUsername(updateDTO.getUsername());
            userRepository.update(user);

            return HttpResponse.ok(Map.of("message", "Profile updated successfully"));
        } catch (Exception e) {
            return HttpResponse.serverError(Map.of("error", e.getMessage()));
        }
    }

    @Delete("/delete-account")
    @Secured(SecurityRule.IS_AUTHENTICATED)
    public HttpResponse<?> deleteAccount(@Body Map<String, String> request, Authentication authentication) {
        try {
            String username = authentication.getName();
            String password = request.get("password");

            if (password == null || password.isEmpty()) {
                return HttpResponse.badRequest(Map.of("error", "Password is required"));
            }

            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            //verifying password using UserService
            userService.verifyPassword(user.getUserID(), password);

            // Delete user (this will cascade delete related data if configured)
            userRepository.delete(user);

            return HttpResponse.ok(Map.of("message", "Account deleted successfully"));
        } catch (Exception e) {
            return HttpResponse.serverError(Map.of("error", "Failed to delete account: " + e.getMessage()));
        }
    }

}