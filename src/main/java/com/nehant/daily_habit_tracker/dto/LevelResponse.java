package com.nehant.daily_habit_tracker.dto;

public class LevelResponse {

    private Long habitId;
    private int totalXP;
    private int level;

    public LevelResponse() {
    }

    public LevelResponse(Long habitId, int totalXP, int level) {
        this.habitId = habitId;
        this.totalXP = totalXP;
        this.level = level;
    }

    public Long getHabitId() {
        return habitId;
    }

    public void setHabitId(Long habitId) {
        this.habitId = habitId;
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
}