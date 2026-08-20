package com.nehant.daily_habit_tracker.controller;

import com.nehant.daily_habit_tracker.dto.*;
import com.nehant.daily_habit_tracker.entity.HabitCompletion;
import com.nehant.daily_habit_tracker.service.HabitCompletionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/habits")
public class HabitCompletionController {

    private final HabitCompletionService completionService;

    public HabitCompletionController(HabitCompletionService completionService) {
        this.completionService = completionService;
    }

    @PostMapping("/{id:\\d+}/complete")
    public ResponseEntity<HabitCompletion> completeHabit(
            @PathVariable Long id) {

        HabitCompletion completion =
                completionService.completeHabit(id);

        return ResponseEntity.ok(completion);
    }

    @GetMapping("/today")
    public ResponseEntity<List<TodayHabitResponse>> getTodayHabits() {

        return ResponseEntity.ok(
                completionService.getTodayHabits()
        );
    }

    @GetMapping("/{id:\\d+}/streak")
    public ResponseEntity<StreakResponse> getCurrentStreak(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                completionService.getCurrentStreak(id)
        );
    }

    @GetMapping("/{id:\\d+}/longest-streak")
    public ResponseEntity<LongestStreakResponse> getLongestStreak(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                completionService.getLongestStreak(id)
        );
    }

    @GetMapping("/graph")
    public ResponseEntity<List<GraphDayResponse>> getGraph() {

        return ResponseEntity.ok(
                completionService.getGraphData()
        );
    }

    @GetMapping("/{id:\\d+}/stats")
    public ResponseEntity<MonthlyStatsResponse> getMonthlyStats(
            @PathVariable Long id,
            @RequestParam int month,
            @RequestParam int year) {

        return ResponseEntity.ok(
                completionService.getMonthlyStats(
                        id,
                        month,
                        year
                )
        );
    }

    @GetMapping("/{id:\\d+}/stats/weekly")
    public ResponseEntity<WeeklyStatsResponse> getWeeklyStats(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                completionService.getWeeklyStats(id)
        );
    }

    @GetMapping("/history")
    public ResponseEntity<List<HabitHistoryResponse>> getHabitHistory(
            @RequestParam int month,
            @RequestParam int year) {

        return ResponseEntity.ok(
                completionService.getHabitHistory(
                        month,
                        year
                )
        );
    }
}