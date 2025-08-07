package com.ailearning.controller;

import com.ailearning.dto.request.LoginRequest;
import com.ailearning.dto.request.SignUpRequest;
import com.ailearning.dto.response.ApiResponse;
import com.ailearning.dto.response.JwtAuthenticationResponse;
import com.ailearning.dto.response.UserResponse;
import com.ailearning.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "Authentication management APIs")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/signin")
    @Operation(summary = "Sign in user", description = "Authenticate user and return JWT token")
    public ResponseEntity<JwtAuthenticationResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        JwtAuthenticationResponse response = authService.authenticateUser(loginRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/signup")
    @Operation(summary = "Sign up user", description = "Register a new user account")
    public ResponseEntity<ApiResponse> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        UserResponse userResponse = authService.registerUser(signUpRequest);
        return ResponseEntity.ok(new ApiResponse(true, "User registered successfully", userResponse));
    }
}
