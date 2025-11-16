# Stage 1: Build the JAR
FROM maven:3.9.6-eclipse-temurin-22 AS builder
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Stage 2: Run the JAR
FROM openjdk:22-jdk-slim
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
ENV DB_NAME=yapper_abb9
ENV DB_USER=yapper
ENV DB_PASSWORD=BQZwYUDk6A0isbITwBJ0oHXsjLP9AoOX
ENV DB_PORT=5432
ENV HOST_NAME=dpg-d4549s6mcj7s73ft69e0-a.frankfurt-postgres.render.com
EXPOSE 8080
ENV Host_Name=host.docker.internal
ENTRYPOINT ["java", "-jar", "app.jar"]
