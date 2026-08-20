package com.nehant.daily_habit_tracker.service;

import com.nehant.daily_habit_tracker.entity.Achievement;
import com.nehant.daily_habit_tracker.entity.Habit;
import com.nehant.daily_habit_tracker.entity.User;
import com.nehant.daily_habit_tracker.exception.HabitNotFoundException;
import com.nehant.daily_habit_tracker.repository.AchievementRepository;
import com.nehant.daily_habit_tracker.repository.HabitRepository;
import com.nehant.daily_habit_tracker.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AchievementService {

    private final AchievementRepository achievementRepository;
    private final HabitRepository habitRepository;
    private final UserRepository userRepository;

    public AchievementService(
            AchievementRepository achievementRepository,
            HabitRepository habitRepository,
            UserRepository userRepository) {

        this.achievementRepository = achievementRepository;
        this.habitRepository = habitRepository;
        this.userRepository = userRepository;
    }

    public Achievement unlockFirstHabit(Long habitId) {

        getOwnedHabit(habitId);

        List<Achievement> existingAchievements =
                achievementRepository.findByHabitId(habitId);

        for (Achievement achievement : existingAchievements) {

            if (achievement.getName().equals("First Habit")) {
                return achievement;
            }
        }

        Achievement achievement = new Achievement();

        achievement.setHabitId(habitId);
        achievement.setName("First Habit");
        achievement.setDescription("Completed your first habit");
        achievement.setUnlocked(true);

        return achievementRepository.save(achievement);
    }

    public List<Achievement> getAchievements(Long habitId) {

        getOwnedHabit(habitId);

        return achievementRepository.findByHabitId(habitId);
    }

    public Achievement unlockSevenDayStreak(Long habitId) {

        getOwnedHabit(habitId);

        List<Achievement> existingAchievements =
                achievementRepository.findByHabitId(habitId);

        for (Achievement achievement : existingAchievements) {

            if (achievement.getName().equals("7 Day Streak")) {
                return achievement;
            }
        }

        Achievement achievement = new Achievement();

        achievement.setHabitId(habitId);
        achievement.setName("7 Day Streak");
        achievement.setDescription("Maintained a 7-day habit streak");
        achievement.setUnlocked(true);

        return achievementRepository.save(achievement);
    }

    public Achievement unlock100XP(Long habitId) {

        getOwnedHabit(habitId);

        List<Achievement> existingAchievements =
                achievementRepository.findByHabitId(habitId);

        for (Achievement achievement : existingAchievements) {

            if (achievement.getName().equals("100 XP")) {
                return achievement;
            }
        }

        Achievement achievement = new Achievement();

        achievement.setHabitId(habitId);
        achievement.setName("100 XP");
        achievement.setDescription("Earned 100 XP");
        achievement.setUnlocked(true);

        return achievementRepository.save(achievement);
    }

    private Habit getOwnedHabit(Long habitId) {

        Habit habit = habitRepository.findById(habitId)
                .orElseThrow(() ->
                        new HabitNotFoundException(habitId));

        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        if (habit.getUser() == null ||
                !habit.getUser()
                        .getId()
                        .equals(user.getId())) {

            throw new RuntimeException(
                    "You do not own this habit"
            );
        }

        return habit;
    }
}