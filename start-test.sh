#!/bin/bash

echo "Starting AI Learning Platform Backend in TEST mode..."

echo "Cleaning and compiling..."
mvn clean compile

echo "Starting Spring Boot application with test profile..."
mvn spring-boot:run -Dspring-boot.run.profiles=test

echo "Application stopped."
