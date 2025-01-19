#!/bin/bash

cd ./teammanager

echo "Building the Spring Boot application..."
mvn clean package -DskipTests

# Check if the build was successful
if [ $? -ne 0 ]; then
  echo "Build failed. Exiting..."
  exit 1
fi

cd ../teammanager-ui

echo "Building the Swing application..."
mvn clean package -DskipTests

# Check if the build was successful
if [ $? -ne 0 ]; then
  echo "Build failed. Exiting..."
  exit 1
fi

cd ..

echo "Starting the application with Docker Compose..."
docker compose up --build -d

if [ $? -ne 0 ]; then
  echo "Docker Compose failed. Exiting..."
  exit 1
fi

# Wait for containers to be healthy
echo "Waiting for containers to be healthy..."
healthy=false
retries=20
delay=10

while [ $retries -gt 0 ]; do
  db_status=$(docker inspect --format='{{.State.Health.Status}}' oracle-db 2>/dev/null)
  app_status=$(docker inspect --format='{{.State.Health.Status}}' teammanager-app 2>/dev/null)

  if [ "$db_status" == "healthy" ] && [ "$app_status" == "healthy" ]; then
    healthy=true
    break
  fi

  retries=$((retries - 1))
  echo "Waiting for services... Retries left: $retries"
  sleep $delay
done

if [ "$healthy" != "true" ]; then
  echo "Containers did not become healthy in time. Exiting..."
  exit 1
fi

echo "All containers are healthy!"

# Start the Swing application
java -jar ./teammanager-ui/target/teammanager-gui-1.0-SNAPSHOT-jar-with-dependencies.jar
