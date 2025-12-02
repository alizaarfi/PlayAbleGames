package com.example.controller;

import com.example.repository.AllUsersRepository;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Put;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;

import java.util.*;

/**
 * REST controller that exposes user listing endpoints.
 *
 * This controller delegates data access to the AllUsersRepository and
 * returns simple Map-based responses that Micronaut will serialize to JSON.
 */

@Controller("/api")
@Secured(SecurityRule.IS_ANONYMOUS) //admin test - no security
public class AllUsersController {

    private final AllUsersRepository repository;

    public AllUsersController(AllUsersRepository repository) {
        this.repository = repository;
    }


    /**
     * HTTP GET /api/users
     *
     * - Calls repository.getAllUsers() to fetch a list of user rows.
     * - Wraps the list in a top-level Map under the \"user\" key so the response
     *   shape matches what the frontend/tests expect.
     *
     * Response shape example:
     * {
     *   "user": [
     *     { "id": 1, "username": "alice", "email": "alice@example.com", "enabled": true },
     *     ...
     *   ]
     * }
     */

    @Get("/users")
    public Map<String, Object> getAllUsers() {
        List<Map<String, Object>> users = repository.getAllUsers();

        Map<String, Object> response = new HashMap<>();
        response.put("user", users);
        return response;
    }
}
