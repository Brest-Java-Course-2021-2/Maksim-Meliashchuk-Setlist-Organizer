package com.epam.brest.web_app;

import com.epam.brest.service.BandService;
import com.epam.brest.service.TrackService;
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

    private final TrackService trackService;

    private final BandService bandService;

    private final Logger logger = LogManager.getLogger(TrackController.class);

    public TrackController(TrackService trackService, BandService bandService) {
        this.trackService = trackService;
        this.bandService = bandService;
    }


    @GetMapping(value = "/repertoire")
    public String repertoire(Model model) {
        logger.debug("Go to page repertoire");
        model.addAttribute("tracks", trackService.findAllTracks());
        return "repertoire";
    }

    @GetMapping(value = "/repertoire/add")
    public String track(Model model) {
        logger.debug("Go to page add track");
        return "track";
    }

}
