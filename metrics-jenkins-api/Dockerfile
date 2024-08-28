# Building stage
FROM maven:3.5-jdk-8 AS build
COPY src /usr/src/app/src
COPY pom.xml /usr/src/app
RUN mvn -f /usr/src/app/pom.xml clean package


# Packaging stage
FROM gcr.io/distroless/java
COPY --from=build /usr/src/app/target/metrics-jenkins-api-0.0.1-SNAPSHOT.jar /usr/app/metrics-jenkins-api-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/usr/app/metrics-jenkins-api.jar"]