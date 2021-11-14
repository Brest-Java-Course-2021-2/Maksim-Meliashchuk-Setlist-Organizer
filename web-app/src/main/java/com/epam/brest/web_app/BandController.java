package com.epam.brest.web_app;

import com.epam.brest.service.BandDtoService;
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

    private final BandDtoService bandDtoService;

    private final Logger logger = LogManager.getLogger(BandController.class);

    public BandController(BandDtoService bandDtoService) {
        this.bandDtoService = bandDtoService;
    }

    @GetMapping(value = "/bands")
    public String bands(Model model) {
        logger.debug("go to page bands");
        model.addAttribute("bands", bandDtoService.findAllWithCountTrack());
        return "bands";
    }

    @GetMapping(value = "/band/add")
    public String band(Model model) {
        logger.debug("go to page add band");
        return "band";
    }

}