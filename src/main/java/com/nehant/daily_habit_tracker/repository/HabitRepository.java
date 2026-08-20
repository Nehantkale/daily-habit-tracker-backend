package com.nehant.daily_habit_tracker.repository;

import com.nehant.daily_habit_tracker.entity.Habit;
import com.nehant.daily_habit_tracker.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HabitRepository extends JpaRepository<Habit, Long> {

    List<Habit> findByActiveTrue();

    List<Habit> findByUser(User user);
}