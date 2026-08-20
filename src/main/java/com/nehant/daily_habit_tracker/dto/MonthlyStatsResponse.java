package com.nehant.daily_habit_tracker.dto;

public class MonthlyStatsResponse {

    private Long habitId;
    private int month;
    private int year;
    private int completedDays;
    private int missedDays;
    private double completionRate;

    public MonthlyStatsResponse() {
    }

    public MonthlyStatsResponse(Long habitId,
                                int month,
                                int year,
                                int completedDays,
                                int missedDays,
                                double completionRate) {
        this.habitId = habitId;
        this.month = month;
        this.year = year;
        this.completedDays = completedDays;
        this.missedDays = missedDays;
        this.completionRate = completionRate;
    }

    public Long getHabitId() {
        return habitId;
    }

    public void setHabitId(Long habitId) {
        this.habitId = habitId;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getCompletedDays() {
        return completedDays;
    }

    public void setCompletedDays(int completedDays) {
        this.completedDays = completedDays;
    }

    public int getMissedDays() {
        return missedDays;
    }

    public void setMissedDays(int missedDays) {
        this.missedDays = missedDays;
    }

    public double getCompletionRate() {
        return completionRate;
    }

    public void setCompletionRate(double completionRate) {
        this.completionRate = completionRate;
    }
}