FROM maven:3.8.2-jdk-17 AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package

FROM openjdk:17-jdk-alpine3.13 as release

WORKDIR /app

EXPOSE 8088

COPY --from=build /app/target/*.jar /app/app.jar

ENV SPRING_PROFILES_ACTIVE=production

RUN adduser -D -h /app -u 5000 myuser && \
    chown -R myuser:myuser /app

USER myuser

ENTRYPOINT ["java", "-jar", "/app/app.jar"]