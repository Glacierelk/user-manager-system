FROM openjdk:23-ea-21-jdk-slim
LABEL authors="Glacier-elk"

WORKDIR /app

COPY ./build/libs/EurekaServiceCenter-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8729

ENTRYPOINT ["java", "-jar", "app.jar"]