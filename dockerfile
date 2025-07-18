# Étape 1 : Build avec Maven
FROM maven:latest AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package

# Étape 2 : Exécution de l'application
FROM openjdk:21-jdk-slim
WORKDIR /app
COPY --from=builder /app/target/password-manager-1.0-SNAPSHOT.jar /app/password-manager.jar
ENTRYPOINT ["java", "-jar", "/app/password-manager.jar"]