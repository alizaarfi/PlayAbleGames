package com.example.controller;

import com.example.repository.IndividualUserRepository;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;

import java.util.*;

@Controller("/api")
@Secured(SecurityRule.IS_ANONYMOUS) //admin test - no security
public class IndividualUserController {

    private final IndividualUserRepository repository;

    public IndividualUserController(IndividualUserRepository repository) {
        this.repository = repository;
    }

    @Get("/users/{userId}")
    public HttpResponse<Map<String, Object>> getUserById(@PathVariable int userId) {
        Map<String, Object> userData = repository.getUserWithReviews(userId);

        if (userData == null) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "User not found");
            errorResponse.put("userId", userId);
            return HttpResponse.notFound(errorResponse);
        }

        return HttpResponse.ok(userData);
    }

    @Post("/users/{userId}/ban")
    public HttpResponse<Map<String, Object>> banUser(@PathVariable int userId) {
        boolean success = repository.banUser(userId);

        Map<String, Object> response = new HashMap<>();
        if (success) {
            response.put("message", "User banned successfully");
            response.put("userId", userId);
            return HttpResponse.ok(response);
        } else {
            response.put("error", "Failed to ban user");
            response.put("userId", userId);
            return HttpResponse.serverError(response);  // Changed this line
        }
    }
}
