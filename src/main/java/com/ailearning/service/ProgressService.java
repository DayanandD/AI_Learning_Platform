package com.ailearning.service;

import com.ailearning.dto.request.UpdateProgressRequest;
import com.ailearning.dto.response.UserProgressResponse;
import com.ailearning.entity.Topic;
import com.ailearning.entity.User;
import com.ailearning.entity.UserProgress;
import com.ailearning.exception.ResourceNotFoundException;
import com.ailearning.repository.TopicRepository;
import com.ailearning.repository.UserProgressRepository;
import com.ailearning.repository.UserRepository;
import com.ailearning.security.UserPrincipal;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProgressService {

    @Autowired
    private UserProgressRepository userProgressRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<UserProgressResponse> getUserProgress() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        
        User user = userRepository.findById(userPrincipal.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));

        List<UserProgress> progressList = userProgressRepository.findByUser(user);
        
        return progressList.stream()
                .map(this::mapToUserProgressResponse)
                .collect(Collectors.toList());
    }

    public UserProgressResponse updateProgress(UpdateProgressRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        
        User user = userRepository.findById(userPrincipal.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));

        Topic topic = topicRepository.findById(request.getTopicId())
                .orElseThrow(() -> new ResourceNotFoundException("Topic", "id", request.getTopicId()));

        Optional<UserProgress> existingProgress = userProgressRepository.findByUserAndTopic(user, topic);
        
        UserProgress progress;
        if (existingProgress.isPresent()) {
            progress = existingProgress.get();
        } else {
            progress = new UserProgress(user, topic);
        }

        progress.setStatus(request.getStatus());
        progress.setProgressPercentage(request.getProgressPercentage());
        progress.setLastAccessedAt(LocalDateTime.now());
        
        if (request.getTimeSpentMinutes() != null) {
            progress.setTimeSpentMinutes(progress.getTimeSpentMinutes() + request.getTimeSpentMinutes());
        }

        if (request.getStatus() == UserProgress.ProgressStatus.COMPLETED) {
            progress.setCompletedAt(LocalDateTime.now());
            progress.setProgressPercentage(100);
        }

        UserProgress savedProgress = userProgressRepository.save(progress);
        return mapToUserProgressResponse(savedProgress);
    }

    private UserProgressResponse mapToUserProgressResponse(UserProgress progress) {
        UserProgressResponse response = modelMapper.map(progress, UserProgressResponse.class);
        response.setTopicId(progress.getTopic().getId());
        response.setTopicTitle(progress.getTopic().getTitle());
        return response;
    }
}
