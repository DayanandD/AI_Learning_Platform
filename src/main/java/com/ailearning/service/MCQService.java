package com.ailearning.service;

import com.ailearning.dto.request.MCQAnswerRequest;
import com.ailearning.dto.response.MCQQuestionResponse;
import com.ailearning.entity.MCQQuestion;
import com.ailearning.entity.Topic;
import com.ailearning.entity.User;
import com.ailearning.entity.UserMCQAttempt;
import com.ailearning.exception.ResourceNotFoundException;
import com.ailearning.repository.MCQQuestionRepository;
import com.ailearning.repository.TopicRepository;
import com.ailearning.repository.UserMCQAttemptRepository;
import com.ailearning.repository.UserRepository;
import com.ailearning.security.UserPrincipal;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MCQService {

    @Autowired
    private MCQQuestionRepository mcqQuestionRepository;

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private UserMCQAttemptRepository userMCQAttemptRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<MCQQuestionResponse> getQuestionsByTopicId(Long topicId) {
        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new ResourceNotFoundException("Topic", "id", topicId));

        List<MCQQuestion> questions = mcqQuestionRepository.findByTopicAndActiveTrue(topic);
        
        return questions.stream()
                .map(this::mapToMCQQuestionResponse)
                .collect(Collectors.toList());
    }

    public MCQQuestionResponse submitAnswer(MCQAnswerRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        
        User user = userRepository.findById(userPrincipal.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));

        MCQQuestion question = mcqQuestionRepository.findById(request.getQuestionId())
                .orElseThrow(() -> new ResourceNotFoundException("MCQ Question", "id", request.getQuestionId()));

        UserMCQAttempt attempt = new UserMCQAttempt(user, question, request.getSelectedAnswer());
        attempt.setTimeSpentSeconds(request.getTimeSpentSeconds());
        
        userMCQAttemptRepository.save(attempt);

        MCQQuestionResponse response = mapToMCQQuestionResponse(question);
        response.setAnswered(true);
        response.setUserAnswer(request.getSelectedAnswer());
        response.setCorrect(attempt.isCorrect());
        
        return response;
    }

    private MCQQuestionResponse mapToMCQQuestionResponse(MCQQuestion question) {
        MCQQuestionResponse response = modelMapper.map(question, MCQQuestionResponse.class);
        response.setTopicId(question.getTopic().getId());
        response.setTopicTitle(question.getTopic().getTitle());

        // Check if current user has answered this question
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserPrincipal) {
            UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
            User user = userRepository.findById(userPrincipal.getId()).orElse(null);
            
            if (user != null) {
                List<UserMCQAttempt> attempts = userMCQAttemptRepository.findByUserAndQuestion(user, question);
                if (!attempts.isEmpty()) {
                    UserMCQAttempt lastAttempt = attempts.get(attempts.size() - 1);
                    response.setAnswered(true);
                    response.setUserAnswer(lastAttempt.getSelectedAnswer());
                    response.setCorrect(lastAttempt.isCorrect());
                }
            }
        }

        return response;
    }
}
