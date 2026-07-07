FROM eclipse-temurin:17-jre

ENV SERVER_PORT=8080
EXPOSE 8080

WORKDIR /app

COPY target/workspace-service-*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]