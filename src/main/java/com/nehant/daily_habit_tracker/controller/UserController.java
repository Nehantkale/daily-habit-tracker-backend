package com.nehant.daily_habit_tracker.controller;

import com.nehant.daily_habit_tracker.dto.LoginRequest;
import com.nehant.daily_habit_tracker.dto.LoginResponse;
import com.nehant.daily_habit_tracker.entity.User;
import com.nehant.daily_habit_tracker.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {

        System.out.println("CONTROLLER USERNAME: " + user.getUsername());
        System.out.println("CONTROLLER EMAIL: " + user.getEmail());
        System.out.println("CONTROLLER PASSWORD: " +
                (user.getPassword() == null ? "NULL" : "RECEIVED"));

        User savedUser = userService.createUser(user);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedUser);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {

        return ResponseEntity.ok(
                userService.getUserById(id)
        );
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @RequestBody LoginRequest request) {

        LoginResponse response = userService.login(request);

        return ResponseEntity.ok(response);
    }
}