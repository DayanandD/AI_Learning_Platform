#!/bin/bash

echo "Starting AI Learning Platform Backend..."

# Clean and compile
echo "Cleaning and compiling..."
mvn clean compile

# Run the application
echo "Starting Spring Boot application..."
mvn spring-boot:run

echo "Application stopped."
