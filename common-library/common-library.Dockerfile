# Use OpenJDK 17 base image
FROM eclipse-temurin:17-jdk-jammy

# Set working directory
WORKDIR /app

# Copy built common-library JAR (built with 'mvn clean install' or 'package')
COPY target/common-library-0.0.1-SNAPSHOT.jar common-library.jar

# No ENTRYPOINT or CMD needed because this is a library, not a runnable service
# This image just holds the artifact for sharing