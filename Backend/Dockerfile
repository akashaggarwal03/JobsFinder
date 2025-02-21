# Use a JDK image for the build stage
FROM eclipse-temurin:21-jdk AS build


# Set the working directory
WORKDIR /JobsFinder/Backend

# Copy only essential files first (for caching dependencies)
COPY gradle/ gradle/
COPY build.gradle settings.gradle gradlew ./
COPY src ./src

# Make the Gradle wrapper executable and build the application
RUN chmod +x gradlew && ./gradlew build -x test --stacktrace

# Use a JDK image for the build stage
FROM eclipse-temurin:21-jdk


# Set the working directory
WORKDIR /JobsFinder/Backend

# Copy the built JAR from the first stage
COPY --from=build /JobsFinder/Backend/build/libs/*.jar app.jar

# Expose the port your app runs on
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]