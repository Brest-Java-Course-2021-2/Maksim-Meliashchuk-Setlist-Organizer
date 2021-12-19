package com.epam.brest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource({"classpath:sql-track.properties"})
@PropertySource({"classpath:sql-band.properties"})
public class RestApplication extends SpringBootServletInitializer {

    private static final Logger logger = LogManager.getLogger(RestApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(RestApplication.class, args);
    }

}