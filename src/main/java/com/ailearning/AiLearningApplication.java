package com.ailearning;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class AiLearningApplication {
    public static void main(String[] args) {
        System.out.println("Starting AI Learning Platform...");
        SpringApplication.run(AiLearningApplication.class, args);
        System.out.println("AI Learning Platform started successfully!");
    }
}
