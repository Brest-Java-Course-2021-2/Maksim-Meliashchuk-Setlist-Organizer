FROM adoptopenjdk/openjdk11:jre
WORKDIR usr/src/app
COPY target/rest-app-1.0-SNAPSHOT.jar rest-app.jar
ENTRYPOINT ["java","-jar", "-Dspring.profiles.active=postgres, jpa", "rest-app.jar"]