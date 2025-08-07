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
@Table(name = "mcq_questions")
@EntityListeners(AuditingEntityListener.class)
public class MCQQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(columnDefinition = "TEXT")
    private String question;

    @NotBlank
    @Size(max = 500)
    private String optionA;

    @NotBlank
    @Size(max = 500)
    private String optionB;

    @NotBlank
    @Size(max = 500)
    private String optionC;

    @NotBlank
    @Size(max = 500)
    private String optionD;

    @Enumerated(EnumType.STRING)
    @Column(length = 10, nullable = false)  // Changed from length = 1 to length = 10
    private CorrectOption correctAnswer;

    @Column(columnDefinition = "TEXT")
    private String explanation;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private Topic.Difficulty difficulty = Topic.Difficulty.BEGINNER;

    private Integer points = 1;

    private boolean active = true;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topic_id", nullable = false)
    private Topic topic;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<UserMCQAttempt> attempts = new HashSet<>();

    public enum CorrectOption {
        A, B, C, D
    }

    // Constructors
    public MCQQuestion() {}

    public MCQQuestion(String question, String optionA, String optionB, String optionC, String optionD, CorrectOption correctAnswer, Topic topic) {
        this.question = question;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
        this.correctAnswer = correctAnswer;
        this.topic = topic;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getQuestion() { return question; }
    public void setQuestion(String question) { this.question = question; }

    public String getOptionA() { return optionA; }
    public void setOptionA(String optionA) { this.optionA = optionA; }

    public String getOptionB() { return optionB; }
    public void setOptionB(String optionB) { this.optionB = optionB; }

    public String getOptionC() { return optionC; }
    public void setOptionC(String optionC) { this.optionC = optionC; }

    public String getOptionD() { return optionD; }
    public void setOptionD(String optionD) { this.optionD = optionD; }

    public CorrectOption getCorrectAnswer() { return correctAnswer; }
    public void setCorrectAnswer(CorrectOption correctAnswer) { this.correctAnswer = correctAnswer; }

    public String getExplanation() { return explanation; }
    public void setExplanation(String explanation) { this.explanation = explanation; }

    public Topic.Difficulty getDifficulty() { return difficulty; }
    public void setDifficulty(Topic.Difficulty difficulty) { this.difficulty = difficulty; }

    public Integer getPoints() { return points; }
    public void setPoints(Integer points) { this.points = points; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public Topic getTopic() { return topic; }
    public void setTopic(Topic topic) { this.topic = topic; }

    public Set<UserMCQAttempt> getAttempts() { return attempts; }
    public void setAttempts(Set<UserMCQAttempt> attempts) { this.attempts = attempts; }
}
