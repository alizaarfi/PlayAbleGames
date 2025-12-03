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


/**
 * Controller handling operations for a single user and their reviews.
 *
 * - Exposes endpoints to fetch a user's details (including reviews) and to ban a user.
 * - Uses constructor injection to obtain the repository that performs data access.
 * - Returns Micronaut objects so appropriate HTTP status codes and bodies can be produced.
 */

@Controller("/api")
public class IndividualUserController {

    private final IndividualUserRepository repository;

    public IndividualUserController(IndividualUserRepository repository) {
        this.repository = repository;
    }

    /**
     * HTTP GET /api/users/{userId}
     *
     * - Retrieves a Map\<String,Object\> representing the user and their reviews.
     * - If the user is not found, returns 404 Not Found with an error payload.
     * - If found, returns 200 OK with the user data (Micronaut will serialize the Map to JSON).
     */

    @Get("/users/{userId}")
    @Secured("ADMIN")
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


    /**
     * HTTP POST /api/users/{userId}/ban
     *
     * - Attempts to ban the user via the repository. The repository returns a boolean indicating success.
     * - On success: returns 200 OK with a confirmation message and the affected userId.
     * - On failure: returns 500 Server Error with an error payload. (Adjust status and messages as needed.)
     */

    @Post("/users/{userId}/ban")
    @Secured("ADMIN")
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
            return HttpResponse.serverError(response);
        }
    }
}
