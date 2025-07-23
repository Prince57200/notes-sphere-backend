# ------------ Stage 1: Build WAR with Maven ------------
FROM maven:3.9.4-eclipse-temurin-17 AS build

# Copy source code
COPY . /app
WORKDIR /app

# Build WAR file
RUN mvn clean package -DskipTests

# ------------ Stage 2: Deploy to Tomcat ------------
FROM tomcat:9.0-jdk17

# Remove default apps
RUN rm -rf /usr/local/tomcat/webapps/*

# Copy WAR from previous stage
COPY --from=build /app/target/NoteTacker.war /usr/local/tomcat/webapps/ROOT.war

EXPOSE 8080
