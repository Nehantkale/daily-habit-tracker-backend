package com.nehant.daily_habit_tracker.dto;

import java.time.LocalDate;

public class GraphDayResponse {

    private LocalDate date;
    private int count;

    public GraphDayResponse() {
    }

    public GraphDayResponse(LocalDate date, int count) {
        this.date = date;
        this.count = count;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}