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
public class TrackController {
    private final Logger logger = LogManager.getLogger(TrackController.class);

    @GetMapping(value = "/repertoire")
    public String repertoire(Model model) {
        logger.debug("go to page repertoire");
        return "repertoire";
    }

    @GetMapping(value = "/repertoire/add")
    public String track(Model model) {
        logger.debug("go to page add track");
        return "track";
    }

}
