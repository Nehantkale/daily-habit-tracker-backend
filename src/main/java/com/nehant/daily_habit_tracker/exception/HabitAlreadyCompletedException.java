package com.nehant.daily_habit_tracker.exception;

public class HabitAlreadyCompletedException extends RuntimeException {

    public HabitAlreadyCompletedException() {
        super("Habit already completed today");
    }
}