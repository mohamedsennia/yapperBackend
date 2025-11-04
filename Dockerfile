FROM maven:3.9.6-eclipse-temurin-22 AS builder

RUN mvn clean package -DskipTests
# Use an official OpenJDK runtime as a parent image
FROM  openjdk:22-jdk-slim

# Set the working directory in the container
WORKDIR /app


COPY --from=builder /app/target/*.jar app.jar



# Make port 8080 available to the world outside this container
EXPOSE 8080

# Environment variable
ENV Host_Name=host.docker.internal

# Run the jar file
ENTRYPOINT ["java", "-jar", "app.jar"]
