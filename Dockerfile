# ========================================
# Etapa 1: Build
# ========================================
FROM gradle:8.4-jdk17 AS builder
WORKDIR /app
COPY . .
RUN gradle clean bootJar --no-daemon

# ========================================
# Etapa 2: Run
# ========================================
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar

# Puerto dinámico de Render
EXPOSE 8080

# Activar perfil render
ENV SPRING_PROFILES_ACTIVE=render

ENTRYPOINT ["java", "-Dserver.port=${PORT:-8080}", "-jar", "app.jar"]