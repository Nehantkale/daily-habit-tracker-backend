package com.nehant.daily_habit_tracker.service;

import com.nehant.daily_habit_tracker.dto.*;
import com.nehant.daily_habit_tracker.entity.Habit;
import com.nehant.daily_habit_tracker.entity.HabitCompletion;
import com.nehant.daily_habit_tracker.entity.User;
import com.nehant.daily_habit_tracker.exception.HabitAlreadyCompletedException;
import com.nehant.daily_habit_tracker.exception.HabitNotFoundException;
import com.nehant.daily_habit_tracker.repository.HabitCompletionRepository;
import com.nehant.daily_habit_tracker.repository.HabitRepository;
import com.nehant.daily_habit_tracker.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class HabitCompletionService {

    private final HabitRepository habitRepository;
    private final HabitCompletionRepository completionRepository;
    private final UserRepository userRepository;
    private final XPService xpService;
    private final AchievementService achievementService;

    public HabitCompletionService(HabitRepository habitRepository,
                                  HabitCompletionRepository completionRepository,
                                  UserRepository userRepository,
                                  XPService xpService,
                                  AchievementService achievementService) {

        this.habitRepository = habitRepository;
        this.completionRepository = completionRepository;
        this.userRepository = userRepository;
        this.xpService = xpService;
        this.achievementService = achievementService;
    }

    public HabitCompletion completeHabit(Long habitId) {

        Habit habit = getOwnedHabit(habitId);

        if (completionRepository
                .findByHabitAndDate(habit, LocalDate.now())
                .isPresent()) {

            throw new HabitAlreadyCompletedException();
        }

        HabitCompletion completion = new HabitCompletion();

        completion.setHabit(habit);
        completion.setDate(LocalDate.now());
        completion.setCompleted(true);

        HabitCompletion savedCompletion =
                completionRepository.save(completion);

        // Give 10 XP
        xpService.addXP(habitId, 10);

        // First Habit achievement
        achievementService.unlockFirstHabit(habitId);

        // Check current XP
        int totalXP = xpService.getTotalXP(habitId);

        // Unlock 100 XP achievement
        if (totalXP >= 100) {
            achievementService.unlock100XP(habitId);
        }

        // Check current streak
        StreakResponse streakResponse =
                getCurrentStreak(habitId);

        // Unlock 7-day achievement
        if (streakResponse.getCurrentStreak() >= 7) {
            achievementService.unlockSevenDayStreak(habitId);
        }

        return savedCompletion;
    }

    public List<TodayHabitResponse> getTodayHabits() {

        User user = getCurrentUser();

        List<Habit> habits =
                habitRepository.findByUser(user)
                        .stream()
                        .filter(Habit::isActive)
                        .toList();

        List<TodayHabitResponse> response =
                new ArrayList<>();

        for (Habit habit : habits) {

            boolean completed =
                    completionRepository
                            .findByHabitAndDate(
                                    habit,
                                    LocalDate.now()
                            )
                            .isPresent();

            TodayHabitResponse dto =
                    new TodayHabitResponse(
                            habit.getId(),
                            habit.getTitle(),
                            completed
                    );

            response.add(dto);
        }

        return response;
    }

    public StreakResponse getCurrentStreak(Long habitId) {

        Habit habit = getOwnedHabit(habitId);

        List<HabitCompletion> completions =
                completionRepository
                        .findByHabitOrderByDateDesc(habit);

        int streak = 0;

        LocalDate expectedDate = LocalDate.now();

        for (HabitCompletion completion : completions) {

            if (completion.getDate().equals(expectedDate)) {

                streak++;

                expectedDate =
                        expectedDate.minusDays(1);

            } else {
                break;
            }
        }

        return new StreakResponse(habitId, streak);
    }

    public LongestStreakResponse getLongestStreak(Long habitId) {

        Habit habit = getOwnedHabit(habitId);

        List<HabitCompletion> completions =
                completionRepository
                        .findByHabitOrderByDateDesc(habit);

        if (completions.isEmpty()) {
            return new LongestStreakResponse(
                    habitId,
                    0
            );
        }

        int longestStreak = 1;
        int currentStreak = 1;

        for (int i = 1; i < completions.size(); i++) {

            LocalDate currentDate =
                    completions.get(i - 1).getDate();

            LocalDate nextDate =
                    completions.get(i).getDate();

            if (currentDate.minusDays(1)
                    .equals(nextDate)) {

                currentStreak++;

            } else {

                longestStreak =
                        Math.max(
                                longestStreak,
                                currentStreak
                        );

                currentStreak = 1;
            }
        }

        longestStreak =
                Math.max(
                        longestStreak,
                        currentStreak
                );

        return new LongestStreakResponse(
                habitId,
                longestStreak
        );
    }

    public List<GraphDayResponse> getGraphData() {

        User user = getCurrentUser();

        List<HabitCompletion> completions =
                completionRepository
                        .findAllByOrderByDateAsc();

        Map<LocalDate, Integer> graph =
                new LinkedHashMap<>();

        for (HabitCompletion completion : completions) {

            Habit habit = completion.getHabit();

            if (habit.getUser() == null ||
                    !habit.getUser()
                            .getId()
                            .equals(user.getId())) {

                continue;
            }

            LocalDate date =
                    completion.getDate();

            graph.put(
                    date,
                    graph.getOrDefault(date, 0) + 1
            );
        }

        List<GraphDayResponse> response =
                new ArrayList<>();

        if (graph.isEmpty()) {
            return response;
        }

        LocalDate firstDate =
                graph.keySet()
                        .iterator()
                        .next();

        LocalDate lastDate =
                graph.keySet()
                        .stream()
                        .reduce(
                                (first, second) -> second
                        )
                        .orElse(firstDate);

        LocalDate currentDate = firstDate;

        while (!currentDate.isAfter(lastDate)) {

            int count =
                    graph.getOrDefault(
                            currentDate,
                            0
                    );

            response.add(
                    new GraphDayResponse(
                            currentDate,
                            count
                    )
            );

            currentDate =
                    currentDate.plusDays(1);
        }

        return response;
    }

    public MonthlyStatsResponse getMonthlyStats(
            Long habitId,
            int month,
            int year) {

        Habit habit = getOwnedHabit(habitId);

        LocalDate startDate =
                LocalDate.of(year, month, 1);

        LocalDate endDate =
                startDate.withDayOfMonth(
                        startDate.lengthOfMonth()
                );

        List<HabitCompletion> completions =
                completionRepository
                        .findByHabitOrderByDateDesc(habit);

        int completedDays = 0;

        for (HabitCompletion completion : completions) {

            LocalDate date =
                    completion.getDate();

            if (!date.isBefore(startDate)
                    && !date.isAfter(endDate)) {

                completedDays++;
            }
        }

        int totalDays =
                startDate.lengthOfMonth();

        int missedDays =
                totalDays - completedDays;

        double completionRate =
                Math.round(
                        ((completedDays * 100.0)
                                / totalDays) * 100.0
                ) / 100.0;

        return new MonthlyStatsResponse(
                habitId,
                month,
                year,
                completedDays,
                missedDays,
                completionRate
        );
    }

    public WeeklyStatsResponse getWeeklyStats(
            Long habitId) {

        Habit habit = getOwnedHabit(habitId);

        LocalDate endDate =
                LocalDate.now();

        LocalDate startDate =
                endDate.minusDays(6);

        List<HabitCompletion> completions =
                completionRepository
                        .findByHabitOrderByDateDesc(habit);

        int completedDays = 0;

        for (HabitCompletion completion : completions) {

            LocalDate date =
                    completion.getDate();

            if (!date.isBefore(startDate)
                    && !date.isAfter(endDate)) {

                completedDays++;
            }
        }

        int totalDays = 7;

        double completionRate =
                Math.round(
                        ((completedDays * 100.0)
                                / totalDays) * 100.0
                ) / 100.0;

        return new WeeklyStatsResponse(
                habitId,
                completedDays,
                totalDays,
                completionRate
        );
    }

    // -----------------------------
    // Security helper methods
    // -----------------------------

    private User getCurrentUser() {

        String username =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getName();

        return userRepository
                .findByUsername(username)
                .orElseThrow(() ->
                        new RuntimeException(
                                "User not found"
                        ));
    }

    private Habit getOwnedHabit(Long habitId) {

        Habit habit =
                habitRepository.findById(habitId)
                        .orElseThrow(() ->
                                new HabitNotFoundException(
                                        habitId
                                ));

        User currentUser = getCurrentUser();

        if (habit.getUser() == null ||
                !habit.getUser()
                        .getId()
                        .equals(currentUser.getId())) {

            throw new RuntimeException(
                    "You do not own this habit"
            );
        }

        return habit;
    }
    public List<HabitHistoryResponse> getHabitHistory(
            int month,
            int year) {

        LocalDate startDate = LocalDate.of(year, month, 1);

        LocalDate endDate =
                startDate.withDayOfMonth(
                        startDate.lengthOfMonth()
                );

        // Get all active habits
        List<Habit> habits =
                habitRepository.findByActiveTrue();

        // Get completions for this month
        List<HabitCompletion> completions =
                completionRepository.findByDateBetween(
                        startDate,
                        endDate
                );

        Map<Long, HabitHistoryResponse> history =
                new LinkedHashMap<>();

        // First create an entry for EVERY habit
        for (Habit habit : habits) {

            history.put(
                    habit.getId(),
                    new HabitHistoryResponse(
                            habit.getId(),
                            habit.getTitle(),
                            new ArrayList<>()
                    )
            );
        }

        // Then add completed dates
        for (HabitCompletion completion : completions) {

            Habit habit = completion.getHabit();

            Long habitId = habit.getId();

            if (history.containsKey(habitId)) {

                history.get(habitId)
                        .getCompletedDates()
                        .add(completion.getDate());
            }
        }

        return new ArrayList<>(history.values());
    }
}