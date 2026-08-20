package com.nehant.daily_habit_tracker.controller;

import com.nehant.daily_habit_tracker.dto.DashboardResponse;
import com.nehant.daily_habit_tracker.service.DashboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/dashboard")
    public ResponseEntity<DashboardResponse> getDashboard() {

        return ResponseEntity.ok(dashboardService.getDashboard());
    }
}