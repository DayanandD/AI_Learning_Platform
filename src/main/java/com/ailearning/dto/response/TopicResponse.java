package com.ailearning.dto.response;

import com.ailearning.entity.Topic;
import java.time.LocalDateTime;

public class TopicResponse {
    private Long id;
    private String title;
    private String description;
    private Topic.Difficulty difficulty;
    private Integer estimatedDuration;
    private Integer sortOrder;
    private LocalDateTime createdAt;
    private Long languageId;
    private String languageName;
    private Long contentsCount;
    private UserProgressResponse userProgress;

    public TopicResponse() {}

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Topic.Difficulty getDifficulty() { return difficulty; }
    public void setDifficulty(Topic.Difficulty difficulty) { this.difficulty = difficulty; }

    public Integer getEstimatedDuration() { return estimatedDuration; }
    public void setEstimatedDuration(Integer estimatedDuration) { this.estimatedDuration = estimatedDuration; }

    public Integer getSortOrder() { return sortOrder; }
    public void setSortOrder(Integer sortOrder) { this.sortOrder = sortOrder; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public Long getLanguageId() { return languageId; }
    public void setLanguageId(Long languageId) { this.languageId = languageId; }

    public String getLanguageName() { return languageName; }
    public void setLanguageName(String languageName) { this.languageName = languageName; }

    public Long getContentsCount() { return contentsCount; }
    public void setContentsCount(Long contentsCount) { this.contentsCount = contentsCount; }

    public UserProgressResponse getUserProgress() { return userProgress; }
    public void setUserProgress(UserProgressResponse userProgress) { this.userProgress = userProgress; }
}
