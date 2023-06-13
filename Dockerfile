# syntax = docker/dockerfile:1.2
#
# Build stage
#
FROM maven:3.8.6-openjdk-18 AS build
COPY . .
RUN mvn clean package assembly:single -DskipTests

#
# Package stage
#
FROM openjdk:17-jdk-slim
COPY --from=build /target/2023-tpa-sama-grupo-1-4.0-SNAPSHOT-jar-with-dependencies.jar 2023-tpa-sama-grupo-1.jar
# ENV PORT=8080
EXPOSE 8080
ENTRYPOINT ["java","-classpath","2023-tpa-sama-grupo-1.jar","org.utn.ServerApi"]
