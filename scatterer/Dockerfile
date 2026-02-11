# -------- Build Stage --------
FROM maven:3.9.9-eclipse-temurin-21 AS builder

WORKDIR /build

# Copy entire project (important for multimodule)
COPY .. .

# Build only scatterer and its dependencies
RUN mvn -pl scatterer -am clean package -DskipTests


# -------- Runtime Stage --------
FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

COPY --from=builder /build/scatterer/target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]