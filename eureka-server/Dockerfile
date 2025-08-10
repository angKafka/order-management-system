FROM eclipse-temurin:17-jdk-jammy

WORKDIR /app

# Copy your Spring Boot fat jar
COPY target/eureka-server-0.0.1-SNAPSHOT.jar app.jar

# Copy New Relic agent files into the image (make sure you have the newrelic folder with jar and yml next to this Dockerfile)
COPY newrelic/ /usr/local/newrelic/
RUN chmod 755 /usr/local/newrelic/newrelic.jar

EXPOSE 8761

ENTRYPOINT ["java", "-javaagent:/usr/local/newrelic/newrelic.jar", "-jar", "app.jar"]