package com.nehant.daily_habit_tracker.dto;

public class WeeklyStatsResponse {

    private Long habitId;
    private int completedDays;
    private int totalDays;
    private double completionRate;

    public WeeklyStatsResponse() {
    }

    public WeeklyStatsResponse(Long habitId,
                               int completedDays,
                               int totalDays,
                               double completionRate) {
        this.habitId = habitId;
        this.completedDays = completedDays;
        this.totalDays = totalDays;
        this.completionRate = completionRate;
    }

    public Long getHabitId() {
        return habitId;
    }

    public void setHabitId(Long habitId) {
        this.habitId = habitId;
    }

    public int getCompletedDays() {
        return completedDays;
    }

    public void setCompletedDays(int completedDays) {
        this.completedDays = completedDays;
    }

    public int getTotalDays() {
        return totalDays;
    }

    public void setTotalDays(int totalDays) {
        this.totalDays = totalDays;
    }

    public double getCompletionRate() {
        return completionRate;
    }

    public void setCompletionRate(double completionRate) {
        this.completionRate = completionRate;
    }
}