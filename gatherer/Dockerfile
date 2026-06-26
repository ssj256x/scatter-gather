# ==========================================
# STAGE 1: Build & Compile (Monorepo Context)
# ==========================================
FROM maven:3.9.9-eclipse-temurin-21-alpine AS builder
WORKDIR /build

# 1. Copy the parent POM and the POMs of the required modules
COPY pom.xml .
COPY common/pom.xml ./common/
COPY worker/pom.xml ./worker/
COPY scatterer/pom.xml ./scatterer/
COPY gatherer/pom.xml ./gatherer/
COPY observability/pom.xml ./observability/

# 2. Go offline to download and cache external dependencies ahead of copying source code
RUN mvn dependency:go-offline -B

# 3. Copy the actual source files for the shared library and the target module
COPY common/src ./common/src
COPY gatherer/src ./gatherer/src

# 4. Compile the common module and the worker module using the Maven reactor
RUN mvn -pl gatherer -am clean package -DskipTests -Dmaven.javadoc.skip=true

# ==========================================
# STAGE 2: Ultra-Lean Runtime Execution
# ==========================================
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Install libstdc++ for RocksDB
RUN apk add --no-cache libstdc++

# Create a non-privileged system user for runtime safety
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# Copy only the compiled fat JAR from the builder stage
COPY --from=builder --chown=spring:spring /build/gatherer/target/*.jar app.jar

# Apply the leanest JVM footprint options block for your Pi 5 RAM limits
ENV JAVA_OPTS="-XX:+UseSerialGC \
               -XX:MaxRAMPercentage=75.0 \
               -Xss256k \
               -XX:TieredStopAtLevel=1"

EXPOSE 8080
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]