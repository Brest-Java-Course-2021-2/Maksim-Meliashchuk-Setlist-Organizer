# Maksim-Meliashchuk-Setlist-Organizer

[![Java CI with Maven](https://github.com/Brest-Java-Course-2021-2/Maksim-Meliashchuk-Setlist-Organizer/actions/workflows/maven.yml/badge.svg)](https://github.com/Brest-Java-Course-2021-2/Maksim-Meliashchuk-Setlist-Organizer/actions/workflows/maven.yml)
[![Codacy Security Scan](https://github.com/Brest-Java-Course-2021-2/Maksim-Meliashchuk-Setlist-Organizer/actions/workflows/codacy-analysis.yml/badge.svg)](https://github.com/Brest-Java-Course-2021-2/Maksim-Meliashchuk-Setlist-Organizer/actions/workflows/codacy-analysis.yml)
[![License](https://img.shields.io/badge/License-Apache_2.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

## Project Information

'Setlist Organizer' is a web application for organizing repertoires of musical bands.

- [Software requirements specification](documentation/SetlistOrganizerSRC.md)
- [Available REST endpoints](documentation/SetlistOrganizerEndpoints.md)

## Technology Stack

- **Programming Language:** [Java](https://www.java.com) <a href="https://www.java.com" target="_blank"> <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/java/java-original.svg" alt="java" width="20" height="20"/> </a>
- **Core Framework:** [Spring Boot](https://spring.io/projects/spring-boot) <a href="https://spring.io/projects/spring-boot" target="_blank" rel="noreferrer"> <img src="https://www.vectorlogo.zone/logos/springio/springio-icon.svg" alt="spring" width="18" height="18"/> </a>
- **Build System:** [Maven](https://maven.apache.org/)
- **Control System:** [Git](https://git-scm.com/)
- **License:** [Apache license, version 2.0](http://www.apache.org/licenses/LICENSE-2.0)
- **Code Style:** [Codacy](https://www.codacy.com/)
- **Automated Testing:** 
    - [JUnit5](https://junit.org/junit5/)
    - [Mockito](http://site.mockito.org/)
- **Log:** [Log4j 2](https://logging.apache.org/log4j/2.x/)
- **Database:** [H2](http://www.h2database.com/html/main.html)
- **Template Engine:** [Thymeleaf](https://www.thymeleaf.org/)
- **CSS Framework:** [Bootstrap](https://getbootstrap.com/) <a href="https://getbootstrap.com" target="_blank" rel="noreferrer"> <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/bootstrap/bootstrap-plain-wordmark.svg" alt="bootstrap" width="20" height="20"/> </a>

## Requirements

* JDK 11
* Apache Maven
* Docker 20.10.12
* Docker-compose 1.29.2


## Installation Information

```bash
  $ git clone https://github.com/Brest-Java-Course-2021-2/Maksim-Meliashchuk-Setlist-Organizer.git
  $ cd Maksim-Meliashchuk-Setlist-Organizer
  $ mvn clean install
```

## Run local tests (H2 in memory) 

In the root directory of the project:
```bash
$ java -jar rest-app/target/rest-app-1.0-SNAPSHOT.jar
```
The rest application will be accessible at [http://localhost:8088](http://localhost:8088).
```bash
$ java -jar web-app/target/web-app-1.0-SNAPSHOT.jar 
```
The web application will be accessible at [http://localhost:8080](http://localhost:8080).

## Run with docker-compose
In the root directory of the project start up the rest-app and web-app in one go:
```bash
$ sudo docker-compose up --build
```
The web application will be accessible at [http://localhost:8080](http://localhost:8080).
The rest application will be accessible at [http://localhost:8088](http://localhost:8088).

To stop the containers:
```bash
$ sudo docker-compose down
```


