package com.example.controller;

import com.example.repository.AdminDashboardRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AdminDashboardControllerTest {

    @Mock
    AdminDashboardRepository repository;

    @Test
    public void getDashboardData_returnsExpectedMap() {
        when(repository.getTotalFlagged()).thenReturn(2);
        when(repository.getTotalUsers()).thenReturn(10);
        when(repository.getTotalGames()).thenReturn(5);

        AdminDashboardController controller = new AdminDashboardController(repository);
        Map<String, Object> response = controller.getDashboardData();

        assertTrue(response.containsKey("dashboard"));
        Object dashboardObj = response.get("dashboard");
        assertTrue(dashboardObj instanceof Map);

        @SuppressWarnings("unchecked")
        Map<String, Object> dashboard = (Map<String, Object>) dashboardObj;
        assertEquals(2, dashboard.get("totalFlaggedItems"));
        assertEquals(10, dashboard.get("totalUsers"));
        assertEquals(5, dashboard.get("totalGames"));
    }
}