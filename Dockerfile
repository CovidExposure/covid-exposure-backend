 FROM openjdk:8-jdk-alpine
 MAINTAINER MC
 EXPOSE 8080
 COPY ./target/record-0.0.1-SNAPSHOT.jar /app.jar
 ENTRYPOINT [ "java","-jar", "/app.jar"]