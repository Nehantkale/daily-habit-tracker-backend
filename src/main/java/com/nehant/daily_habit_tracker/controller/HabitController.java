package com.nehant.daily_habit_tracker.controller;

import com.nehant.daily_habit_tracker.entity.Habit;
import com.nehant.daily_habit_tracker.service.HabitService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/habits")
public class HabitController {

    private final HabitService habitService;

    public HabitController(HabitService habitService) {
        this.habitService = habitService;
    }

    @GetMapping
    public ResponseEntity<List<Habit>> getAllHabits() {
        return ResponseEntity.ok(habitService.getAllHabits());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Habit> getHabitById(@PathVariable Long id) {
        return ResponseEntity.ok(habitService.getHabitById(id));
    }

    @PostMapping
    public ResponseEntity<Habit> createHabit(@Valid @RequestBody Habit habit) {

        Habit savedHabit = habitService.createHabit(habit);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedHabit);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Habit> updateHabit(
            @PathVariable Long id,
            @Valid @RequestBody Habit updatedHabit) {

        Habit habit = habitService.updateHabit(id, updatedHabit);

        return ResponseEntity.ok(habit);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteHabit(@PathVariable Long id) {

        habitService.deleteHabit(id);

        return ResponseEntity.ok("Habit deleted successfully");
    }

    @PutMapping("/{id}/activate")
    public ResponseEntity<Habit> activateHabit(@PathVariable Long id) {

        Habit habit = habitService.activateHabit(id);

        return ResponseEntity.ok(habit);
    }

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Habit Controller Working");
    }

}