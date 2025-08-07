package com.ailearning.controller;

import com.ailearning.dto.request.UpdateProgressRequest;
import com.ailearning.dto.response.UserProgressResponse;
import com.ailearning.service.ProgressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/progress")
@Tag(name = "Progress", description = "Learning progress management APIs")
@SecurityRequirement(name = "Bearer Authentication")
public class ProgressController {

    @Autowired
    private ProgressService progressService;

    @GetMapping
    @Operation(summary = "Get user progress", description = "Get current user's learning progress")
    public ResponseEntity<List<UserProgressResponse>> getUserProgress() {
        List<UserProgressResponse> progress = progressService.getUserProgress();
        return ResponseEntity.ok(progress);
    }

    @PostMapping
    @Operation(summary = "Update progress", description = "Update user's learning progress for a topic")
    public ResponseEntity<UserProgressResponse> updateProgress(@Valid @RequestBody UpdateProgressRequest request) {
        UserProgressResponse progress = progressService.updateProgress(request);
        return ResponseEntity.ok(progress);
    }
}
