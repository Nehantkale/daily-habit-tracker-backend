package com.nehant.daily_habit_tracker.repository;

import com.nehant.daily_habit_tracker.entity.Habit;
import com.nehant.daily_habit_tracker.entity.HabitCompletion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface HabitCompletionRepository extends JpaRepository<HabitCompletion, Long> {

    Optional<HabitCompletion> findByHabitAndDate(
            Habit habit,
            LocalDate date
    );

    List<HabitCompletion> findByHabitOrderByDateDesc(
            Habit habit
    );

    List<HabitCompletion> findAllByOrderByDateAsc();

    List<HabitCompletion> findByDateBetween(
            LocalDate startDate,
            LocalDate endDate
    );
}