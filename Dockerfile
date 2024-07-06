#FROM maven:3.8.5-openjdk-11-slim AS build
## Set the working directory inside the container
#WORKDIR /app
#
## Copy the Maven project descriptor files
#COPY pom.xml .
#COPY src ./src
#RUN mvn clean install

FROM openjdk:17
#FROM ibmjava:jre
#FROM ubuntu
#WORKDIR /app
EXPOSE 8080
ADD target/user-management.jar user-management.jar
ENTRYPOINT ["java", "-jar", "/user-management.jar"]
#CMD ["java", "-jar", "/user-management.jar"]
#ENTRYPOINT ["java", "-jar", "/user-management.jar --spring.profiles.active=test"]