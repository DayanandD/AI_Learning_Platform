package com.ailearning.entity;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_mcq_attempts")
@EntityListeners(AuditingEntityListener.class)
public class UserMCQAttempt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 10, nullable = false)  // Changed from length = 1 to length = 10
    private MCQQuestion.CorrectOption selectedAnswer;

    private boolean isCorrect;

    private Integer pointsEarned = 0;

    private Integer timeSpentSeconds;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime attemptedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    private MCQQuestion question;

    // Constructors
    public UserMCQAttempt() {}

    public UserMCQAttempt(User user, MCQQuestion question, MCQQuestion.CorrectOption selectedAnswer) {
        this.user = user;
        this.question = question;
        this.selectedAnswer = selectedAnswer;
        this.isCorrect = question.getCorrectAnswer() == selectedAnswer;
        this.pointsEarned = this.isCorrect ? question.getPoints() : 0;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public MCQQuestion.CorrectOption getSelectedAnswer() { return selectedAnswer; }
    public void setSelectedAnswer(MCQQuestion.CorrectOption selectedAnswer) { this.selectedAnswer = selectedAnswer; }

    public boolean isCorrect() { return isCorrect; }
    public void setCorrect(boolean correct) { isCorrect = correct; }

    public Integer getPointsEarned() { return pointsEarned; }
    public void setPointsEarned(Integer pointsEarned) { this.pointsEarned = pointsEarned; }

    public Integer getTimeSpentSeconds() { return timeSpentSeconds; }
    public void setTimeSpentSeconds(Integer timeSpentSeconds) { this.timeSpentSeconds = timeSpentSeconds; }

    public LocalDateTime getAttemptedAt() { return attemptedAt; }
    public void setAttemptedAt(LocalDateTime attemptedAt) { this.attemptedAt = attemptedAt; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public MCQQuestion getQuestion() { return question; }
    public void setQuestion(MCQQuestion question) { this.question = question; }
}
