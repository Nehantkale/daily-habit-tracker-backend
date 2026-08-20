package com.nehant.daily_habit_tracker.controller;

import com.nehant.daily_habit_tracker.entity.Achievement;
import com.nehant.daily_habit_tracker.service.AchievementService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/achievements")
public class AchievementController {

    private final AchievementService achievementService;

    public AchievementController(AchievementService achievementService) {
        this.achievementService = achievementService;
    }

    @GetMapping("/{habitId}")
    public ResponseEntity<List<Achievement>> getAchievements(
            @PathVariable Long habitId) {

        return ResponseEntity.ok(
                achievementService.getAchievements(habitId)
        );
    }
}