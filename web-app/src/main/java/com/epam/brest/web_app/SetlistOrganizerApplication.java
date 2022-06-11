package com.epam.brest.web_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.EnableFeignClients;

import java.io.IOException;

@SpringBootApplication
@RefreshScope
@EnableFeignClients
public class SetlistOrganizerApplication {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(SetlistOrganizerApplication.class, args);
    }
}



