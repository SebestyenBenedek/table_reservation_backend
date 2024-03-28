# Base image with alias
FROM openjdk:17-jdk-alpine3.13 as build

WORKDIR /app

EXPOSE 8081

COPY target/*.jar /app/api-gateway.jar

# Release image with alias
FROM build as release

ENV SPRING_PROFILES_ACTIVE=production
ENV EUREKA_CLIENT_SERVICEURL_DEFAULTZONE=http://eureka-server:8761/eureka/

RUN adduser -D -h /app -u 5000 myuser && \
    chown -R myuser:myuser /app

USER myuser

ENTRYPOINT ["java", "-jar", "/app/api-gateway.jar"]
