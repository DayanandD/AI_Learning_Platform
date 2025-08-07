# AI Learning Platform - Quick Start Guide

## Prerequisites
- Java 17 or higher
- Maven 3.6+
- PostgreSQL 12+

## Quick Setup

### 1. Database Setup
\`\`\`sql
-- Connect to PostgreSQL as superuser (postgres user)
psql -U postgres

-- Run these commands:
DROP DATABASE IF EXISTS ai_learning_db;
CREATE DATABASE ai_learning_db;

-- Create user (if doesn't exist)
CREATE USER ai_learning_user WITH PASSWORD 'Daya@2796';
GRANT ALL PRIVILEGES ON DATABASE ai_learning_db TO ai_learning_user;
ALTER USER ai_learning_user CREATEDB;

-- Exit psql
\q
\`\`\`

### 2. Environment Variables (Optional)
\`\`\`bash
export DB_USERNAME=ai_learning_user
export DB_PASSWORD=Daya@2796
export JWT_SECRET=mySecretKey123456789012345678901234567890123456789012345678901234567890
\`\`\`

### 3. Run the Application
\`\`\`bash
# Option 1: Using Maven
mvn clean spring-boot:run

# Option 2: Using the startup script
chmod +x start.sh
./start.sh

# Option 3: Build and run JAR
mvn clean package -DskipTests
java -jar target/ai-learning-platform-1.0.0.jar
\`\`\`

## Verify Installation

### 1. Check Application Health
\`\`\`bash
curl http://localhost:8080/api/actuator/health
\`\`\`

### 2. Test API
\`\`\`bash
curl http://localhost:8080/api/test/ping
\`\`\`

### 3. Access Swagger UI
Open: http://localhost:8080/api/swagger-ui.html

### 4. Test Authentication
\`\`\`bash
# Login with admin user
curl -X POST http://localhost:8080/api/auth/signin \
  -H "Content-Type: application/json" \
  -d '{
    "usernameOrEmail": "admin",
    "password": "admin123"
  }'
\`\`\`

## Default Admin User
- **Username**: admin
- **Password**: admin123
- **Email**: admin@ailearning.com

## API Endpoints

### Public Endpoints
- `GET /api/test/ping` - Simple ping test
- `GET /api/test/health` - Health check
- `GET /api/actuator/health` - Spring actuator health
- `POST /api/auth/signin` - User login
- `POST /api/auth/signup` - User registration

### Protected Endpoints (Require JWT Token)
- `GET /api/users/me` - Get current user
- `GET /api/languages` - Get all languages
- `GET /api/topics/language/{id}` - Get topics by language
- `GET /api/contents/topic/{id}` - Get contents by topic

## Troubleshooting

### Database Connection Issues
1. Ensure PostgreSQL is running
2. Check database credentials
3. Verify database exists

### Port Already in Use
\`\`\`bash
# Kill process on port 8080
lsof -ti:8080 | xargs kill -9

# Or change port in application.yml
server:
  port: 8081
\`\`\`

### JWT Token Issues
- Ensure JWT_SECRET is at least 256 bits (32 characters)
- Check token expiration settings

## Next Steps
1. Test all endpoints using Swagger UI
2. Create additional users via signup endpoint
3. Build admin APIs for content management
4. Integrate with your React frontend
\`\`\`

Finally, let's make sure the pom.xml is correct:
