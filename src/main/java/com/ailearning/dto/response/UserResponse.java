package com.ailearning.dto.response;

import com.ailearning.entity.User;
import java.time.LocalDateTime;

public class UserResponse {
    private Long id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private User.Role role;
    private User.LearningLevel learningLevel;
    private LocalDateTime createdAt;

    public UserResponse() {}

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public User.Role getRole() { return role; }
    public void setRole(User.Role role) { this.role = role; }

    public User.LearningLevel getLearningLevel() { return learningLevel; }
    public void setLearningLevel(User.LearningLevel learningLevel) { this.learningLevel = learningLevel; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
