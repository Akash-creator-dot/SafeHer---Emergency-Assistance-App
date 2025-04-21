package com.example.safeher;

public class User {
    private String username;
    private String password;
    public User() {
        // Required for Firebase
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getters and setters (optional but recommended)
    public String getUsername() { return username; }
    public String getPassword() { return password; }

    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }
}
