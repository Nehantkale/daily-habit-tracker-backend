package com.nehant.daily_habit_tracker.dto;

public class StreakResponse {

    private Long habitId;
    private int currentStreak;

    public StreakResponse() {
    }

    public StreakResponse(Long habitId, int currentStreak) {
        this.habitId = habitId;
        this.currentStreak = currentStreak;
    }

    public Long getHabitId() {
        return habitId;
    }

    public void setHabitId(Long habitId) {
        this.habitId = habitId;
    }

    public int getCurrentStreak() {
        return currentStreak;
    }

    public void setCurrentStreak(int currentStreak) {
        this.currentStreak = currentStreak;
    }
}