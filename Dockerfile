FROM openjdk:17
#FROM ibmjava:jre
#FROM ubuntu
EXPOSE 8080
ADD target/user-management.jar user-management.jar
ENTRYPOINT ["java", "-jar", "/user-management.jar"]
#ENTRYPOINT ["java", "-jar", "/user-management.jar --spring.profiles.active=test"]