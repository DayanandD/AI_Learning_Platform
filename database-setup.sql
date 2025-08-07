-- Connect to PostgreSQL as superuser (postgres user)
-- Run: psql -U postgres

-- Drop existing database and user if they exist
DROP DATABASE IF EXISTS ai_learning_db;
DROP USER IF EXISTS ai_learning_user;

-- Create the user first
CREATE USER ai_learning_user WITH PASSWORD 'Daya@2796';

-- Create the database
CREATE DATABASE ai_learning_db OWNER ai_learning_user;

-- Grant all privileges
GRANT ALL PRIVILEGES ON DATABASE ai_learning_db TO ai_learning_user;
ALTER USER ai_learning_user CREATEDB;

-- Connect to the new database and grant schema permissions
\c ai_learning_db;
GRANT ALL ON SCHEMA public TO ai_learning_user;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO ai_learning_user;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO ai_learning_user;
