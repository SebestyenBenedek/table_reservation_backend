# Base image with alias
FROM openjdk:17-jdk-alpine3.13 as build

WORKDIR /app

EXPOSE 8088

COPY target/*.jar /app/app.jar

# Release image with alias
FROM build as release

ENV SPRING_PROFILES_ACTIVE=production

RUN adduser -D -h /app -u 5000 myuser && \
    chown -R myuser:myuser /app

USER myuser

ENTRYPOINT ["java", "-jar", "/app/app.jar"]