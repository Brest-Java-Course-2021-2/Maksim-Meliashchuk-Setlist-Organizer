FROM openjdk:11
WORKDIR usr/src/app
COPY target/rest-app-1.0-SNAPSHOT.jar rest-app.jar
ENTRYPOINT ["java","-jar","rest-app.jar"]