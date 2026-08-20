package com.nehant.daily_habit_tracker.dto;

public class LongestStreakResponse {

    private Long habitId;
    private int longestStreak;

    public LongestStreakResponse() {
    }

    public LongestStreakResponse(Long habitId, int longestStreak) {
        this.habitId = habitId;
        this.longestStreak = longestStreak;
    }

    public Long getHabitId() {
        return habitId;
    }

    public void setHabitId(Long habitId) {
        this.habitId = habitId;
    }

    public int getLongestStreak() {
        return longestStreak;
    }

    public void setLongestStreak(int longestStreak) {
        this.longestStreak = longestStreak;
    }
}