# --- Stage 1: Build the JAR using Maven ---
FROM maven:3.9.6-eclipse-temurin-17 AS build

# Set working directory inside the build container
WORKDIR /app

# Copy Maven wrapper and pom.xml for dependency resolution
COPY .mvn/ .mvn/
COPY mvnw pom.xml ./

# Make mvnw executable (in case of permission issues)
RUN chmod +x ./mvnw

# Download all dependencies (cache optimization)
RUN ./mvnw dependency:go-offline

# Copy the source code
COPY src ./src

# Build the jar without running tests (faster build)
RUN ./mvnw clean package -DskipTests


# --- Stage 2: Create minimal image with the built jar ---
FROM eclipse-temurin:17-jdk-jammy

# Set working directory inside the runtime container
WORKDIR /app

# Expose default Spring Boot port
EXPOSE 8080

# Health check to ensure app is running
HEALTHCHECK --interval=30s --timeout=3s --start-period=60s --retries=3 \
  CMD curl -f http://localhost:8080/api/v1/actuator/health || exit 1

# Copy the jar from the build stage to the runtime image
COPY --from=build /app/target/ai-learning-platform-1.0.0.jar app.jar

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]
