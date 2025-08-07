package com.ailearning.controller;

import com.ailearning.dto.response.LanguageResponse;
import com.ailearning.service.LanguageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/languages")
@Tag(name = "Languages", description = "Programming languages management APIs")
@SecurityRequirement(name = "Bearer Authentication")
public class LanguageController {

    @Autowired
    private LanguageService languageService;

    @GetMapping
    @Operation(summary = "Get all languages", description = "Get all active programming languages")
    public ResponseEntity<List<LanguageResponse>> getAllLanguages() {
        List<LanguageResponse> languages = languageService.getAllActiveLanguages();
        return ResponseEntity.ok(languages);
    }

    @GetMapping("/{languageId}")
    @Operation(summary = "Get language by ID", description = "Get programming language details by ID")
    public ResponseEntity<LanguageResponse> getLanguageById(@PathVariable Long languageId) {
        LanguageResponse language = languageService.getLanguageById(languageId);
        return ResponseEntity.ok(language);
    }
}
