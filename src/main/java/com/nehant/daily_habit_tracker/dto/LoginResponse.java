package com.nehant.daily_habit_tracker.dto;

public class LoginResponse {

    private Long userId;
    private String username;
    private String message;
    private String token;

    public LoginResponse() {
    }

    public LoginResponse(Long userId,
                         String username,
                         String message,
                         String token) {
        this.userId = userId;
        this.username = username;
        this.message = message;
        this.token = token;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}