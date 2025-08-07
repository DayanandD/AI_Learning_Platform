package com.ailearning.service;

import com.ailearning.dto.request.LoginRequest;
import com.ailearning.dto.request.SignUpRequest;
import com.ailearning.dto.response.JwtAuthenticationResponse;
import com.ailearning.dto.response.UserResponse;
import com.ailearning.entity.User;
import com.ailearning.exception.BadRequestException;
import com.ailearning.repository.UserRepository;
import com.ailearning.security.JwtTokenProvider;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private ModelMapper modelMapper;

    public JwtAuthenticationResponse authenticateUser(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsernameOrEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = tokenProvider.generateToken(authentication);
        String refreshToken = tokenProvider.generateRefreshToken(authentication);

        User user = userRepository.findByUsernameOrEmail(loginRequest.getUsernameOrEmail())
                .orElseThrow(() -> new BadRequestException("User not found"));

        UserResponse userResponse = modelMapper.map(user, UserResponse.class);

        return new JwtAuthenticationResponse(accessToken, refreshToken, userResponse);
    }

    public UserResponse registerUser(SignUpRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            throw new BadRequestException("Username is already taken!");
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new BadRequestException("Email Address already in use!");
        }

        User user = new User();
        user.setUsername(signUpRequest.getUsername());
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        user.setFirstName(signUpRequest.getFirstName());
        user.setLastName(signUpRequest.getLastName());
        user.setLearningLevel(signUpRequest.getLearningLevel());
        user.setRole(User.Role.USER);

        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserResponse.class);
    }
}
