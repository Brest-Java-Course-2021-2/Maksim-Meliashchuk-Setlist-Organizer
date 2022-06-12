FROM adoptopenjdk/openjdk11:jre
WORKDIR usr/src/app
COPY target/eurekaserver-1.0-SNAPSHOT.jar eurekaserver.jar
ENTRYPOINT ["java","-jar","eurekaserver.jar"]