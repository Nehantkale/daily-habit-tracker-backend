package com.nehant.daily_habit_tracker.dto;

public class XPResponse {

    private Long habitId;
    private int totalXP;

    public XPResponse() {
    }

    public XPResponse(Long habitId, int totalXP) {
        this.habitId = habitId;
        this.totalXP = totalXP;
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
}