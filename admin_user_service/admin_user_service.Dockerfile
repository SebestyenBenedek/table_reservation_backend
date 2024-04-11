FROM eclipse-temurin:17-jdk AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package

FROM openjdk:17-jdk-alpine3.13 as release

WORKDIR /app

EXPOSE 8085

COPY --from=build /app/target/*.jar /app/app.jar

ENV SPRING_PROFILES_ACTIVE=production

RUN adduser -D -h /app -u 5000 myuser && \
    chown -R myuser:myuser /app

USER myuser

ENTRYPOINT ["java", "-jar", "/app/app.jar"]