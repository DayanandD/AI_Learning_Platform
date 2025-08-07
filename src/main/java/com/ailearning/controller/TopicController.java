package com.ailearning.controller;

import com.ailearning.dto.response.TopicResponse;
import com.ailearning.service.TopicService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/topics")
@Tag(name = "Topics", description = "Learning topics management APIs")
@SecurityRequirement(name = "Bearer Authentication")
public class TopicController {

    @Autowired
    private TopicService topicService;

    @GetMapping("/language/{languageId}")
    @Operation(summary = "Get topics by language", description = "Get all topics for a specific programming language")
    public ResponseEntity<List<TopicResponse>> getTopicsByLanguageId(@PathVariable Long languageId) {
        List<TopicResponse> topics = topicService.getTopicsByLanguageId(languageId);
        return ResponseEntity.ok(topics);
    }

    @GetMapping("/{topicId}")
    @Operation(summary = "Get topic by ID", description = "Get topic details by ID")
    public ResponseEntity<TopicResponse> getTopicById(@PathVariable Long topicId) {
        TopicResponse topic = topicService.getTopicById(topicId);
        return ResponseEntity.ok(topic);
    }
}
