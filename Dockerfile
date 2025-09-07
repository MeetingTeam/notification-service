FROM openjdk:17-jdk-alpine

# Change directory
WORKDIR /app

# Download the OTel Java Agent
ARG OTEL_AGENT_VERSION=2.8.0
ARG OTEL_AGENT_JAR=/app/opentelemetry-javaagent.jar
ADD https://github.com/open-telemetry/opentelemetry-java-instrumentation/releases/download/v${OTEL_AGENT_VERSION}/opentelemetry-javaagent.jar $OTEL_AGENT_JAR

# Copy war file
COPY target/notification-service-0.0.1-SNAPSHOT.war notification-service.war

# Create non-root user
RUN adduser -D notification_service
RUN chown -R notification_service:notification_service /app
USER notification_service

# Run app
ENTRYPOINT ["sh","-c","java -javaagent:opentelemetry-javaagent.jar -jar -Dspring.config.location=${CONFIG_PATH} notification_service.war"]

# Expose port 8086
EXPOSE 8086