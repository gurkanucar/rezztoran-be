## Build stage
FROM maven:3.8.4-jdk-11-slim AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline

COPY src/ /app/src/
RUN mvn clean package -DskipTests

# Step : Package image
FROM openjdk:11-jdk-slim
ARG JAR_FILE=target/rezztoran-be-0.0.1-SNAPSHOT.jar
COPY --from=build /app/${JAR_FILE} rezztoran-be-0.0.1-SNAPSHOT.jar
EXPOSE 8082
#ENTRYPOINT ["java", "-jar" , "rezztoran-be-0.0.1-SNAPSHOT.jar"]