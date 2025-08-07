package com.ailearning.service;

import com.ailearning.dto.response.ContentResponse;
import com.ailearning.entity.Content;
import com.ailearning.entity.Topic;
import com.ailearning.exception.ResourceNotFoundException;
import com.ailearning.repository.ContentRepository;
import com.ailearning.repository.TopicRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContentService {

    @Autowired
    private ContentRepository contentRepository;

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<ContentResponse> getContentsByTopicId(Long topicId) {
        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new ResourceNotFoundException("Topic", "id", topicId));

        List<Content> contents = contentRepository.findActiveContentsByTopicIdOrderedBySortOrder(topicId);
        
        return contents.stream()
                .map(this::mapToContentResponse)
                .collect(Collectors.toList());
    }

    public ContentResponse getContentById(Long contentId) {
        Content content = contentRepository.findById(contentId)
                .orElseThrow(() -> new ResourceNotFoundException("Content", "id", contentId));

        return mapToContentResponse(content);
    }

    private ContentResponse mapToContentResponse(Content content) {
        ContentResponse response = modelMapper.map(content, ContentResponse.class);
        response.setTopicId(content.getTopic().getId());
        response.setTopicTitle(content.getTopic().getTitle());
        return response;
    }
}
