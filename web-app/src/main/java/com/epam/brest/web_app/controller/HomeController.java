package com.epam.brest.web_app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Root controller.
 */
@Controller
public class HomeController {

    @GetMapping(value = "/")
    public String defaultPageRedirect() {
        return "redirect:bands";
    }
}
