FROM sonarqube:10.5.0-community

# Set SonarQube home directory
ENV SONARQUBE_HOME /opt/sonarqube

# Switch to root user to change permissions
USER root

# Create the plugins directory if it doesn't exist and set appropriate permissions
RUN mkdir -p $SONARQUBE_HOME/extensions/plugins && \
    chmod 755 $SONARQUBE_HOME/extensions/plugins

# Download and install plugins
RUN wget "http://downloads.sonarsource.com/plugins/org/codehaus/sonar-plugins/sonar-scm-git-plugin/1.1/sonar-scm-git-plugin-1.1.jar" \
    && wget "https://github.com/SonarSource/sonar-java/releases/download/3.12-RC2/sonar-java-plugin-3.12-build4634.jar" \
    && wget "https://github.com/SonarSource/sonar-github/releases/download/1.1-M9/sonar-github-plugin-1.1-SNAPSHOT.jar" \
    && wget "https://github.com/SonarSource/sonar-auth-github/releases/download/1.0-RC1/sonar-auth-github-plugin-1.0-SNAPSHOT.jar" \
    && wget "https://github.com/QualInsight/qualinsight-plugins-sonarqube-badges/releases/download/qualinsight-plugins-sonarqube-badges-1.2.1/qualinsight-sonarqube-badges-1.2.1.jar" \
    && mv *.jar $SONARQUBE_HOME/extensions/plugins/ \
    && ls -lah $SONARQUBE_HOME/extensions/plugins/

# Create the sonar user
#RUN useradd -m sonar

# Create the docker group and add SonarQube user to it
#RUN groupadd docker && usermod -a -G docker sonar

# Switch back to SonarQube user
#USER sonarqube

