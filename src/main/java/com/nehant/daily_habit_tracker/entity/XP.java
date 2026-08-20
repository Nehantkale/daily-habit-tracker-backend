package com.nehant.daily_habit_tracker.entity;

import jakarta.persistence.*;

@Entity
public class XP {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long habitId;

    private int points;

    public XP() {
    }

    public XP(Long id, Long habitId, int points) {
        this.id = id;
        this.habitId = habitId;
        this.points = points;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getHabitId() {
        return habitId;
    }

    public void setHabitId(Long habitId) {
        this.habitId = habitId;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}