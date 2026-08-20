package com.nehant.daily_habit_tracker.entity;

import jakarta.persistence.*;

@Entity
public class Achievement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long habitId;

    private String name;

    private String description;

    private boolean unlocked;

    public Achievement() {
    }

    public Achievement(Long id,
                       Long habitId,
                       String name,
                       String description,
                       boolean unlocked) {
        this.id = id;
        this.habitId = habitId;
        this.name = name;
        this.description = description;
        this.unlocked = unlocked;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isUnlocked() {
        return unlocked;
    }

    public void setUnlocked(boolean unlocked) {
        this.unlocked = unlocked;
    }
}