@echo off

cd teammanager

echo Building the Spring Boot application...
mvn clean package -DskipTests

if %ERRORLEVEL% neq 0 (
    echo Build failed. Exiting...
    exit /b 1
)

cd ..\teammanager-ui

echo Building the Swing application...
mvn clean package -DskipTests

if %ERRORLEVEL% neq 0 (
    echo Build failed. Exiting...
    exit /b 1
)

cd ..

echo Starting the application with Docker Compose...
docker compose up --build -d

if %ERRORLEVEL% neq 0 (
    echo Docker Compose failed. Exiting...
    exit /b 1
)

:: Wait for containers to be healthy
echo Waiting for containers to be healthy...
set healthy=false
set /a retries=20
set /a delay=10

:wait_healthy
docker inspect --format="{{.State.Health.Status}}" oracle-db > status_oracle.txt 2>nul
docker inspect --format="{{.State.Health.Status}}" teammanager-app > status_app.txt 2>nul

for /f %%i in (status_oracle.txt) do if "%%i"=="healthy" set db_healthy=true
for /f %%i in (status_app.txt) do if "%%i"=="healthy" set app_healthy=true

if "%db_healthy%"=="true" if "%app_healthy%"=="true" (
    set healthy=true
)

if "%healthy%"=="false" (
    set /a retries-=1
    if %retries% leq 0 (
        echo Containers did not become healthy in time. Exiting...
        exit /b 1
    )
    timeout /t %delay% >nul
    goto wait_healthy
)

echo All containers are healthy!

:: Start the Swing application
java -jar teammanager-ui\target\teammanager-gui-1.0-SNAPSHOT-jar-with-dependencies.jar