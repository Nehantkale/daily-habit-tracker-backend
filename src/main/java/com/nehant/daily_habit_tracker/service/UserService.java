package com.nehant.daily_habit_tracker.service;

import com.nehant.daily_habit_tracker.config.JwtService;
import com.nehant.daily_habit_tracker.dto.LoginRequest;
import com.nehant.daily_habit_tracker.dto.LoginResponse;
import com.nehant.daily_habit_tracker.entity.User;
import com.nehant.daily_habit_tracker.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public User createUser(User user) {

        System.out.println("USERNAME: " + user.getUsername());
        System.out.println("EMAIL: " + user.getEmail());
        System.out.println("PASSWORD RECEIVED: " +
                (user.getPassword() == null ? "NULL" : "YES"));

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already registered");
        }

        String encodedPassword =
                passwordEncoder.encode(user.getPassword());

        user.setPassword(encodedPassword);

        System.out.println("PASSWORD AFTER ENCODING: " +
                (user.getPassword() == null ? "NULL" : "YES"));

        return userRepository.save(user);
    }

    public User getUserById(Long id) {

        return userRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));
    }

    public LoginResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new RuntimeException("Invalid email or password"));
        System.out.println("EMAIL FOUND: " + user.getEmail());
        System.out.println("PASSWORD LENGTH: " +
                (user.getPassword() == null ? "NULL" : user.getPassword().length()));

        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword())) {

            throw new RuntimeException("Invalid email or password");
        }

        String token = jwtService.generateToken(
                user.getId(),
                user.getUsername()
        );

        return new LoginResponse(
                user.getId(),
                user.getUsername(),
                "Login successful",
                token
        );
    }
}