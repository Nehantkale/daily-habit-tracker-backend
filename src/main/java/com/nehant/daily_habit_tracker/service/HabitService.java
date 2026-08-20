package com.nehant.daily_habit_tracker.service;

import com.nehant.daily_habit_tracker.entity.Habit;
import com.nehant.daily_habit_tracker.entity.User;
import com.nehant.daily_habit_tracker.exception.HabitNotFoundException;
import com.nehant.daily_habit_tracker.repository.HabitRepository;
import com.nehant.daily_habit_tracker.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HabitService {

    private final HabitRepository repository;
    private final UserRepository userRepository;

    public HabitService(HabitRepository repository,
                        UserRepository userRepository) {

        this.repository = repository;
        this.userRepository = userRepository;
    }

    public List<Habit> getAllHabits() {

        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        return repository.findByUser(user);
    }

    public Habit createHabit(Habit habit) {

        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        habit.setUser(user);

        return repository.save(habit);
    }

    public Habit getHabitById(Long id) {

        Habit habit = repository.findById(id)
                .orElseThrow(() -> new HabitNotFoundException(id));

        checkOwnership(habit);

        return habit;
    }

    public Habit updateHabit(Long id, Habit updatedHabit) {

        Habit habit = repository.findById(id)
                .orElseThrow(() -> new HabitNotFoundException(id));

        checkOwnership(habit);

        habit.setTitle(updatedHabit.getTitle());
        habit.setDescription(updatedHabit.getDescription());
        habit.setActive(updatedHabit.isActive());

        return repository.save(habit);
    }

    public void deleteHabit(Long id) {

        Habit habit = repository.findById(id)
                .orElseThrow(() -> new HabitNotFoundException(id));

        checkOwnership(habit);

        habit.setActive(false);

        repository.save(habit);
    }

    public Habit activateHabit(Long id) {

        Habit habit = repository.findById(id)
                .orElseThrow(() -> new HabitNotFoundException(id));

        checkOwnership(habit);

        habit.setActive(true);

        return repository.save(habit);
    }

    private void checkOwnership(Habit habit) {

        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        if (!habit.getUser().getUsername().equals(username)) {
            throw new RuntimeException("You do not own this habit");
        }
    }
}