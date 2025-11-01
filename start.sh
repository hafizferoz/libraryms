#!/bin/bash
# Clean start
rm -rf target

if [ -f "./mvnw" ]; then
  ./mvnw clean package -q
else
  mvn clean package -q
fi

# Run the Spring Boot CLI app
java -jar target/libraryms-0.0.1-SNAPSHOT.jar