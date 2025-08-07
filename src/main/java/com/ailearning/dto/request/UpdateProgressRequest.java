package com.ailearning.dto.request;

import com.ailearning.entity.UserProgress;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class UpdateProgressRequest {
    @NotNull
    private Long topicId;

    @NotNull
    private UserProgress.ProgressStatus status;

    @Min(0)
    @Max(100)
    private Integer progressPercentage;

    private Integer timeSpentMinutes;

    public UpdateProgressRequest() {}

    // Getters and Setters
    public Long getTopicId() { return topicId; }
    public void setTopicId(Long topicId) { this.topicId = topicId; }

    public UserProgress.ProgressStatus getStatus() { return status; }
    public void setStatus(UserProgress.ProgressStatus status) { this.status = status; }

    public Integer getProgressPercentage() { return progressPercentage; }
    public void setProgressPercentage(Integer progressPercentage) { this.progressPercentage = progressPercentage; }

    public Integer getTimeSpentMinutes() { return timeSpentMinutes; }
    public void setTimeSpentMinutes(Integer timeSpentMinutes) { this.timeSpentMinutes = timeSpentMinutes; }
}
