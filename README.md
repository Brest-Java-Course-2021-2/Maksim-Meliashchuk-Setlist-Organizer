# <div align="center"> <img width="30" alt="logo" src="documentation/logo.svg"> SETLIST ORGANIZER </div>

<div align="center">

[![Java CI with Maven](https://github.com/Brest-Java-Course-2021-2/Maksim-Meliashchuk-Setlist-Organizer/actions/workflows/maven.yml/badge.svg)](https://github.com/Brest-Java-Course-2021-2/Maksim-Meliashchuk-Setlist-Organizer/actions/workflows/maven.yml)
[![Codacy Security Scan](https://github.com/Brest-Java-Course-2021-2/Maksim-Meliashchuk-Setlist-Organizer/actions/workflows/codacy-analysis.yml/badge.svg)](https://github.com/Brest-Java-Course-2021-2/Maksim-Meliashchuk-Setlist-Organizer/actions/workflows/codacy-analysis.yml)
[![License](https://img.shields.io/badge/License-Apache_2.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

[![Run in Postman](https://run.pstmn.io/button.svg)](https://app.getpostman.com/run-collection/18999675-2eae966d-2b48-4890-91ff-a0d7f181caa6?action=collection%2Ffork&collection-url=entityId%3D18999675-2eae966d-2b48-4890-91ff-a0d7f181caa6%26entityType%3Dcollection%26workspaceId%3D56c614c7-7bb5-44fe-b171-746dba387b30)
<img src="https://validator.swagger.io/validator?url=https://setlist-organizer-rest.herokuapp.com/v3/api-docs">
[![Swagger](https://img.shields.io/badge/Swagger-85EA2D?style=for-the-badge&logo=Swagger&logoColor=black)](https://setlist-organizer-rest.herokuapp.com/swagger-ui/index.html)
[![Heroku](https://img.shields.io/badge/Heroku-430098?style=for-the-badge&logo=heroku&logoColor=white)](https://setlist-organizer-web.herokuapp.com/)


<sub>Built by <a href="https://github.com/Maxxx873">Maksim Meliashchuk</a>

</div>

## Table of content

- [Project Information](#project-information)
- [Applications Demo](#applications-demo)
- [Technology Stack](#technology-stack)
- [Requirements](#requirements)
- [Installation Information](#installation-information)
- [OpenAPI generated server](#openapi-generated-server)
- [Swagger generated client](#swagger-generated-client)
- [Web clients configure](#web-clients-configure)
- [Run local tests](#run-local-tests)
- [Run application with PostgreSQL](#run-application-with-postgresql)
- [Run with docker-compose](#run-with-docker-compose)
- [Local tests with Postman](#local-tests-with-postman)
- [Documenting a REST API](#documenting-a-rest-api)
- [Excel Import and Export](#excel-import-and-export)

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
- [Presentation](documentation/SetlistOrganizer.pdf)

_<sub>Note: the Mind map, Software requirements specification, Available REST endpoints and Presentation 
describe the basic version of the project.The project is constantly being improved and developed.
The features of the current version of the project may differ from the information in this section.
See the following sections in this README for up-to-date project information.</a>_


## Applications Demo

Applications deployed on [Heroku](https://heroku.com/) <a href="https://heroku.com" target="_blank" rel="noreferrer"> <img src="https://www.vectorlogo.zone/logos/heroku/heroku-icon.svg" alt="heroku" width="25" height="25"/> </a>

The web application will be accessible at:

[https://setlist-organizer-web.herokuapp.com/](https://setlist-organizer-web.herokuapp.com/)

The rest application will be accessible at:

[https://setlist-organizer-rest.herokuapp.com/](https://setlist-organizer-rest.herokuapp.com/bands)

API documentation with Swagger UI:

[https://setlist-organizer-rest.herokuapp.com/swagger-ui/index.html](https://setlist-organizer-rest.herokuapp.com/swagger-ui/index.html)

## Technology Stack

- **Programming Language:** [Java](https://www.java.com) 
<a href="https://www.java.com" target="_blank"> <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/java/java-original.svg" alt="java" width="20" height="20"/> </a>
- **Core Framework:** [Spring boot](https://spring.io/projects/spring-boot)
<a href="https://spring.io/projects/spring-boot" target="_blank" rel="noreferrer"> <img src="https://www.vectorlogo.zone/logos/springio/springio-icon.svg" alt="spring" width="18" height="18"/> </a>
- **Validation Framework:** [Hibernate Validator](https://hibernate.org/validator/) <img height="20" width="20" src="https://www.vectorlogo.zone/logos/hibernate/hibernate-icon.svg"/>
- **Build System:** [Maven](https://maven.apache.org/) <img height="20" width="20" src="https://raw.githubusercontent.com/vscode-icons/vscode-icons/master/icons/file_type_maven.svg"/>
- **Control System:** [Git](https://git-scm.com/) <a href="https://git-scm.com/" target="_blank" rel="noreferrer"> <img src="https://www.vectorlogo.zone/logos/git-scm/git-scm-icon.svg" alt="git" width="18" height="18"/> </a>
- **License:** [Apache license, version 2.0](http://www.apache.org/licenses/LICENSE-2.0)
- **Code Style:** [Codacy](https://www.codacy.com/) <img height="20" width="20" src="https://unpkg.com/simple-icons@v6/icons/codacy.svg"/>
- **Automated Testing:**
    - [JUnit5](https://junit.org/junit5/) <img height="20" width="20" src="https://unpkg.com/simple-icons@v6/icons/junit5.svg"/>
    - [Mockito](http://site.mockito.org/)
- **Log:** 
  - [Log4j 2](https://logging.apache.org/log4j/2.x/)
  - [Logback](https://logback.qos.ch/)
- **Monitoring:**
  - [Micrometer](https://micrometer.io/)
  - [Prometheus](https://prometheus.io/) <img height="20" width="20" src="https://www.vectorlogo.zone/logos/prometheusio/prometheusio-icon.svg"/>
  - [Grafana](https://grafana.com/) <img height="20" width="20" src="https://www.vectorlogo.zone/logos/grafana/grafana-icon.svg"/>
- **Database:** 
  - [H2](http://www.h2database.com/html/main.html)
  - [PostgreSQL](https://www.postgresql.org/) <a href="https://www.postgresql.org" target="_blank" rel="noreferrer"> <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/postgresql/postgresql-original-wordmark.svg" alt="postgresql" width="20" height="20"/> </a>
- **Database migration tool:** [Flyway](https://flywaydb.org/) <img height="20" width="20" src="https://upload.vectorlogo.zone/logos/flywaydb/images/b336d129-8bbb-48b4-bed0-55ddd690cef4.svg" />
- **JSON library:** 
  - [Jackson](https://github.com/FasterXML/jackson)
  - [GSON](https://github.com/google/gson)
- **Generate test data:** [Java Faker](https://github.com/DiUS/java-faker)
- **Java API for Microsoft Excel:** [Apache POI](https://poi.apache.org/components/spreadsheet/)
- **Annotation processor:**
  - [Lombok](https://projectlombok.org/)
  - [MapStruct](https://mapstruct.org/)
- **API documentation generation:**
  - [Springdoc-openapi](https://springdoc.org/) <img height="20" width="20" src="https://www.vectorlogo.zone/logos/openapis/openapis-icon.svg"/>
  - [Swagger UI](https://swagger.io/tools/swagger-ui/) <img height="20" width="20" src="https://raw.githubusercontent.com/AliasIO/wappalyzer/master/src/drivers/webextension/images/icons/Swagger%20UI.svg"/>
- **Code generation:**
  - [OpenAPI Generator](https://openapi-generator.tech) <img height="20" width="20" src="https://openapi-generator.tech/img/color-logo.svg"/>
  - [Swagger Codegen](https://github.com/swagger-api/swagger-codegen) <img height="20" width="20" src="https://raw.githubusercontent.com/AliasIO/wappalyzer/master/src/drivers/webextension/images/icons/Swagger%20UI.svg"/>
- **Template Engine:** [Thymeleaf](https://www.thymeleaf.org/) <img height="20" width="20" src="https://unpkg.com/simple-icons@v6/icons/thymeleaf.svg"/>
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

## OpenAPI generated server

Automatically generated by the [OpenAPI Generator](https://openapi-generator.tech) project
by using the [OpenAPI-Spec](https://openapis.org) and the [openapi-generator-maven-plugin](https://github.com/OpenAPITools/openapi-generator/tree/master/modules/openapi-generator-maven-plugin/README.md).

An OpenAPI document that conforms to the OpenAPI Specification [openapi.yaml](rest-app-openapi/src/main/resources/openapi.yaml)
has been generated automatically by using [springdoc-openapi-maven-plugin](https://github.com/springdoc/springdoc-openapi-maven-plugin) 
in the module [rest-app](rest-app).

*Refer to the module [rest-app-openapi](rest-app-openapi/README.md) for more information.*

## Swagger generated client 
<img src="https://img.shields.io/badge/Swagger-85EA2D?style=for-the-badge&logo=Swagger&logoColor=black"/>

Automatically generated by the [Swagger Codegen](https://github.com/swagger-api/swagger-codegen) by using the
[swagger-codegen-maven-plugin](https://github.com/swagger-api/swagger-codegen/blob/master/modules/swagger-codegen-maven-plugin/README.md).

An OpenAPI document that conforms to the OpenAPI Specification [openapi.yaml](rest-app-openapi/src/main/resources/openapi.yaml)
has been generated automatically by using [springdoc-openapi-maven-plugin](https://github.com/springdoc/springdoc-openapi-maven-plugin)
in the module [rest-app](rest-app).

*Refer to the module [service-swagger-client](service-swagger-client/README.md) for more information.*


## Web clients configure

The web application has three web client implementations for making HTTP calls to 
REST application services - RestTemplate, a new WebClient alternative and automatically generated by the 
[Swagger Codegen](https://github.com/swagger-api/swagger-codegen) client API - 
[ApiClient](service-swagger-client/README.md).
Setup web-app in application.properties.

Using **RestTemplate** (is deprecated since Spring 5):
```bash
app.httpClient = RestTemplate
```
Using **WebClient** (exists since Spring 5):
```bash
app.httpClient = WebClient
```
Using **ApiClient** ([OkHttpClient](https://square.github.io/okhttp/) and [GSON](https://github.com/google/gson) based):
```bash
app.httpClient = ApiClient
```

**ApiClient** is a preferable choice.

_<sub>Note: this Web application has endpoints for using create and import Excel files with 
the 'ApiClient' property only.</a>_

## Run local tests

Embedded H2 in memory.

In the root directory of the project:
```bash
$ java -jar -Dspring.profiles.active=dev rest-app/target/rest-app-1.0-SNAPSHOT.jar
```
To start the OpenAPI generated server:
```bash
$ java -jar -Dspring.profiles.active=dev rest-app-openapi/target/rest-app-openapi-1.0-SNAPSHOT.jar
```
The rest application will be accessible at [http://localhost:8088](http://localhost:8088).
```bash
$ java -jar web-app/target/web-app-1.0-SNAPSHOT.jar 
```
The web application will be accessible at [http://localhost:8080](http://localhost:8080).

## Run application with PostgreSQL
<img src="https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white"/>

PostgreSQL require(can be customized in application-postgres.properties file in prod-db module):
* `driver` - org.postgresql.Driver
* `url` - jdbc:postgresql://localhost:5432/setlistOrganizer
* `user` - postgres
* `password` - password
  
Run rest-app with PostgreSQL:
```bash
$ java -jar -Dspring.profiles.active=postgres rest-app/target/rest-app-1.0-SNAPSHOT.jar
```

## Run with docker-compose
<img src="https://img.shields.io/badge/Docker-2CA5E0?style=for-the-badge&logo=docker&logoColor=white"/>

In the root directory of the project, run the rest-app with monitoring and web-app in one go:
```bash
$ sudo docker-compose up --build
```
The web application will be accessible at [http://localhost:8080](http://localhost:8080)

The rest application will be accessible at [http://localhost:8088](http://localhost:8088)

Micrometer by default shows jvm metrics at [http://localhost:8088/actuator/prometheus](http://localhost:8088/actuator/prometheus)

Access the Prometheus webUI on  [http://localhost:9090](http://localhost:9090)

Access the Grafana webUI on  [http://localhost:3000](http://localhost:3000)

To stop the containers:
```bash
$ sudo docker-compose down
```

## Local tests with Postman
<img src="https://img.shields.io/badge/Postman-FF6C37?style=for-the-badge&logo=Postman&logoColor=white"/>

To test the REST API, you can use the Postman collection to access the API endpoints:

[postman_collection.json](./documentation/Setlist_organizer_rest_app.postman_collection.json)

See the Postman [online documentation](https://learning.postman.com/docs/getting-started/installation-and-updates/).

## Documenting a REST API
<img src="https://img.shields.io/badge/openapi-%236BA539.svg?&style=for-the-badge&logo=openapiinitiative&logoColor=black" />

Using OpenAPI 3.0

The OpenAPI descriptions in JSON format will be available at the path: 
[http://localhost:8088/v3/api-docs](http://localhost:8088/v3/api-docs)

The OpenAPI descriptions in YAML format will be available at the path:
[http://localhost:8088/v3/api-docs.yaml](http://localhost:8088/v3/api-docs.yaml)

API documentation with Swagger UI: 
[http://localhost:8088/swagger-ui/index.html](http://localhost:8088/swagger-ui/index.html)


## Excel Import and Export
<img src="https://img.shields.io/badge/Microsoft_Excel-217346?style=for-the-badge&logo=microsoft-excel&logoColor=white"/>

These REST applications have several endpoints (`/export/excel` and `/import/excel`) to create and import Excel files using 
[Apache POI](https://poi.apache.org/components/spreadsheet/).

*Please, after running the REST application, refer to the API documentation with [Swagger UI](http://localhost:8088/swagger-ui/index.html) for more information.*

Following are samples of Excel files that can be imported:

[Band.xlsx](./documentation/Band.xlsx)

[Track.xlsx](./documentation/Track.xlsx)


