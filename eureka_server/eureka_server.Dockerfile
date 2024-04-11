FROM maven:3.8.2-jdk-11 AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package

FROM adoptopenjdk:11-jre-hotspot as release

WORKDIR /app

EXPOSE 8761

COPY --from=build /app/target/*.jar ./eureka-server.jar

ENTRYPOINT ["java","-jar","./eureka-server.jar"]
