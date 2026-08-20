package com.nehant.daily_habit_tracker.repository;

import com.nehant.daily_habit_tracker.entity.XP;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface XPRepository extends JpaRepository<XP, Long> {

    List<XP> findByHabitId(Long habitId);
}