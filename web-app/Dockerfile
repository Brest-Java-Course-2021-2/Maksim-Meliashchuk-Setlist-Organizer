FROM adoptopenjdk/openjdk11:jre
WORKDIR usr/src/app
COPY target/web-app-1.0-SNAPSHOT.jar web-app.jar
ENTRYPOINT ["java","-jar","web-app.jar"]