@echo off
REM Clean start
if exist target rmdir /s /q target

REM Build with Maven
if exist mvnw.cmd (
    call mvnw.cmd clean package -q
) else (
    call mvn clean package -q
)

REM Run the Spring Boot CLI app
java -jar target\libraryms-0.0.1-SNAPSHOT.jar

pause