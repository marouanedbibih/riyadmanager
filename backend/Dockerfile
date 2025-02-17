# Stage 1: Build the application
FROM eclipse-temurin:21-jdk-alpine AS build 

VOLUME /tmp

WORKDIR /app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

RUN ./mvnw install -DskipTests

# Stage 2: Create the final image
FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

# Define build arguments
ARG SPRING_DATASOURCE_URL
ARG SPRING_DATASOURCE_USERNAME
ARG SPRING_DATASOURCE_PASSWORD
ARG SPRING_PROFILES_ACTIVE
ARG CORS_ALLOWED_ORIGINS

# Set environment variables from build arguments
ENV SPRING_DATASOURCE_URL=${SPRING_DATASOURCE_URL}
ENV SPRING_DATASOURCE_USERNAME=${SPRING_DATASOURCE_USERNAME}
ENV SPRING_DATASOURCE_PASSWORD=${SPRING_DATASOURCE_PASSWORD}
ENV SPRING_PROFILES_ACTIVE=${SPRING_PROFILES_ACTIVE}
ENV CORS_ALLOWED_ORIGINS=${CORS_ALLOWED_ORIGINS}

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080 

ENTRYPOINT ["java", "-jar", "app.jar"]
