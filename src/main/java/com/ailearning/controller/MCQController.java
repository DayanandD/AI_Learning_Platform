package com.ailearning.controller;

import com.ailearning.dto.request.MCQAnswerRequest;
import com.ailearning.dto.response.MCQQuestionResponse;
import com.ailearning.service.MCQService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mcq")
@Tag(name = "MCQ", description = "Multiple Choice Questions management APIs")
@SecurityRequirement(name = "Bearer Authentication")
public class MCQController {

    @Autowired
    private MCQService mcqService;

    @GetMapping("/topic/{topicId}")
    @Operation(summary = "Get MCQ questions by topic", description = "Get all MCQ questions for a specific topic")
    public ResponseEntity<List<MCQQuestionResponse>> getQuestionsByTopicId(@PathVariable Long topicId) {
        List<MCQQuestionResponse> questions = mcqService.getQuestionsByTopicId(topicId);
        return ResponseEntity.ok(questions);
    }

    @PostMapping("/answer")
    @Operation(summary = "Submit MCQ answer", description = "Submit answer for an MCQ question")
    public ResponseEntity<MCQQuestionResponse> submitAnswer(@Valid @RequestBody MCQAnswerRequest request) {
        MCQQuestionResponse response = mcqService.submitAnswer(request);
        return ResponseEntity.ok(response);
    }
}
