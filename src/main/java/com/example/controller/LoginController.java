package com.example.controller;

import com.example.api.LoginRequest;
import com.example.api.LoginResponse;
import com.example.service.UserService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;

import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import io.micronaut.security.token.jwt.generator.JwtTokenGenerator;
import jakarta.inject.Inject;

import java.util.Map;
import java.util.Optional;

/**
 * Controller handing user login
 * allows unauthenticated users to login and receive a JWT token
 */

@Secured(SecurityRule.IS_ANONYMOUS) //unauthenticated users
@Controller("/user")
public class LoginController {

    private final UserService userService;
    @Inject
    private final JwtTokenGenerator jwtTokenGenerator;

    public LoginController(UserService userService, JwtTokenGenerator jwtTokenGenerator) {
        this.userService = userService;
        this.jwtTokenGenerator=jwtTokenGenerator;
    }

    /**
     * endpoint for logging in a user
     * @param loginRequest contains username and password
     * @return JWT token if successful, or error response if failed
     */
    @Post("/login")
    public HttpResponse<?> login(@Body LoginRequest loginRequest) {
        try {
            //authenticate user credentials
            var user = userService.authenticate(
                    loginRequest.getUsername(),
                    loginRequest.getPassword());
            if (user.isEmpty()) {
                //return 400 bad request
                return HttpResponse.badRequest(new LoginResponse(null, "Invalid username or password. Please try again.", null));
            }

            //get the user's roles from database
            java.util.List<String> roles = userService.getUserRoles(user.get().getUserID());

            // Check if user has any roles
            if (roles.isEmpty()) {
                return HttpResponse.badRequest(
                        new LoginResponse(null, "User has no assigned roles.", null));
            }

            // If user has ADMIN role, they're an admin. else use first role
            String primaryRole = roles.contains("ADMIN") ? "ADMIN" : roles.get(0);


            // Create JWT token with username and roles
            java.util.Map<String, Object> claims = new java.util.HashMap<>();
            claims.put("sub", loginRequest.getUsername());
            claims.put("userId", user.get().getUserID());
            claims.put("roles", roles);

            Optional<String> tokenOpt = jwtTokenGenerator.generateToken(claims);

            if (tokenOpt.isEmpty()) {
                return HttpResponse.serverError("Token generation failed");
            }

            // Return token, message, AND role
            return HttpResponse.ok(
                    new LoginResponse(tokenOpt.get(), "Login Successful", primaryRole));

        } catch (RuntimeException e) {
            return HttpResponse.badRequest(
                    new LoginResponse(null, "Invalid username or password. Please try again.", null));
        }
    }

}
