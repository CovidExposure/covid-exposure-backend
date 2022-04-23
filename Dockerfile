 FROM openjdk:8-jdk-alpine
 MAINTAINER MC

 ADD . /record
 WORKDIR /record
 
 RUN [ "./mvnw", "package", "-Dmaven.test.skip" ]
 EXPOSE 8080
 ENTRYPOINT [ "java","-jar", "./target/record-0.0.1-SNAPSHOT.jar","--spring.profiles.active=dev"]