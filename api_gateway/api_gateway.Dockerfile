FROM maven:3.8.2-jdk-11 AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package

FROM openjdk:17-jdk-alpine3.13 as release

WORKDIR /app

EXPOSE 8081

COPY --from=build /app/target/*.jar /app/api-gateway.jar

ENV SPRING_PROFILES_ACTIVE=production
ENV EUREKA_CLIENT_SERVICEURL_DEFAULTZONE=http://eureka-server:8761/eureka/

RUN adduser -D -h /app -u 5000 myuser && \
    chown -R myuser:myuser /app

USER myuser

ENTRYPOINT ["java", "-jar", "/app/api-gateway.jar"]
