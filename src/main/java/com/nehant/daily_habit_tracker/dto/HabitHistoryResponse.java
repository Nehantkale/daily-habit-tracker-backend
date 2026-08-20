package com.nehant.daily_habit_tracker.dto;

import java.time.LocalDate;
import java.util.List;

public class HabitHistoryResponse {

    private Long habitId;
    private String title;
    private List<LocalDate> completedDates;

    public HabitHistoryResponse() {
    }

    public HabitHistoryResponse(
            Long habitId,
            String title,
            List<LocalDate> completedDates) {

        this.habitId = habitId;
        this.title = title;
        this.completedDates = completedDates;
    }

    public Long getHabitId() {
        return habitId;
    }

    public void setHabitId(Long habitId) {
        this.habitId = habitId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<LocalDate> getCompletedDates() {
        return completedDates;
    }

    public void setCompletedDates(List<LocalDate> completedDates) {
        this.completedDates = completedDates;
    }
}