package com.epam.brest.web_app;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * MVC controller.
 */
@Controller
public class BandController {

    private final Logger logger = LogManager.getLogger(BandController.class);

    @GetMapping(value = "/bands")
    public String bands(Model model) {
        logger.debug("go to page bands");
        return "bands";
    }

    @GetMapping(value = "/band/add")
    public String band(Model model) {
        logger.debug("go to page add band");
        return "band";
    }

}