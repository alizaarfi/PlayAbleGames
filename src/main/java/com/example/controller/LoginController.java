package com.example.controller;

import com.example.api.LoginRequest;
import com.example.api.LoginResponse;
import com.example.domain.User;
import com.example.service.UserService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;

import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import io.micronaut.security.token.jwt.generator.JwtTokenGenerator;
import jakarta.inject.Inject;

import java.util.HashMap;
import java.util.List;
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
    private final JwtTokenGenerator jwtTokenGenerator;

    @Inject
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

        System.out.println("=== LOGIN ATTEMPT ===");
        System.out.println("Username: " + loginRequest.getUsername());

        //validation
        if (loginRequest == null || loginRequest.getUsername() == null || loginRequest.getPassword() == null) {
            System.out.println("ERROR: Missing username or password");
            return HttpResponse.badRequest(
                    new LoginResponse(null, "Missing username or password", null));
        }

        try {
            //Authenticate user controller -> service
            Optional<User> userOpt = userService.authenticate(
                    loginRequest.getUsername(),
                    loginRequest.getPassword()
            );

            if (userOpt.isEmpty()) {
                System.out.println("ERROR: User authentication failed");
                return HttpResponse.badRequest(
                        new LoginResponse(null, "Invalid username or password", null));
            }
            User user = userOpt.get();
            System.out.println("User authenticated: " + user.getUsername() + " (ID: " + user.getUserID() + ")");

            //get user roles from database (controller -> service-> repository)
            List<String> roles = userService.getUserRoles(user.getUserID());
            System.out.println("User roles: " + roles);

            if (roles.isEmpty()) {
                System.out.println("ERROR: User has no roles");
                return HttpResponse.badRequest(
                        new LoginResponse(null, "User has no assigned roles", null));
            }

            // determine primary role (Admin takes precedence)
            String primaryRole = roles.contains("ADMIN") ? "ADMIN" : "BASIC";
            System.out.println("Primary role: " + primaryRole);

            //generate JWT token
            Map<String, Object> claims = new HashMap<>();
            claims.put("sub", user.getUsername());
            claims.put("userId", user.getUserID());
            claims.put("roles", roles);

            Optional<String> tokenOpt = jwtTokenGenerator.generateToken(claims);

            if (tokenOpt.isEmpty()) {
                System.out.println("ERROR: Token generation failed");
                return HttpResponse.serverError("Token generation failed");
            }

            System.out.println("Login successful! Token generated.");

            //return response with token and role
            return HttpResponse.ok(new LoginResponse(tokenOpt.get(), "Login Successful", primaryRole));
        } catch (RuntimeException e) {
            System.out.println("ERROR: Exception during login: " + e.getMessage());
            e.printStackTrace();

            return HttpResponse.badRequest(
                    new LoginResponse(null, "Invalid username or password", null)
            );
        }
    }

}
