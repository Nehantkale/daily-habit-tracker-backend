package com.nehant.daily_habit_tracker.exception;

public class HabitNotFoundException extends RuntimeException {

    public HabitNotFoundException(Long id) {
        super("Habit not found with id: " + id);
    }
}