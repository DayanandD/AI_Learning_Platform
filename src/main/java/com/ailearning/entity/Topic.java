package com.ailearning.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "topics")
@EntityListeners(AuditingEntityListener.class)
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 200)
    private String title;

    @Size(max = 1000)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private Difficulty difficulty = Difficulty.BEGINNER;

    private Integer estimatedDuration; // in minutes

    private Integer sortOrder = 0;

    private boolean active = true;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "language_id", nullable = false)
    private Language language;

    @OneToMany(mappedBy = "topic", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Content> contents = new HashSet<>();

    @OneToMany(mappedBy = "topic", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<UserProgress> userProgress = new HashSet<>();

    @OneToMany(mappedBy = "topic", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<MCQQuestion> mcqQuestions = new HashSet<>();

    public enum Difficulty {
        BEGINNER, INTERMEDIATE, ADVANCED
    }

    // Constructors
    public Topic() {}

    public Topic(String title, String description, Language language) {
        this.title = title;
        this.description = description;
        this.language = language;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Difficulty getDifficulty() { return difficulty; }
    public void setDifficulty(Difficulty difficulty) { this.difficulty = difficulty; }

    public Integer getEstimatedDuration() { return estimatedDuration; }
    public void setEstimatedDuration(Integer estimatedDuration) { this.estimatedDuration = estimatedDuration; }

    public Integer getSortOrder() { return sortOrder; }
    public void setSortOrder(Integer sortOrder) { this.sortOrder = sortOrder; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public Language getLanguage() { return language; }
    public void setLanguage(Language language) { this.language = language; }

    public Set<Content> getContents() { return contents; }
    public void setContents(Set<Content> contents) { this.contents = contents; }

    public Set<UserProgress> getUserProgress() { return userProgress; }
    public void setUserProgress(Set<UserProgress> userProgress) { this.userProgress = userProgress; }

    public Set<MCQQuestion> getMcqQuestions() { return mcqQuestions; }
    public void setMcqQuestions(Set<MCQQuestion> mcqQuestions) { this.mcqQuestions = mcqQuestions; }
}
