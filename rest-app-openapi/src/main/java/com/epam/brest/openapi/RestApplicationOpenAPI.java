package com.epam.brest.openapi;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@ComponentScan(basePackages = "com.epam.brest")
@PropertySource({"classpath:sql-track.properties"})
@PropertySource({"classpath:sql-band.properties"})
@OpenAPIDefinition(info = @Info(
        title = "Setlist Organizer API",
        version = "1.0.0",
        description = "'Setlist Organizer' is a web application for organizing repertoires of musical bands.",
        license = @License(
                url = "http://www.apache.org/licenses/LICENSE-2.0",
                name = "Apache 2.0"
        )),
        externalDocs = @ExternalDocumentation(
                description = "Setlist Organizer github",
                url = "https://github.com/Brest-Java-Course-2021-2/Maksim-Meliashchuk-Setlist-Organizer"
))
public class RestApplicationOpenAPI {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestApplicationOpenAPI.class);

    public static void main(String[] args) {
        SpringApplication.run (RestApplicationOpenAPI.class, args);
    }

}
