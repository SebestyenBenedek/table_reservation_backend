# Stage 1: Base image with necessary tools
FROM openjdk:17-jdk-alpine3.13 AS build

# Install Git, Maven, and other necessary tools
RUN apk update && \
    apk add --no-cache git maven && \
    rm -rf /var/cache/apk/*

# Stage 2: Final Jenkins agent image
FROM openjdk:17-jdk-alpine3.13

# Copy necessary artifacts from the builder stage
COPY --from=build /usr/bin/git /usr/bin/git
COPY --from=build /usr/bin/mvn /usr/bin/mvn

# Expose the SSH port
EXPOSE 50000

# Run Jenkins agent
CMD ["java", "-jar", "/usr/share/jenkins/agent.jar"]