package com.nehant.daily_habit_tracker.dto;

import java.util.List;

public class DashboardResponse {

    private int todayCompleted;
    private int todayTotal;
    private double completionRate;
    private List<GraphDayResponse> graph;

    private int totalXP;
    private int level;
    private int currentStreak;
    private int longestStreak;
    private int achievements;

    public DashboardResponse() {
    }

    public DashboardResponse(int todayCompleted,
                             int todayTotal,
                             double completionRate,
                             List<GraphDayResponse> graph,
                             int totalXP,
                             int level,
                             int currentStreak,
                             int longestStreak,
                             int achievements) {

        this.todayCompleted = todayCompleted;
        this.todayTotal = todayTotal;
        this.completionRate = completionRate;
        this.graph = graph;
        this.totalXP = totalXP;
        this.level = level;
        this.currentStreak = currentStreak;
        this.longestStreak = longestStreak;
        this.achievements = achievements;
    }

    public int getTodayCompleted() {
        return todayCompleted;
    }

    public void setTodayCompleted(int todayCompleted) {
        this.todayCompleted = todayCompleted;
    }

    public int getTodayTotal() {
        return todayTotal;
    }

    public void setTodayTotal(int todayTotal) {
        this.todayTotal = todayTotal;
    }

    public double getCompletionRate() {
        return completionRate;
    }

    public void setCompletionRate(double completionRate) {
        this.completionRate = completionRate;
    }

    public List<GraphDayResponse> getGraph() {
        return graph;
    }

    public void setGraph(List<GraphDayResponse> graph) {
        this.graph = graph;
    }

    public int getTotalXP() {
        return totalXP;
    }

    public void setTotalXP(int totalXP) {
        this.totalXP = totalXP;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getCurrentStreak() {
        return currentStreak;
    }

    public void setCurrentStreak(int currentStreak) {
        this.currentStreak = currentStreak;
    }

    public int getLongestStreak() {
        return longestStreak;
    }

    public void setLongestStreak(int longestStreak) {
        this.longestStreak = longestStreak;
    }

    public int getAchievements() {
        return achievements;
    }

    public void setAchievements(int achievements) {
        this.achievements = achievements;
    }
}