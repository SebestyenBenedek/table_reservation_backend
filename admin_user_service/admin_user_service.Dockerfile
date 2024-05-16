FROM maven:3.8.4-openjdk-17 AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-alpine3.13

RUN apk add --no-cache curl

WORKDIR /app

EXPOSE 8085

COPY --from=build /app/target/*.jar /app/app.jar

RUN adduser -D -h /app -u 5000 appuser && \
    chown -R appuser:appuser /app

USER appuser

ENTRYPOINT ["java", "-jar", "/app/app.jar"]