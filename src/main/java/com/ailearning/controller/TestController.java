package com.ailearning.controller;

import com.ailearning.dto.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/test")
@Tag(name = "Test", description = "Test endpoints to verify API is working")
public class TestController {

    @GetMapping("/ping")
    @Operation(summary = "Ping test", description = "Simple ping test to check if API is running")
    public ResponseEntity<ApiResponse> ping() {
        Map<String, Object> data = new HashMap<>();
        data.put("message", "AI Learning Platform API is running!");
        data.put("timestamp", LocalDateTime.now());
        data.put("status", "OK");
        
        return ResponseEntity.ok(new ApiResponse(true, "Ping successful", data));
    }

    @GetMapping("/health")
    @Operation(summary = "Health check", description = "Detailed health check")
    public ResponseEntity<ApiResponse> health() {
        Map<String, Object> data = new HashMap<>();
        data.put("application", "AI Learning Platform");
        data.put("version", "1.0.0");
        data.put("status", "UP");
        data.put("timestamp", LocalDateTime.now());
        data.put("java_version", System.getProperty("java.version"));
        data.put("spring_boot_version", "3.2.0");
        
        return ResponseEntity.ok(new ApiResponse(true, "Application is healthy", data));
    }
}
