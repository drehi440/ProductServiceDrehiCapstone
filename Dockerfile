# === Build stage ===
FROM maven:3.9-amazoncorretto-21 AS build

WORKDIR /app

# Cache dependencies
COPY pom.xml .
RUN mvn -q -B -e -DskipTests dependency:go-offline

# Copy sources and build
COPY src ./src
RUN mvn -q -B -DskipTests clean package

# === Runtime stage ===
FROM amazoncorretto:21-alpine3.20 AS runtime

ENV SPRING_PROFILES_ACTIVE=prod
WORKDIR /app

COPY --from=build /app/target/*.jar /app/app.jar

EXPOSE 8080

ENTRYPOINT ["java","-Dspring.profiles.active=prod","-jar","/app/app.jar"]


