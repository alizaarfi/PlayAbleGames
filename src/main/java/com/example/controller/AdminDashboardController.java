package com.example.controller;

import com.example.repository.AdminDashboardRepository;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.server.cors.CrossOrigin;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;

import java.util.HashMap;
import java.util.Map;

/**
 * REST controller for managing administrative dashboard operations.
 *
 * This controller provides endpoints to retrieve aggregated statistics
 * for the admin dashboard, including user counts, game counts, and
 * flagged content metrics.
 *
 */

 @Controller("/api")
@Secured(SecurityRule.IS_ANONYMOUS) //admin test - no security
public class AdminDashboardController {

    private final AdminDashboardRepository repository;

    public AdminDashboardController(AdminDashboardRepository repository) {
        this.repository = repository;
    }

    /**
     * HTTP GET endpoint at /api/admin
     *
     * - Collects aggregated counts from the repository.
     * - Builds a small JSON-like response (Map) with a "dashboard" key.
     * - Returns a Map\<String, Object\> that Micronaut will serialize to JSON.
     *
     * Response shape example:
     * {
     *   "dashboard": {
     *     "totalFlaggedItems": 5,
     *     "totalUsers": 123,
     *     "totalGames": 42
     *   }
     * }
     */

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