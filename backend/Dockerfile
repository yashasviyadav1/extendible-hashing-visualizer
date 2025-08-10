# Step 1: Build the application using Java 24 + Maven installed manually
FROM eclipse-temurin:24 AS build
WORKDIR /app

# Install Maven
RUN apt-get update && apt-get install -y maven

COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Step 2: Run the application using lightweight Java 24 JRE
FROM eclipse-temurin:24-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
