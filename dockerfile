# Use an official OpenJDK runtime as a parent image
FROM  openjdk:22-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the project's compiled jar file to the container
COPY target/*.jar app.jar



# Make port 8080 available to the world outside this container
EXPOSE 8080

# Environment variables
ENV DB_Name=MessengerApp
ENV DB_Password=root
ENV DB_Port=5432
ENV DB_USER=postgres
ENV Host_Name=host.docker.internal

# Run the jar file
ENTRYPOINT ["java", "-jar", "app.jar"]
