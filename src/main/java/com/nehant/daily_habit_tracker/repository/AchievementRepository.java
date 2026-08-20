package com.nehant.daily_habit_tracker.repository;

import com.nehant.daily_habit_tracker.entity.Achievement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AchievementRepository extends JpaRepository<Achievement, Long> {

    List<Achievement> findByHabitId(Long habitId);
}