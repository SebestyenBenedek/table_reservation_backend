FROM adoptopenjdk:11-jre-hotspot

WORKDIR /app

EXPOSE 8761

COPY target/*.jar ./eureka-server.jar

ENTRYPOINT ["java","-jar","./eureka-server.jar"]
