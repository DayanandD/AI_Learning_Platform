// package com.ailearning.config;

// import com.ailearning.entity.User;
// import com.ailearning.repository.UserRepository;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.CommandLineRunner;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.stereotype.Component;

// @Component
// public class MinimalDataSeeder implements CommandLineRunner {

//     private static final Logger logger = LoggerFactory.getLogger(MinimalDataSeeder.class);

//     @Autowired
//     private UserRepository userRepository;

//     @Autowired
//     private PasswordEncoder passwordEncoder;

//     @Override
//     public void run(String... args) throws Exception {
//         logger.info("Checking if admin user exists...");
        
//         if (userRepository.count() == 0) {
//             logger.info("Creating admin user...");
            
//             User admin = new User();
//             admin.setUsername("admin");
//             admin.setEmail("admin@ailearning.com");
//             admin.setPassword(passwordEncoder.encode("admin123"));
//             admin.setFirstName("Admin");
//             admin.setLastName("User");
//             admin.setRole(User.Role.ADMIN);
//             admin.setLearningLevel(User.LearningLevel.PROFESSIONAL);
//             admin.setEnabled(true);
            
//             userRepository.save(admin);
//             logger.info("Admin user created successfully!");
//             logger.info("Login credentials: username=admin, password=admin123");
//         } else {
//             logger.info("Users already exist, skipping admin user creation");
//         }
//     }
// }
