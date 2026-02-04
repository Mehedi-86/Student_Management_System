# 1. Use the modern standard for Java 17 (Eclipse Temurin)
FROM eclipse-temurin:17-jdk

# 2. Set the working directory inside the container
WORKDIR /app

# 3. Copy the built Jar file into the container
COPY target/*.jar app.jar

# 4. Expose port 8080 so we can access the app
EXPOSE 8080

# 5. Run the jar file
ENTRYPOINT ["java", "-jar", "app.jar"]