-- Connect to PostgreSQL as superuser and run these commands

-- Drop the existing database completely
DROP DATABASE IF EXISTS ai_learning_db;

-- Recreate the database
CREATE DATABASE ai_learning_db;

-- Create the user if it doesn't exist
DO
$do$
BEGIN
   IF NOT EXISTS (
      SELECT FROM pg_catalog.pg_roles
      WHERE  rolname = 'ai_learning_user') THEN
      CREATE ROLE ai_learning_user LOGIN PASSWORD 'Daya@2796';
   END IF;
END
$do$;

-- Grant all privileges
GRANT ALL PRIVILEGES ON DATABASE ai_learning_db TO ai_learning_user;
ALTER USER ai_learning_user CREATEDB;
