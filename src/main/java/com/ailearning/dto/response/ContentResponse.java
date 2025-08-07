package com.ailearning.dto.response;

import com.ailearning.entity.Content;
import com.ailearning.entity.User;
import java.time.LocalDateTime;

public class ContentResponse {
    private Long id;
    private String title;
    private Content.ContentType contentType;
    private String textContent;
    private String imageUrl;
    private String videoUrl;
    private String codeContent;
    private String labInstructions;
    private User.LearningLevel targetLevel;
    private Integer sortOrder;
    private LocalDateTime createdAt;
    private Long topicId;
    private String topicTitle;

    public ContentResponse() {}

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public Content.ContentType getContentType() { return contentType; }
    public void setContentType(Content.ContentType contentType) { this.contentType = contentType; }

    public String getTextContent() { return textContent; }
    public void setTextContent(String textContent) { this.textContent = textContent; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public String getVideoUrl() { return videoUrl; }
    public void setVideoUrl(String videoUrl) { this.videoUrl = videoUrl; }

    public String getCodeContent() { return codeContent; }
    public void setCodeContent(String codeContent) { this.codeContent = codeContent; }

    public String getLabInstructions() { return labInstructions; }
    public void setLabInstructions(String labInstructions) { this.labInstructions = labInstructions; }

    public User.LearningLevel getTargetLevel() { return targetLevel; }
    public void setTargetLevel(User.LearningLevel targetLevel) { this.targetLevel = targetLevel; }

    public Integer getSortOrder() { return sortOrder; }
    public void setSortOrder(Integer sortOrder) { this.sortOrder = sortOrder; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public Long getTopicId() { return topicId; }
    public void setTopicId(Long topicId) { this.topicId = topicId; }

    public String getTopicTitle() { return topicTitle; }
    public void setTopicTitle(String topicTitle) { this.topicTitle = topicTitle; }
}
