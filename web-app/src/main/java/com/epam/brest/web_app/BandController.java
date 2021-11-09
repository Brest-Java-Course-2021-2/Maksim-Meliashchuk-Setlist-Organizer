package com.epam.brest.web_app;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * MVC controller.
 */
@Controller
public class BandController {

    @GetMapping(value = "/bands")
    public String bands(Model model) {
        return "bands";
    }

    @GetMapping(value = "/band/add")
    public String band(Model model) {
        return "band";
    }


}