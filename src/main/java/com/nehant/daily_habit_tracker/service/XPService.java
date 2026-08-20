package com.nehant.daily_habit_tracker.service;

import com.nehant.daily_habit_tracker.entity.Habit;
import com.nehant.daily_habit_tracker.entity.XP;
import com.nehant.daily_habit_tracker.entity.User;
import com.nehant.daily_habit_tracker.exception.HabitNotFoundException;
import com.nehant.daily_habit_tracker.repository.HabitRepository;
import com.nehant.daily_habit_tracker.repository.XPRepository;
import com.nehant.daily_habit_tracker.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class XPService {

    private final XPRepository xpRepository;
    private final HabitRepository habitRepository;
    private final UserRepository userRepository;

    public XPService(XPRepository xpRepository,
                     HabitRepository habitRepository,
                     UserRepository userRepository) {

        this.xpRepository = xpRepository;
        this.habitRepository = habitRepository;
        this.userRepository = userRepository;
    }

    public XP addXP(Long habitId, int points) {

        getOwnedHabit(habitId);

        XP xp = new XP();

        xp.setHabitId(habitId);
        xp.setPoints(points);

        return xpRepository.save(xp);
    }

    public int getTotalXP(Long habitId) {

        getOwnedHabit(habitId);

        List<XP> xpList =
                xpRepository.findByHabitId(habitId);

        int totalXP = 0;

        for (XP xp : xpList) {
            totalXP += xp.getPoints();
        }

        return totalXP;
    }

    public int getLevel(Long habitId) {

        int totalXP = getTotalXP(habitId);

        if (totalXP >= 1000) {
            return 5;
        } else if (totalXP >= 500) {
            return 4;
        } else if (totalXP >= 250) {
            return 3;
        } else if (totalXP >= 100) {
            return 2;
        } else {
            return 1;
        }
    }

    private Habit getOwnedHabit(Long habitId) {

        Habit habit = habitRepository.findById(habitId)
                .orElseThrow(() ->
                        new HabitNotFoundException(habitId));

        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        if (habit.getUser() == null ||
                !habit.getUser()
                        .getId()
                        .equals(user.getId())) {

            throw new RuntimeException(
                    "You do not own this habit"
            );
        }

        return habit;
    }
}