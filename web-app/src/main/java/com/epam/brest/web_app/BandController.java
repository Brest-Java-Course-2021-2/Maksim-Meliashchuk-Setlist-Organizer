package com.epam.brest.web_app;

import com.epam.brest.model.Band;
import com.epam.brest.service.BandDtoService;
import com.epam.brest.service.BandService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping(value = "/band")
    public String gotoAddBandPage(Model model) {
        logger.debug("gotoAddBandPage({})", model);
        model.addAttribute("isNew", true);
        model.addAttribute("band", new Band());
        return "band";
    }

    @GetMapping(value = "/band/{id}")
    public final String gotoEditBandPage(@PathVariable Integer id, Model model) {
        logger.debug("gotoEditBandPage(id:{},model:{})", id, model);
        model.addAttribute("isNew", false);
        model.addAttribute("band", bandService.getBandById(id));
        return "band";
    }

    @PostMapping(value = "/band")
    public String addBand(Band band) {
        logger.debug("addBand({})", band);
        this.bandService.create(band);
        return "redirect:/bands";
    }

    @PostMapping(value = "/band/{id}")
    public String updateBand(Band band) {
        logger.debug("updateBand({})", band);
        this.bandService.update(band);
        return "redirect:/bands";
    }

    @GetMapping(value = "/band/{id}/delete")
    public final String deleteBandById(@PathVariable Integer id, Model model) {

        logger.debug("delete({},{})", id, model);
        bandService.delete(id);
        return "redirect:/bands";
    }


}