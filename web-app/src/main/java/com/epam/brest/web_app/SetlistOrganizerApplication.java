package com.epam.brest.web_app;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SetlistOrganizerApplication {

    private static final Logger logger = LogManager.getLogger(SetlistOrganizerApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(SetlistOrganizerApplication.class, args);
    }
}

