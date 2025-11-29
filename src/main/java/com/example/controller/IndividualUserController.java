package com.example.controller;

import com.example.repository.IndividualUserRepository;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;

import java.util.*;

@Controller("/api")
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
}
