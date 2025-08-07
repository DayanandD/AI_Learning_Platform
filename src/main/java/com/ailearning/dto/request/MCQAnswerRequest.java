package com.ailearning.dto.request;

import com.ailearning.entity.MCQQuestion;
import jakarta.validation.constraints.NotNull;

public class MCQAnswerRequest {
    @NotNull
    private Long questionId;

    @NotNull
    private MCQQuestion.CorrectOption selectedAnswer;

    private Integer timeSpentSeconds;

    public MCQAnswerRequest() {}

    // Getters and Setters
    public Long getQuestionId() { return questionId; }
    public void setQuestionId(Long questionId) { this.questionId = questionId; }

    public MCQQuestion.CorrectOption getSelectedAnswer() { return selectedAnswer; }
    public void setSelectedAnswer(MCQQuestion.CorrectOption selectedAnswer) { this.selectedAnswer = selectedAnswer; }

    public Integer getTimeSpentSeconds() { return timeSpentSeconds; }
    public void setTimeSpentSeconds(Integer timeSpentSeconds) { this.timeSpentSeconds = timeSpentSeconds; }
}
