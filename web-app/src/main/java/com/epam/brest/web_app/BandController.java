package com.epam.brest.web_app;

import com.epam.brest.model.Band;
import com.epam.brest.service.BandDtoService;
import com.epam.brest.service.BandService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * MVC controller.
 */
@Controller
public class BandController {

    private final BandDtoService bandDtoService;

    private final BandService bandService;

    private final Logger logger = LogManager.getLogger(BandController.class);

    public BandController(BandDtoService bandDtoService, BandService bandService) {
        this.bandDtoService = bandDtoService;
        this.bandService = bandService;
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

    @PostMapping(value = "/band")
    public String addDepartment(Band band) {

        logger.debug("addBand({}, {})", band);
        this.bandService.create(band);
        return "redirect:/bands";
    }
}