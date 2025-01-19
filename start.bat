@echo off

cd .\teammanager

echo Building the Spring Boot application...
mvn clean package -DskipTests

if %errorlevel% neq 0 (
    echo Build failed. Exiting...
    exit /b 1
)

echo Starting the application with Docker Compose...
docker-compose up --build -d

if %errorlevel% neq 0 (
    echo Docker Compose failed. Exiting...
    exit /b 1
)

echo Application is up and running!
