#!/bin/bash

cd ./teammanager

echo "Building the Spring Boot application..."
mvn clean package -DskipTests

# Check if the build was successful
if [ $? -ne 0 ]; then
  echo "Build failed. Exiting..."
  exit 1
fi

echo "Starting the application with Docker Compose..."
docker-compose up --build -d

if [ $? -ne 0 ]; then
  echo "Docker Compose failed. Exiting..."
  exit 1
fi

echo "Application is up and running!"
