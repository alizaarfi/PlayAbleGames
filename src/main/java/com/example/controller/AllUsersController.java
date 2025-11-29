package com.example.controller;

import com.example.repository.AllUsersRepository;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Put;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;

import java.util.*;

@Controller("/api")
@Secured(SecurityRule.IS_ANONYMOUS) //admin test - no security
public class AllUsersController {

    private final AllUsersRepository repository;

    public AllUsersController(AllUsersRepository repository) {
        this.repository = repository;
    }

    @Get("/users")
    public Map<String, Object> getAllUsers() {
        List<Map<String, Object>> users = repository.getAllUsers();

        Map<String, Object> response = new HashMap<>();
        response.put("users", users);
        return response;
    }
}
