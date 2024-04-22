FROM maven:3.8.4-openjdk-17 AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-alpine3.13

WORKDIR /app

EXPOSE 8081

COPY --from=build /app/target/*.jar /app/api-gateway.jar

ENV EUREKA_CLIENT_SERVICEURL_DEFAULTZONE=http://eureka-server:8761/eureka/

RUN adduser -D -h /app -u 5000 appuser && \
    chown -R appuser:appuser /app

USER appuser

ENTRYPOINT ["java", "-jar", "/app/api-gateway.jar"]
