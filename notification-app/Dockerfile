FROM adoptopenjdk/openjdk11:jre
WORKDIR usr/src/app
COPY target/notification-app-1.0-SNAPSHOT.jar notification-app.jar
ENTRYPOINT ["java","-jar","notification-app.jar"]
