# AI Learning Platform - Backend API

A comprehensive Spring Boot REST API for the AI Learning Platform with PostgreSQL database, Swagger documentation, and JWT authentication.

## 🚀 Features

- **RESTful API** with Spring Boot 3.x
- **PostgreSQL Database** with JPA/Hibernate
- **JWT Authentication** & Authorization
- **Swagger/OpenAPI 3** Documentation
- **User Management** & Progress Tracking
- **Content Management** System
- **Learning Analytics** & Reporting
- **Docker Support** for easy deployment

## 📋 Prerequisites

- Java 17 or higher
- Maven 3.6+
- PostgreSQL 12+
- Docker (optional)
- IDE (IntelliJ IDEA, Eclipse, VS Code)

## 🛠️ Technology Stack

- **Framework**: Spring Boot 3.2.0
- **Database**: PostgreSQL 15
- **ORM**: Spring Data JPA / Hibernate
- **Security**: Spring Security + JWT
- **Documentation**: Swagger/OpenAPI 3
- **Build Tool**: Maven
- **Testing**: JUnit 5, Mockito, TestContainers

## 📁 Project Structure

\`\`\`
src/
├── main/
│   ├── java/com/ailearning/
│   │   ├── AiLearningApplication.java
│   │   ├── config/
│   │   │   ├── SecurityConfig.java
│   │   │   ├── SwaggerConfig.java
│   │   │   ├── DatabaseConfig.java
│   │   │   └── CorsConfig.java
│   │   ├── controller/
│   │   │   ├── AuthController.java
│   │   │   ├── UserController.java
│   │   │   ├── LanguageController.java
│   │   │   ├── TopicController.java
│   │   │   ├── ContentController.java
│   │   │   └── ProgressController.java
│   │   ├── dto/
│   │   │   ├── request/
│   │   │   │   ├── LoginRequest.java
│   │   │   │   ├── RegisterRequest.java
│   │   │   │   ├── UpdateProgressRequest.java
│   │   │   │   └── CreateContentRequest.java
│   │   │   └── response/
│   │   │       ├── JwtResponse.java
│   │   │       ├── UserResponse.java
│   │   │       ├── LanguageResponse.java
│   │   │       └── ProgressResponse.java
│   │   ├── entity/
│   │   │   ├── User.java
│   │   │   ├── Language.java
│   │   │   ├── Topic.java
│   │   │   ├── Content.java
│   │   │   ├── UserProgress.java
│   │   │   ├── LearningSession.java
│   │   │   ├── MCQQuestion.java
│   │   │   └── UserMCQAttempt.java
│   │   ├── repository/
│   │   │   ├── UserRepository.java
│   │   │   ├── LanguageRepository.java
│   │   │   ├── TopicRepository.java
│   │   │   ├── ContentRepository.java
│   │   │   ├── UserProgressRepository.java
│   │   │   └── MCQQuestionRepository.java
│   │   ├── service/
│   │   │   ├── AuthService.java
│   │   │   ├── UserService.java
│   │   │   ├── LanguageService.java
│   │   │   ├── ContentService.java
│   │   │   └── ProgressService.java
│   │   ├── security/
│   │   │   ├── JwtAuthenticationEntryPoint.java
│   │   │   ├── JwtAuthenticationFilter.java
│   │   │   ├── JwtTokenProvider.java
│   │   │   └── UserPrincipal.java
│   │   └── exception/
│   │       ├── GlobalExceptionHandler.java
│   │       ├── ResourceNotFoundException.java
│   │       └── BadRequestException.java
│   └── resources/
│       ├── application.yml
│       ├── application-dev.yml
│       ├── application-prod.yml
│       └── db/migration/
│           ├── V1__Create_initial_schema.sql
│           ├── V2__Insert_sample_data.sql
│           └── V3__Add_indexes.sql
└── test/
    ├── java/com/ailearning/
    │   ├── controller/
    │   ├── service/
    │   └── integration/
    └── resources/
        └── application-test.yml
\`\`\`

## 🗄️ Database Schema

### Core Tables

\`\`\`sql
-- Users table
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    learning_mode VARCHAR(20) DEFAULT 'BEGINNER',
    profile_image_url VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_active BOOLEAN DEFAULT true
);

-- Programming Languages/Subjects
CREATE TABLE languages (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    icon_url VARCHAR(255),
    difficulty_level VARCHAR(20),
    color_code VARCHAR(7), -- Hex color code
    is_active BOOLEAN DEFAULT true,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Topics within each language
CREATE TABLE topics (
    id BIGSERIAL PRIMARY KEY,
    language_id BIGINT REFERENCES languages(id) ON DELETE CASCADE,
    parent_topic_id BIGINT REFERENCES topics(id),
    name VARCHAR(200) NOT NULL,
    description TEXT,
    order_index INTEGER,
    estimated_duration INTEGER, -- in minutes
    difficulty_level VARCHAR(20),
    prerequisites TEXT[], -- Array of prerequisite topic names
    is_active BOOLEAN DEFAULT true,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Content sections for each topic
CREATE TABLE contents (
    id BIGSERIAL PRIMARY KEY,
    topic_id BIGINT REFERENCES topics(id) ON DELETE CASCADE,
    content_type VARCHAR(50) NOT NULL, -- TEXT, IMAGE, VIDEO, SIMULATOR, QA, MCQ, LAB, TEXT_IMAGE
    title VARCHAR(200) NOT NULL,
    content_data JSONB NOT NULL, -- Flexible content storage
    order_index INTEGER,
    learning_level VARCHAR(20), -- BEGINNER, INTERMEDIATE, ADVANCED
    estimated_time INTEGER, -- in minutes
    is_active BOOLEAN DEFAULT true,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- User progress tracking
CREATE TABLE user_progress (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT REFERENCES users(id) ON DELETE CASCADE,
    topic_id BIGINT REFERENCES topics(id) ON DELETE CASCADE,
    content_id BIGINT REFERENCES contents(id) ON DELETE CASCADE,
    status VARCHAR(20) DEFAULT 'NOT_STARTED', -- NOT_STARTED, IN_PROGRESS, COMPLETED
    completion_percentage INTEGER DEFAULT 0,
    time_spent INTEGER DEFAULT 0, -- in seconds
    last_accessed TIMESTAMP,
    completed_at TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(user_id, content_id)
);

-- Learning sessions
CREATE TABLE learning_sessions (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT REFERENCES users(id) ON DELETE CASCADE,
    topic_id BIGINT REFERENCES topics(id) ON DELETE CASCADE,
    session_start TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    session_end TIMESTAMP,
    total_time INTEGER, -- in seconds
    activities_completed INTEGER DEFAULT 0,
    score INTEGER DEFAULT 0
);

-- MCQ Questions
CREATE TABLE mcq_questions (
    id BIGSERIAL PRIMARY KEY,
    content_id BIGINT REFERENCES contents(id) ON DELETE CASCADE,
    question_text TEXT NOT NULL,
    options JSONB NOT NULL, -- Array of options
    correct_answer INTEGER NOT NULL,
    explanation TEXT,
    difficulty_level VARCHAR(20),
    points INTEGER DEFAULT 1,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- User MCQ Attempts
CREATE TABLE user_mcq_attempts (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT REFERENCES users(id) ON DELETE CASCADE,
    question_id BIGINT REFERENCES mcq_questions(id) ON DELETE CASCADE,
    selected_answer INTEGER,
    is_correct BOOLEAN,
    points_earned INTEGER DEFAULT 0,
    attempt_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- User achievements/badges
CREATE TABLE user_achievements (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT REFERENCES users(id) ON DELETE CASCADE,
    achievement_type VARCHAR(50) NOT NULL,
    achievement_name VARCHAR(100) NOT NULL,
    description TEXT,
    earned_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Indexes for better performance
CREATE INDEX idx_user_progress_user_id ON user_progress(user_id);
CREATE INDEX idx_user_progress_topic_id ON user_progress(topic_id);
CREATE INDEX idx_contents_topic_id ON contents(topic_id);
CREATE INDEX idx_topics_language_id ON topics(language_id);
CREATE INDEX idx_learning_sessions_user_id ON learning_sessions(user_id);
CREATE INDEX idx_mcq_attempts_user_id ON user_mcq_attempts(user_id);
\`\`\`

## ⚙️ Configuration

### application.yml
\`\`\`yaml
spring:
  application:
    name: ai-learning-platform
  
  datasource:
    url: jdbc:postgresql://localhost:5432/ai_learning_db
    username: \${DB_USERNAME:ai_learning_user}
    password: \${DB_PASSWORD:your_password_here}
    driver-class-name: org.postgresql.Driver
    hikari:
      maximum-pool-size: 20
      minimum-idle: 5
      connection-timeout: 30000
      idle-timeout: 600000
      max-lifetime: 1800000
  
  jpa:
    hibernate:
      ddl-auto: validate
    show-sql: false
    properties:
      hibernate:
        dialect: org.hibernate.dialect.PostgreSQLDialect
        format_sql: true
        jdbc:
          batch_size: 20
        order_inserts: true
        order_updates: true
  
  flyway:
    enabled: true
    locations: classpath:db/migration
    baseline-on-migrate: true
    validate-on-migrate: true
  
  security:
    jwt:
      secret: \${JWT_SECRET:your-super-secret-jwt-key-minimum-256-bits-long-for-production}
      expiration: 86400000 # 24 hours in milliseconds
      refresh-expiration: 604800000 # 7 days in milliseconds

server:
  port: 8080
  servlet:
    context-path: /api/v1

# Swagger/OpenAPI Configuration
springdoc:
  api-docs:
    path: /api-docs
  swagger-ui:
    path: /swagger-ui.html
    operationsSorter: method
    tagsSorter: alpha
  show-actuator: true

# Logging Configuration
logging:
  level:
    com.ailearning: INFO
    org.springframework.security: DEBUG
    org.springframework.web: DEBUG
  pattern:
    file: "%d{yyyy-MM-dd HH:mm:ss} [%thread] %-5level %logger{36} - %msg%n"
  file:
    name: logs/ai-learning-platform.log

# Management endpoints
management:
  endpoints:
    web:
      exposure:
        include: health,info,metrics,prometheus
  endpoint:
    health:
      show-details: when-authorized
\`\`\`

### application-dev.yml
\`\`\`yaml
spring:
  jpa:
    show-sql: true
    properties:
      hibernate:
        format_sql: true

logging:
  level:
    com.ailearning: DEBUG
    org.springframework.security: DEBUG
    org.hibernate.SQL: DEBUG
    org.hibernate.type.descriptor.sql.BasicBinder: TRACE
\`\`\`

### application-prod.yml
\`\`\`yaml
spring:
  jpa:
    show-sql: false
  
logging:
  level:
    com.ailearning: INFO
    org.springframework.security: WARN
    org.hibernate: WARN
  file:
    name: /var/log/ai-learning-platform.log
\`\`\`

### pom.xml
\`\`\`xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 
         http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.2.0</version>
        <relativePath/>
    </parent>
    
    <groupId>com.ailearning</groupId>
    <artifactId>ai-learning-platform</artifactId>
    <version>1.0.0</version>
    <name>AI Learning Platform</name>
    <description>Backend API for AI Learning Platform</description>
    
    <properties>
        <java.version>17</java.version>
        <jjwt.version>0.11.5</jjwt.version>
        <springdoc.version>2.2.0</springdoc.version>
        <testcontainers.version>1.19.0</testcontainers.version>
    </properties>
    
    <dependencies>
        <!-- Spring Boot Starters -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-security</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-validation</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>
        
        <!-- Database -->
        <dependency>
            <groupId>org.postgresql</groupId>
            <artifactId>postgresql</artifactId>
            <scope>runtime</scope>
        </dependency>
        <dependency>
            <groupId>org.flywaydb</groupId>
            <artifactId>flyway-core</artifactId>
        </dependency>
        
        <!-- JWT -->
        <dependency>
            <groupId>io.jsonwebtoken</groupId>
            <artifactId>jjwt-api</artifactId>
            <version>\${jjwt.version}</version>
        </dependency>
        <dependency>
            <groupId>io.jsonwebtoken</groupId>
            <artifactId>jjwt-impl</artifactId>
            <version>\${jjwt.version}</version>
            <scope>runtime</scope>
        </dependency>
        <dependency>
            <groupId>io.jsonwebtoken</groupId>
            <artifactId>jjwt-jackson</artifactId>
            <version>\${jjwt.version}</version>
            <scope>runtime</scope>
        </dependency>
        
        <!-- Swagger/OpenAPI -->
        <dependency>
            <groupId>org.springdoc</groupId>
            <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
            <version>\${springdoc.version}</version>
        </dependency>
        
        <!-- Utilities -->
        <dependency>
            <groupId>org.apache.commons</groupId>
            <artifactId>commons-lang3</artifactId>
        </dependency>
        
        <!-- Testing -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.springframework.security</groupId>
            <artifactId>spring-security-test</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.testcontainers</groupId>
            <artifactId>junit-jupiter</artifactId>
            <version>\${testcontainers.version}</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.testcontainers</groupId>
            <artifactId>postgresql</artifactId>
            <version>\${testcontainers.version}</version>
            <scope>test</scope>
        </dependency>
    </dependencies>
    
    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
            
            <!-- Flyway Plugin -->
            <plugin>
                <groupId>org.flywaydb</groupId>
                <artifactId>flyway-maven-plugin</artifactId>
                <configuration>
                    <url>jdbc:postgresql://localhost:5432/ai_learning_db</url>
                    <user>\${DB_USERNAME}</user>
                    <password>\${DB_PASSWORD}</password>
                </configuration>
            </plugin>
            
            <!-- JaCoCo for test coverage -->
            <plugin>
                <groupId>org.jacoco</groupId>
                <artifactId>jacoco-maven-plugin</artifactId>
                <version>0.8.8</version>
                <executions>
                    <execution>
                        <goals>
                            <goal>prepare-agent</goal>
                        </goals>
                    </execution>
                    <execution>
                        <id>report</id>
                        <phase>test</phase>
                        <goals>
                            <goal>report</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>
</project>
\`\`\`

## 🚀 Getting Started

### 1. Clone the Repository
\`\`\`bash
git clone https://github.com/your-username/ai-learning-platform-backend.git
cd ai-learning-platform-backend
\`\`\`

### 2. Setup PostgreSQL Database

#### Option A: Local PostgreSQL Installation
\`\`\`bash
# Install PostgreSQL (Ubuntu/Debian)
sudo apt update
sudo apt install postgresql postgresql-contrib

# Start PostgreSQL service
sudo systemctl start postgresql
sudo systemctl enable postgresql

# Create database and user
sudo -u postgres psql
CREATE DATABASE ai_learning_db;
CREATE USER ai_learning_user WITH PASSWORD 'your_secure_password_here';
GRANT ALL PRIVILEGES ON DATABASE ai_learning_db TO ai_learning_user;
ALTER USER ai_learning_user CREATEDB; -- For running tests
\\q
\`\`\`

#### Option B: Docker PostgreSQL
\`\`\`bash
# Run PostgreSQL in Docker
docker run --name ai-learning-postgres \\
  -e POSTGRES_DB=ai_learning_db \\
  -e POSTGRES_USER=ai_learning_user \\
  -e POSTGRES_PASSWORD=your_secure_password_here \\
  -p 5432:5432 \\
  -v postgres_data:/var/lib/postgresql/data \\
  -d postgres:15

# Verify the container is running
docker ps
\`\`\`

### 3. Configure Environment Variables
\`\`\`bash
# Create .env file or set environment variables
export DB_USERNAME=ai_learning_user
export DB_PASSWORD=your_secure_password_here
export JWT_SECRET=your-super-secret-jwt-key-minimum-256-bits-long-for-production

# For Windows (PowerShell)
\$env:DB_USERNAME="ai_learning_user"
\$env:DB_PASSWORD="your_secure_password_here"
\$env:JWT_SECRET="your-super-secret-jwt-key-minimum-256-bits-long-for-production"
\`\`\`

### 4. Build and Run the Application
\`\`\`bash
# Build the project
mvn clean compile

# Run database migrations
mvn flyway:migrate

# Start the application in development mode
mvn spring-boot:run -Dspring-boot.run.profiles=dev

# Or build and run JAR
mvn clean package -DskipTests
java -jar target/ai-learning-platform-1.0.0.jar
\`\`\`

### 5. Verify Installation
\`\`\`bash
# Check if the application is running
curl http://localhost:8080/api/v1/actuator/health

# Expected response:
# {"status":"UP"}
\`\`\`

## 📚 API Documentation

Once the application is running, access the API documentation at:

- **Swagger UI**: http://localhost:8080/api/v1/swagger-ui.html
- **OpenAPI JSON**: http://localhost:8080/api/v1/api-docs
- **Health Check**: http://localhost:8080/api/v1/actuator/health

### Authentication Flow

1. **Register a new user**: POST `/auth/register`
2. **Login**: POST `/auth/login` (returns JWT token)
3. **Use JWT token** in Authorization header: `Bearer <token>`
4. **Refresh token**: POST `/auth/refresh`

### Key API Endpoints

#### Authentication Endpoints
\`\`\`
POST /api/v1/auth/register     - User registration
POST /api/v1/auth/login        - User login
POST /api/v1/auth/refresh      - Refresh JWT token
POST /api/v1/auth/logout       - User logout
GET  /api/v1/auth/me           - Get current user info
\`\`\`

#### User Management
\`\`\`
GET    /api/v1/users/profile   - Get user profile
PUT    /api/v1/users/profile   - Update user profile
GET    /api/v1/users/progress  - Get user progress summary
DELETE /api/v1/users/account   - Delete user account
\`\`\`

#### Languages & Topics
\`\`\`
GET    /api/v1/languages                    - Get all programming languages
GET    /api/v1/languages/{id}               - Get language details
GET    /api/v1/languages/{id}/topics        - Get topics by language
GET    /api/v1/topics/{id}                  - Get topic details
GET    /api/v1/topics/{id}/subtopics        - Get subtopics
\`\`\`

#### Content Management
\`\`\`
GET    /api/v1/contents/topic/{topicId}     - Get content by topic
GET    /api/v1/contents/{id}                - Get specific content
POST   /api/v1/contents/{id}/complete       - Mark content as completed
GET    /api/v1/contents/{id}/mcq            - Get MCQ questions for content
POST   /api/v1/contents/mcq/{questionId}/answer - Submit MCQ answer
\`\`\`

#### Progress Tracking
\`\`\`
GET    /api/v1/progress/user/{userId}       - Get user progress
POST   /api/v1/progress/update              - Update progress
GET    /api/v1/progress/analytics           - Get learning analytics
GET    /api/v1/progress/achievements        - Get user achievements
POST   /api/v1/progress/session/start       - Start learning session
POST   /api/v1/progress/session/end         - End learning session
\`\`\`

### Sample API Requests

#### Register User
\`\`\`bash
curl -X POST http://localhost:8080/api/v1/auth/register \\
  -H "Content-Type: application/json" \\
  -d '{
    "username": "johndoe",
    "email": "john@example.com",
    "password": "SecurePassword123!",
    "firstName": "John",
    "lastName": "Doe",
    "learningMode": "BEGINNER"
  }'
\`\`\`

#### Login
\`\`\`bash
curl -X POST http://localhost:8080/api/v1/auth/login \\
  -H "Content-Type: application/json" \\
  -d '{
    "username": "johndoe",
    "password": "SecurePassword123!"
  }'
\`\`\`

#### Get Languages (with JWT token)
\`\`\`bash
curl -X GET http://localhost:8080/api/v1/languages \\
  -H "Authorization: Bearer YOUR_JWT_TOKEN_HERE"
\`\`\`

## 🐳 Docker Deployment

### Dockerfile
\`\`\`dockerfile
FROM openjdk:17-jdk-slim

# Set working directory
WORKDIR /app

# Copy Maven wrapper and pom.xml
COPY .mvn/ .mvn/
COPY mvnw pom.xml ./

# Download dependencies
RUN ./mvnw dependency:go-offline

# Copy source code
COPY src ./src

# Build the application
RUN ./mvnw clean package -DskipTests

# Expose port
EXPOSE 8080

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=60s --retries=3 \\
  CMD curl -f http://localhost:8080/api/v1/actuator/health || exit 1

# Run the application
ENTRYPOINT ["java", "-jar", "target/ai-learning-platform-1.0.0.jar"]
\`\`\`

### docker-compose.yml
\`\`\`yaml
version: '3.8'

services:
  postgres:
    image: postgres:15
    container_name: ai-learning-postgres
    environment:
      POSTGRES_DB: ai_learning_db
      POSTGRES_USER: ai_learning_user
      POSTGRES_PASSWORD: your_secure_password_here
    ports:
      - "5432:5432"
    volumes:
      - postgres_data:/var/lib/postgresql/data
      - ./init-scripts:/docker-entrypoint-initdb.d
    healthcheck:
      test: ["CMD-SHELL", "pg_isready -U ai_learning_user -d ai_learning_db"]
      interval: 30s
      timeout: 10s
      retries: 3

  backend:
    build: .
    container_name: ai-learning-backend
    ports:
      - "8080:8080"
    environment:
      SPRING_PROFILES_ACTIVE: prod
      DB_USERNAME: ai_learning_user
      DB_PASSWORD: your_secure_password_here
      JWT_SECRET: your-super-secret-jwt-key-minimum-256-bits-long-for-production
      SPRING_DATASOURCE_URL: jdbc:postgresql://postgres:5432/ai_learning_db
    depends_on:
      postgres:
        condition: service_healthy
    healthcheck:
      test: ["CMD", "curl", "-f", "http://localhost:8080/api/v1/actuator/health"]
      interval: 30s
      timeout: 10s
      retries: 3
      start_period: 60s

  # Optional: pgAdmin for database management
  pgadmin:
    image: dpage/pgadmin4:latest
    container_name: ai-learning-pgadmin
    environment:
      PGADMIN_DEFAULT_EMAIL: admin@ailearning.com
      PGADMIN_DEFAULT_PASSWORD: admin123
    ports:
      - "5050:80"
    depends_on:
      - postgres

volumes:
  postgres_data:
\`\`\`

### Run with Docker Compose
\`\`\`bash
# Build and start all services
docker-compose up --build

# Run in background
docker-compose up -d

# View logs
docker-compose logs -f backend

# Stop services
docker-compose down

# Stop and remove volumes
docker-compose down -v
\`\`\`

## 🧪 Testing

### Run Tests
\`\`\`bash
# Run all tests
mvn test

# Run only unit tests
mvn test -Dtest=**/*Test

# Run only integration tests
mvn test -Dtest=**/*IntegrationTest

# Run tests with coverage report
mvn test jacoco:report

# View coverage report
open target/site/jacoco/index.html
\`\`\`

### Test Configuration
The application uses TestContainers for integration tests with a real PostgreSQL instance:

\`\`\`yaml
# application-test.yml
spring:
  datasource:
    url: jdbc:tc:postgresql:15:///testdb
    driver-class-name: org.testcontainers.jdbc.ContainerDatabaseDriver
  jpa:
    hibernate:
      ddl-auto: create-drop
  flyway:
    enabled: false

logging:
  level:
    com.ailearning: DEBUG
    org.testcontainers: INFO
\`\`\`

## 📊 Database Migration

### Flyway Migration Files
Create migration files in `src/main/resources/db/migration/`:

#### V1__Create_initial_schema.sql
\`\`\`sql
-- Create users table
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    learning_mode VARCHAR(20) DEFAULT 'BEGINNER',
    profile_image_url VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_active BOOLEAN DEFAULT true
);

-- Create other tables...
-- (Include all table creation statements from the schema section above)
\`\`\`

#### V2__Insert_sample_data.sql
\`\`\`sql
-- Insert sample programming languages
INSERT INTO languages (name, description, icon_url, difficulty_level, color_code) VALUES
('JavaScript', 'Dynamic programming language for web development', '/icons/javascript.svg', 'BEGINNER', '#F7DF1E'),
('Python', 'High-level programming language for general-purpose programming', '/icons/python.svg', 'BEGINNER', '#3776AB'),
('Java', 'Object-oriented programming language', '/icons/java.svg', 'INTERMEDIATE', '#ED8B00'),
('React', 'JavaScript library for building user interfaces', '/icons/react.svg', 'INTERMEDIATE', '#61DAFB'),
('Node.js', 'JavaScript runtime for server-side development', '/icons/nodejs.svg', 'INTERMEDIATE', '#339933');

-- Insert sample topics for JavaScript
INSERT INTO topics (language_id, name, description, order_index, estimated_duration, difficulty_level) VALUES
((SELECT id FROM languages WHERE name = 'JavaScript'), 'Variables and Data Types', 'Learn about JavaScript variables and data types', 1, 30, 'BEGINNER'),
((SELECT id FROM languages WHERE name = 'JavaScript'), 'Functions', 'Understanding JavaScript functions', 2, 45, 'BEGINNER'),
((SELECT id FROM languages WHERE name = 'JavaScript'), 'Arrays and Objects', 'Working with arrays and objects', 3, 60, 'BEGINNER'),
((SELECT id FROM languages WHERE name = 'JavaScript'), 'DOM Manipulation', 'Manipulating the Document Object Model', 4, 90, 'INTERMEDIATE');

-- Insert sample content for the first topic
INSERT INTO contents (topic_id, content_type, title, content_data, order_index, learning_level, estimated_time) VALUES
((SELECT id FROM topics WHERE name = 'Variables and Data Types'), 'TEXT', 'Introduction to Variables', 
 '{"content": "Variables are containers for storing data values. In JavaScript, you can declare variables using var, let, or const keywords."}', 
 1, 'BEGINNER', 10),
((SELECT id FROM topics WHERE name = 'Variables and Data Types'), 'SIMULATOR', 'Variable Declaration Practice', 
 '{"initialCode": "// Declare a variable named age and assign it the value 25\\nlet age = 25;\\nconsole.log(age);", "expectedOutput": "25"}', 
 2, 'BEGINNER', 15),
((SELECT id FROM topics WHERE name = 'Variables and Data Types'), 'MCQ', 'Variables Quiz', 
 '{"questions": [{"question": "Which keyword is used to declare a constant in JavaScript?", "options": ["var", "let", "const", "final"], "correct": 2}]}', 
 3, 'BEGINNER', 5);
\`\`\`

### Run Migrations
\`\`\`bash
# Run migrations
mvn flyway:migrate

# Check migration status
mvn flyway:info

# Validate migrations
mvn flyway:validate

# Clean database (development only - BE CAREFUL!)
mvn flyway:clean
\`\`\`

## 🔒 Security Features

### JWT Authentication
- **Access Token**: Short-lived (24 hours)
- **Refresh Token**: Long-lived (7 days)
- **Token Blacklisting**: Logout invalidates tokens
- **Role-based Access Control**: Admin, User roles

### Password Security
- **BCrypt Hashing**: Industry-standard password hashing
- **Password Validation**: Minimum 8 characters, special characters required
- **Account Lockout**: After 5 failed login attempts

### API Security
- **CORS Configuration**: Configurable allowed origins
- **Rate Limiting**: Prevent API abuse
- **Input Validation**: All inputs validated and sanitized
- **SQL Injection Prevention**: Parameterized queries

### Security Headers
\`\`\`yaml
# Additional security configuration
server:
  servlet:
    session:
      cookie:
        secure: true
        http-only: true
        same-site: strict
\`\`\`

## 📈 Monitoring & Logging

### Actuator Endpoints
Available at `/api/v1/actuator/`:
- `/health` - Application health status
- `/info` - Application information
- `/metrics` - Application metrics
- `/prometheus` - Prometheus metrics format

### Custom Health Indicators
\`\`\`java
@Component
public class DatabaseHealthIndicator implements HealthIndicator {
    @Override
    public Health health() {
        // Custom database health check logic
        return Health.up()
            .withDetail("database", "PostgreSQL")
            .withDetail("status", "Connected")
            .build();
    }
}
\`\`\`

### Logging Configuration
\`\`\`yaml
logging:
  level:
    com.ailearning: INFO
    org.springframework.web: DEBUG
    org.springframework.security: DEBUG
  pattern:
    console: "%d{yyyy-MM-dd HH:mm:ss} - %msg%n"
    file: "%d{yyyy-MM-dd HH:mm:ss} [%thread] %-5level %logger{36} - %msg%n"
  file:
    name: logs/ai-learning-platform.log
    max-size: 10MB
    max-history: 30
\`\`\`

## 🔧 Troubleshooting

### Common Issues

#### Database Connection Issues
\`\`\`bash
# Check if PostgreSQL is running
sudo systemctl status postgresql

# Check database connectivity
psql -h localhost -U ai_learning_user -d ai_learning_db

# Check application logs
tail -f logs/ai-learning-platform.log
\`\`\`

#### JWT Token Issues
\`\`\`bash
# Verify JWT secret is set
echo \$JWT_SECRET

# Check token expiration in logs
grep "JWT" logs/ai-learning-platform.log
\`\`\`

#### Migration Issues
\`\`\`bash
# Check migration status
mvn flyway:info

# Repair failed migrations
mvn flyway:repair

# Baseline existing database
mvn flyway:baseline
\`\`\`

### Performance Issues
\`\`\`sql
-- Check slow queries
SELECT query, mean_time, calls 
FROM pg_stat_statements 
ORDER BY mean_time DESC 
LIMIT 10;

-- Check database connections
SELECT count(*) as active_connections 
FROM pg_stat_activity 
WHERE state = 'active';
\`\`\`

## 🤝 Contributing

### Development Setup
1. Fork the repository
2. Create a feature branch: `git checkout -b feature/amazing-feature`
3. Make your changes
4. Run tests: `mvn test`
5. Commit changes: `git commit -m 'Add amazing feature'`
6. Push to branch: `git push origin feature/amazing-feature`
7. Open a Pull Request

### Code Style
- Follow Java naming conventions
- Use meaningful variable and method names
- Add JavaDoc comments for public methods
- Write unit tests for new features
- Maintain test coverage above 80%

### Pull Request Guidelines
- Include description of changes
- Reference related issues
- Ensure all tests pass
- Update documentation if needed
- Add screenshots for UI changes

## 📝 API Integration with Frontend

### CORS Configuration
The backend is configured to work with your React frontend:

\`\`\`java
@CrossOrigin(origins = {"http://localhost:3000", "https://your-frontend-domain.com"})
\`\`\`

### Frontend Integration Example
\`\`\`javascript
// Frontend API service example
const API_BASE_URL = 'http://localhost:8080/api/v1';

class ApiService {
  constructor() {
    this.token = localStorage.getItem('jwt_token');
  }

  async login(username, password) {
    const response = await fetch(\`\${API_BASE_URL}/auth/login\`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({ username, password }),
    });
    
    if (response.ok) {
      const data = await response.json();
      this.token = data.token;
      localStorage.setItem('jwt_token', this.token);
      return data;
    }
    throw new Error('Login failed');
  }

  async getLanguages() {
    const response = await fetch(\`\${API_BASE_URL}/languages\`, {
      headers: {
        'Authorization': \`Bearer \${this.token}\`,
      },
    });
    
    if (response.ok) {
      return await response.json();
    }
    throw new Error('Failed to fetch languages');
  }
}
\`\`\`

## 📞 Support & Documentation

### Additional Resources
- **API Documentation**: http://localhost:8080/api/v1/swagger-ui.html
- **Health Checks**: http://localhost:8080/api/v1/actuator/health
- **Metrics**: http://localhost:8080/api/v1/actuator/metrics
- **Database Admin**: http://localhost:5050 (pgAdmin)

### Getting Help
- **Issues**: [GitHub Issues](https://github.com/your-repo/issues)
- **Discussions**: [GitHub Discussions](https://github.com/your-repo/discussions)
- **Email**: support@ailearningplatform.com

### License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

**Happy Coding! 🚀**

*Built with ❤️ using Spring Boot, PostgreSQL, and modern Java practices.*
