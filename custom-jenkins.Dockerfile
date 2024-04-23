# Stage 1: Base image with necessary tools
FROM jenkins/jenkins:lts AS builder

# Switch to root user to install packages
USER root

# Update the package list, install necessary packages, and clean up
RUN apt-get update && \
    apt-get -y install apt-transport-https \
        ca-certificates \
        curl \
        gnupg2 \
        software-properties-common && \
    rm -rf /var/lib/apt/lists/*

# Add Docker's official GPG key
RUN curl -fsSL https://download.docker.com/linux/debian/gpg | apt-key add -

# Add Docker repository to APT sources
RUN add-apt-repository "deb [arch=amd64] https://download.docker.com/linux/debian $(lsb_release -cs) stable"

# Install Docker and clean up
RUN curl -fsSL https://get.docker.com -o get-docker.sh && \
    sh get-docker.sh && \
    rm get-docker.sh && \
    rm -rf /var/lib/apt/lists/*

# Install Maven and clean up
RUN apt-get update && apt-get install -y maven && \
    rm -rf /var/lib/apt/lists/*


# Stage 2: Final Jenkins image
FROM jenkins/jenkins:lts

# Copy necessary artifacts from the builder stage
COPY --from=builder /usr/bin/docker /usr/bin/docker
COPY --from=builder /usr/bin/mvn /usr/bin/mvn

# Set the Jenkins reference directory
ENV JENKINS_REF /usr/share/jenkins/ref

# Copy the plugins.txt file to the Jenkins reference directory
COPY ./plugins.txt $JENKINS_REF/

# Install plugins using the jenkins-plugin-cli
RUN jenkins-plugin-cli --plugin-file "$JENKINS_REF"/plugins.txt

# Switch to root user to create docker group
USER root

# Create the docker group and add Jenkins user to it
RUN groupadd docker && usermod -a -G docker jenkins

# Switch back to Jenkins user
USER jenkins

# Create the plugins directory in the Jenkins reference directory
RUN mkdir -p "$JENKINS_REF"/plugins