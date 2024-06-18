FROM openjdk:23-ea-21-jdk-slim
LABEL authors="Glacier-elk"

WORKDIR /app

COPY ./build/libs/UserInformationMaintenanceService-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8730

ENTRYPOINT ["java", "-jar", "app.jar"]