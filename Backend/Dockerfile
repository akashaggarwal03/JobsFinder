# Use a multi-architecture JDK image
FROM --platform=linux/amd64 eclipse-temurin:17-jdk-alpine

# Set the working directory
WORKDIR /JobsFinder/Backend

# Copy the JAR file into the container
COPY build/libs/JobsFinder-0.0.1-SNAPSHOT.jar app.jar

# Expose the port your app runs on
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]