FROM eclipse-temurin:17-jdk-jammy

WORKDIR /app

COPY target/api-gateway-0.0.1-SNAPSHOT.jar app.jar

COPY newrelic/ /usr/local/newrelic/
RUN chmod 755 /usr/local/newrelic/newrelic.jar

EXPOSE 8085 18003

ENTRYPOINT ["java", "-javaagent:/usr/local/newrelic/newrelic.jar", "-Dnewrelic.config.file=/usr/local/newrelic/newrelic.yml", "-jar", "app.jar"]