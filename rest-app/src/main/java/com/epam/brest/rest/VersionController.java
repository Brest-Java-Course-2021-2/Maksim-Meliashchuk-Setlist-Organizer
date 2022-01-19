package com.epam.brest.rest;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class VersionController {
    private final static String VERSION = "1.0.0";

    @GetMapping(value = "/version")
    public String version() {
        return VERSION;
    }

}