package com.ailearning.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "contents")
@EntityListeners(AuditingEntityListener.class)
public class Content {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 200)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(length = 30, nullable = false)
    private ContentType contentType;

    @Column(columnDefinition = "TEXT")
    private String textContent;

    @Size(max = 500)
    private String imageUrl;

    @Size(max = 500)
    private String videoUrl;

    @Column(columnDefinition = "TEXT")
    private String codeContent;

    @Column(columnDefinition = "TEXT")
    private String labInstructions;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private User.LearningLevel targetLevel = User.LearningLevel.BEGINNER;

    private Integer sortOrder = 0;

    private boolean active = true;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topic_id", nullable = false)
    private Topic topic;

    public enum ContentType {
        TEXT_EXPLANATION,
        IMAGE_EXPLANATION,
        TEXT_IMAGE_COMBO,
        VIDEO_TUTORIAL,
        SIMULATOR_CODE,
        QA_DISCUSSION,
        MCQ_TEST,
        LAB_INSTRUCTION
    }

    // Constructors
    public Content() {}

    public Content(String title, ContentType contentType, Topic topic) {
        this.title = title;
        this.contentType = contentType;
        this.topic = topic;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public ContentType getContentType() { return contentType; }
    public void setContentType(ContentType contentType) { this.contentType = contentType; }

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

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public Topic getTopic() { return topic; }
    public void setTopic(Topic topic) { this.topic = topic; }
}
