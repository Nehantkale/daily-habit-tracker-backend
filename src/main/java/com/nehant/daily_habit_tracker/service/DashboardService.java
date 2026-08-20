package com.nehant.daily_habit_tracker.service;

import com.nehant.daily_habit_tracker.dto.DashboardResponse;
import com.nehant.daily_habit_tracker.dto.GraphDayResponse;
import com.nehant.daily_habit_tracker.dto.TodayHabitResponse;
import com.nehant.daily_habit_tracker.entity.Achievement;
import com.nehant.daily_habit_tracker.entity.Habit;
import com.nehant.daily_habit_tracker.entity.User;
import com.nehant.daily_habit_tracker.repository.HabitRepository;
import com.nehant.daily_habit_tracker.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DashboardService {

    private final HabitCompletionService completionService;
    private final XPService xpService;
    private final AchievementService achievementService;
    private final HabitRepository habitRepository;
    private final UserRepository userRepository;

    public DashboardService(
            HabitCompletionService completionService,
            XPService xpService,
            AchievementService achievementService,
            HabitRepository habitRepository,
            UserRepository userRepository) {

        this.completionService = completionService;
        this.xpService = xpService;
        this.achievementService = achievementService;
        this.habitRepository = habitRepository;
        this.userRepository = userRepository;
    }

    public DashboardResponse getDashboard() {

        // Today's habits for current user
        List<TodayHabitResponse> todayHabits =
                completionService.getTodayHabits();

        int total = todayHabits.size();

        int completed = 0;

        for (TodayHabitResponse habit : todayHabits) {

            if (habit.isCompleted()) {
                completed++;
            }
        }

        double completionRate = 0;

        if (total > 0) {
            completionRate = Math.round(
                    ((completed * 100.0) / total) * 100.0
            ) / 100.0;
        }

        // User-specific graph
        List<GraphDayResponse> graph =
                completionService.getGraphData();

        // Get logged-in user
        String username =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        // Get all habits belonging to current user
        List<Habit> habits =
                habitRepository.findByUser(user);

        int totalXP = 0;
        int currentStreak = 0;
        int longestStreak = 0;
        int achievements = 0;

        for (Habit habit : habits) {

            Long habitId = habit.getId();

            // XP
            totalXP += xpService.getTotalXP(habitId);

            // Current streak
            int habitCurrentStreak =
                    completionService
                            .getCurrentStreak(habitId)
                            .getCurrentStreak();

            currentStreak =
                    Math.max(
                            currentStreak,
                            habitCurrentStreak
                    );

            // Longest streak
            int habitLongestStreak =
                    completionService
                            .getLongestStreak(habitId)
                            .getLongestStreak();

            longestStreak =
                    Math.max(
                            longestStreak,
                            habitLongestStreak
                    );

            // Achievements
            List<Achievement> achievementList =
                    achievementService
                            .getAchievements(habitId);

            achievements += achievementList.size();
        }

        // Calculate level from total XP
        int level;

        if (totalXP >= 1000) {
            level = 5;
        } else if (totalXP >= 500) {
            level = 4;
        } else if (totalXP >= 250) {
            level = 3;
        } else if (totalXP >= 100) {
            level = 2;
        } else {
            level = 1;
        }

        return new DashboardResponse(
                completed,
                total,
                completionRate,
                graph,
                totalXP,
                level,
                currentStreak,
                longestStreak,
                achievements
        );
    }
}