package com.ailearning.dto.response;

import com.ailearning.entity.MCQQuestion;
import com.ailearning.entity.Topic;
import java.time.LocalDateTime;

public class MCQQuestionResponse {
    private Long id;
    private String question;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
    private MCQQuestion.CorrectOption correctAnswer; // Only for admin/instructor
    private String explanation;
    private Topic.Difficulty difficulty;
    private Integer points;
    private LocalDateTime createdAt;
    private Long topicId;
    private String topicTitle;
    private boolean isAnswered;
    private MCQQuestion.CorrectOption userAnswer;
    private boolean isCorrect;

    public MCQQuestionResponse() {}

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

    public MCQQuestion.CorrectOption getCorrectAnswer() { return correctAnswer; }
    public void setCorrectAnswer(MCQQuestion.CorrectOption correctAnswer) { this.correctAnswer = correctAnswer; }

    public String getExplanation() { return explanation; }
    public void setExplanation(String explanation) { this.explanation = explanation; }

    public Topic.Difficulty getDifficulty() { return difficulty; }
    public void setDifficulty(Topic.Difficulty difficulty) { this.difficulty = difficulty; }

    public Integer getPoints() { return points; }
    public void setPoints(Integer points) { this.points = points; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public Long getTopicId() { return topicId; }
    public void setTopicId(Long topicId) { this.topicId = topicId; }

    public String getTopicTitle() { return topicTitle; }
    public void setTopicTitle(String topicTitle) { this.topicTitle = topicTitle; }

    public boolean isAnswered() { return isAnswered; }
    public void setAnswered(boolean answered) { isAnswered = answered; }

    public MCQQuestion.CorrectOption getUserAnswer() { return userAnswer; }
    public void setUserAnswer(MCQQuestion.CorrectOption userAnswer) { this.userAnswer = userAnswer; }

    public boolean isCorrect() { return isCorrect; }
    public void setCorrect(boolean correct) { isCorrect = correct; }
}
