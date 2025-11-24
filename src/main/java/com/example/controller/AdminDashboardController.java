package com.example.controller;

import com.example.repository.AdminDashboardRepository;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.server.cors.CrossOrigin;

import java.util.HashMap;
import java.util.Map;

@Controller("/api")
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