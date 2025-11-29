package com.example.controller;

import com.example.repository.AdminDashboardRepository;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.server.cors.CrossOrigin;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;

import java.util.HashMap;
import java.util.Map;

@Controller("/api")
@Secured(SecurityRule.IS_ANONYMOUS) //admin test - no security
public class AdminDashboardController {

    private final AdminDashboardRepository repository;

    public AdminDashboardController(AdminDashboardRepository repository) {
        this.repository = repository;
    }

    @Get("/admin")
    public Map<String, Object> getDashboardData() {
        Map<String, Object> dashboard = new HashMap<>();
        dashboard.put("totalFlaggedItems", repository.getTotalFlagged());
        dashboard.put("totalUsers", repository.getTotalUsers());
        dashboard.put("totalGames", repository.getTotalGames());

        Map<String, Object> response = new HashMap<>();
        response.put("dashboard", dashboard);
        return response;
    }
}