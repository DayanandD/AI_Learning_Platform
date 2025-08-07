package com.ailearning.dto.response;

import com.ailearning.entity.UserProgress;
import java.time.LocalDateTime;

public class UserProgressResponse {
    private Long id;
    private UserProgress.ProgressStatus status;
    private Integer progressPercentage;
    private Integer timeSpentMinutes;
    private LocalDateTime lastAccessedAt;
    private LocalDateTime completedAt;
    private LocalDateTime createdAt;
    private Long topicId;
    private String topicTitle;

    public UserProgressResponse() {}

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public UserProgress.ProgressStatus getStatus() { return status; }
    public void setStatus(UserProgress.ProgressStatus status) { this.status = status; }

    public Integer getProgressPercentage() { return progressPercentage; }
    public void setProgressPercentage(Integer progressPercentage) { this.progressPercentage = progressPercentage; }

    public Integer getTimeSpentMinutes() { return timeSpentMinutes; }
    public void setTimeSpentMinutes(Integer timeSpentMinutes) { this.timeSpentMinutes = timeSpentMinutes; }

    public LocalDateTime getLastAccessedAt() { return lastAccessedAt; }
    public void setLastAccessedAt(LocalDateTime lastAccessedAt) { this.lastAccessedAt = lastAccessedAt; }

    public LocalDateTime getCompletedAt() { return completedAt; }
    public void setCompletedAt(LocalDateTime completedAt) { this.completedAt = completedAt; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public Long getTopicId() { return topicId; }
    public void setTopicId(Long topicId) { this.topicId = topicId; }

    public String getTopicTitle() { return topicTitle; }
    public void setTopicTitle(String topicTitle) { this.topicTitle = topicTitle; }
}
