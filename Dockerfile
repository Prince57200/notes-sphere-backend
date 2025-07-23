# # ------------ Stage 1: Build WAR with Maven ------------
# FROM maven:3.9.4-eclipse-temurin-17 AS build

# # Copy source code
# COPY . /app
# WORKDIR /app

# # Build WAR file
# RUN mvn clean package -DskipTests

# # ------------ Stage 2: Deploy to Tomcat ------------
# FROM tomcat:9.0-jdk17

# # Remove default apps
# RUN rm -rf /usr/local/tomcat/webapps/*

# # Copy WAR from previous stage
# COPY --from=build /app/target/NoteTacker.war /usr/local/tomcat/webapps/ROOT.war

# EXPOSE 8080


# ------------ Stage 1: Build WAR with Maven ------------
FROM maven:3.9.4-eclipse-temurin-17 AS build

# Copy source code
COPY . /app
WORKDIR /app

# Build WAR file
RUN mvn clean package -DskipTests

# ------------ Stage 2: Deploy to Tomcat ------------
FROM tomcat:9.0-jdk17

# Set environment variables for SSL truststore
ENV TRUSTSTORE_PASSWORD=changeit
ENV JAVA_TOOL_OPTIONS="-Djavax.net.ssl.trustStore=/opt/ssl/aiven-ca.jks -Djavax.net.ssl.trustStorePassword=${TRUSTSTORE_PASSWORD}"

# Create SSL directory
RUN mkdir -p /opt/ssl

# Copy the keystore (you must place `aiven-ca.jks` in the root of your project)
COPY aiven-ca.jks /opt/ssl/aiven-ca.jks

# Remove default apps
RUN rm -rf /usr/local/tomcat/webapps/*

# Copy WAR file from build stage
COPY --from=build /app/target/NoteTacker.war /usr/local/tomcat/webapps/ROOT.war

EXPOSE 8080
