package com.ailearning.service;

import com.ailearning.dto.response.TopicResponse;
import com.ailearning.dto.response.UserProgressResponse;
import com.ailearning.entity.Language;
import com.ailearning.entity.Topic;
import com.ailearning.entity.User;
import com.ailearning.entity.UserProgress;
import com.ailearning.exception.ResourceNotFoundException;
import com.ailearning.repository.ContentRepository;
import com.ailearning.repository.LanguageRepository;
import com.ailearning.repository.TopicRepository;
import com.ailearning.repository.UserProgressRepository;
import com.ailearning.repository.UserRepository;
import com.ailearning.security.UserPrincipal;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TopicService {

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private LanguageRepository languageRepository;

    @Autowired
    private ContentRepository contentRepository;

    @Autowired
    private UserProgressRepository userProgressRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<TopicResponse> getTopicsByLanguageId(Long languageId) {
        Language language = languageRepository.findById(languageId)
                .orElseThrow(() -> new ResourceNotFoundException("Language", "id", languageId));

        List<Topic> topics = topicRepository.findActiveTopicsByLanguageIdOrderedBySortOrder(languageId);
        
        return topics.stream()
                .map(this::mapToTopicResponse)
                .collect(Collectors.toList());
    }

    public TopicResponse getTopicById(Long topicId) {
        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new ResourceNotFoundException("Topic", "id", topicId));

        return mapToTopicResponse(topic);
    }

    private TopicResponse mapToTopicResponse(Topic topic) {
        TopicResponse response = modelMapper.map(topic, TopicResponse.class);
        response.setLanguageId(topic.getLanguage().getId());
        response.setLanguageName(topic.getLanguage().getName());
        response.setContentsCount(contentRepository.countByTopicAndActiveTrue(topic));

        // Add user progress if user is authenticated
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserPrincipal) {
            UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
            User user = userRepository.findById(userPrincipal.getId()).orElse(null);
            
            if (user != null) {
                Optional<UserProgress> userProgress = userProgressRepository.findByUserAndTopic(user, topic);
                if (userProgress.isPresent()) {
                    UserProgressResponse progressResponse = modelMapper.map(userProgress.get(), UserProgressResponse.class);
                    response.setUserProgress(progressResponse);
                }
            }
        }

        return response;
    }
}
