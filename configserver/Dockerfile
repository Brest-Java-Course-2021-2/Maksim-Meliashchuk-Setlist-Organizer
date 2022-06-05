FROM adoptopenjdk/openjdk11:jre
WORKDIR usr/src/app
COPY target/configserver-1.0-SNAPSHOT.jar configserver.jar
ENTRYPOINT ["java","-jar","configserver.jar"]