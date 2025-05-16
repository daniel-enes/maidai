# Build stage
FROM eclipse-temurin:21-jdk-jammy as builder
WORKDIR /workspace

# Copy wrapper files first and set permissions
COPY .mvn/ .mvn
COPY mvnw .
RUN chmod +x mvnw

# Copy project files
COPY pom.xml .
COPY src src

# Build with tests skipped
RUN ./mvnw clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:21-jdk-jammy
WORKDIR /app
COPY --from=builder /workspace/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]