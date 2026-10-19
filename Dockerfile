FROM eclipse-temurin:21-jre
WORKDIR /app
COPY build/libs/devops-cicd-1.0.0.jar app.jar
USER 10001:10001
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
