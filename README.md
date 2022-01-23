# Maksim-Meliashchuk-Setlist-Organizer

<div align="center">

[![Java CI with Maven](https://github.com/Brest-Java-Course-2021-2/Maksim-Meliashchuk-Setlist-Organizer/actions/workflows/maven.yml/badge.svg)](https://github.com/Brest-Java-Course-2021-2/Maksim-Meliashchuk-Setlist-Organizer/actions/workflows/maven.yml)
[![Codacy Security Scan](https://github.com/Brest-Java-Course-2021-2/Maksim-Meliashchuk-Setlist-Organizer/actions/workflows/codacy-analysis.yml/badge.svg)](https://github.com/Brest-Java-Course-2021-2/Maksim-Meliashchuk-Setlist-Organizer/actions/workflows/codacy-analysis.yml)
[![License](https://img.shields.io/badge/License-Apache_2.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

<img src="https://validator.swagger.io/validator?url=https://setlist-organizer-rest.herokuapp.com/v3/api-docs">
</div>

## Project Information

'Setlist Organizer' is a web application for organizing repertoires of musical bands.

<details>
<summary>Mind Map</summary>
  <p align="center">
    <img src="documentation/setlist_organizer_mind_map.svg" alt="Mind map"/>
  </p>
</details>

- [Software requirements specification](documentation/SetlistOrganizerSRC.md)
- [Available REST endpoints](documentation/SetlistOrganizerEndpoints.md)

## Applications Demo on [Heroku](https://heroku.com") <a href="https://heroku.com" target="_blank" rel="noreferrer"> <img src="https://www.vectorlogo.zone/logos/heroku/heroku-icon.svg" alt="heroku" width="25" height="25"/> </a>

The web application will be accessible at:

[https://setlist-organizer-web.herokuapp.com/](https://setlist-organizer-web.herokuapp.com/)

The rest application will be accessible at:

[https://setlist-organizer-rest.herokuapp.com/](https://setlist-organizer-rest.herokuapp.com/bands)

API documentation with Swagger UI:

[https://setlist-organizer-rest.herokuapp.com/swagger-ui/index.html](https://setlist-organizer-rest.herokuapp.com/swagger-ui/index.html)

## Technology Stack

- **Programming Language:** [Java](https://www.java.com) <a href="https://www.java.com" target="_blank"> <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/java/java-original.svg" alt="java" width="20" height="20"/> </a>
- **Core Framework:** [Spring Boot](https://spring.io/projects/spring-boot) <a href="https://spring.io/projects/spring-boot" target="_blank" rel="noreferrer"> <img src="https://www.vectorlogo.zone/logos/springio/springio-icon.svg" alt="spring" width="18" height="18"/> </a>
- **Validation Framework:** [Hibernate Validator](https://hibernate.org/validator/)
- **Build System:** [Maven](https://maven.apache.org/)
- **Control System:** [Git](https://git-scm.com/) <a href="https://git-scm.com/" target="_blank" rel="noreferrer"> <img src="https://www.vectorlogo.zone/logos/git-scm/git-scm-icon.svg" alt="git" width="18" height="18"/> </a>
- **License:** [Apache license, version 2.0](http://www.apache.org/licenses/LICENSE-2.0)
- **Code Style:** [Codacy](https://www.codacy.com/)
- **Automated Testing:**
    - [JUnit5](https://junit.org/junit5/)
    - [Mockito](http://site.mockito.org/)
- **Log:** [Log4j 2](https://logging.apache.org/log4j/2.x/)
- **Database:** [H2](http://www.h2database.com/html/main.html)
- **JSON library:** [Jackson](https://github.com/FasterXML/jackson)
- **Annotations:** [Lombok](https://projectlombok.org/)
- **API documentation generation:**
  - [Springdoc-openapi](https://springdoc.org/)
  - [Swagger UI](https://swagger.io/tools/swagger-ui/)
- **Template Engine:** [Thymeleaf](https://www.thymeleaf.org/)
- **CSS Framework:** [Bootstrap](https://getbootstrap.com/) <a href="https://getbootstrap.com" target="_blank" rel="noreferrer"> <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/bootstrap/bootstrap-plain-wordmark.svg" alt="bootstrap" width="20" height="20"/> </a>
- **App containerization:** [Docker](https://www.docker.com/) <a href="https://www.docker.com/" target="_blank" rel="noreferrer"> <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/docker/docker-original-wordmark.svg" alt="docker" width="20" height="20"/> </a>

## Requirements

The following software is required for the complete workflow (from git clone to the running Docker Container). 
The specified versions are the tested ones. 

* JDK 11+
* Git 2.25.1+
* Apache Maven 3.6.3+
* Docker 20.10.12+
* Docker-compose 1.29.2+

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

## Local tests with Postman

To test the REST API, you can use the Postman collection to access the API endpoints:

[postman_collection.json](./documentation/Setlist_organizer_rest_app.postman_collection.json)

See the Postman [online documentation](https://learning.postman.com/docs/getting-started/installation-and-updates/).

## Documenting a REST API

Using OpenAPI 3.0

The OpenAPI descriptions in JSON format will be available at the path: 
[http://localhost:8088/v3/api-docs](http://localhost:8088/v3/api-docs)

API documentation with Swagger UI: 
[http://localhost:8088/swagger-ui/index.html](http://localhost:8088/swagger-ui/index.html)
