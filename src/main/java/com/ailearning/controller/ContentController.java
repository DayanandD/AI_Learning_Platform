package com.ailearning.controller;

import com.ailearning.dto.response.ContentResponse;
import com.ailearning.service.ContentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contents")
@Tag(name = "Content", description = "Learning content management APIs")
@SecurityRequirement(name = "Bearer Authentication")
public class ContentController {

    @Autowired
    private ContentService contentService;

    @GetMapping("/topic/{topicId}")
    @Operation(summary = "Get contents by topic", description = "Get all contents for a specific topic")
    public ResponseEntity<List<ContentResponse>> getContentsByTopicId(@PathVariable Long topicId) {
        List<ContentResponse> contents = contentService.getContentsByTopicId(topicId);
        return ResponseEntity.ok(contents);
    }

    @GetMapping("/{contentId}")
    @Operation(summary = "Get content by ID", description = "Get content details by ID")
    public ResponseEntity<ContentResponse> getContentById(@PathVariable Long contentId) {
        ContentResponse content = contentService.getContentById(contentId);
        return ResponseEntity.ok(content);
    }
}
