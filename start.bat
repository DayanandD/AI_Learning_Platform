@echo off
echo Starting AI Learning Platform Backend...

echo Cleaning and compiling...
mvn clean compile

echo Starting Spring Boot application...
mvn spring-boot:run

echo Application stopped.
pause
