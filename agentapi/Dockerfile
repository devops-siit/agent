FROM maven:3.8.5-openjdk-18 AS build
COPY src /src
COPY pom.xml /
RUN mvn -f /pom.xml clean package

FROM openjdk:18-oracle
COPY --from=build target/agentapi-0.0.1-SNAPSHOT.jar /agentapi-0.0.1.jar
EXPOSE 8089
ENTRYPOINT ["java", "-jar", "/agentapi-0.0.1.jar"]