# Use Tomcat with Java 17
FROM tomcat:9.0-jdk17

# Clean default webapps (optional)
RUN rm -rf /usr/local/tomcat/webapps/*

# Copy your WAR into ROOT.war so it runs on /
COPY target/NoteTacker.war /usr/local/tomcat/webapps/ROOT.war

# Expose port 8080 (Render uses this)
EXPOSE 8080
