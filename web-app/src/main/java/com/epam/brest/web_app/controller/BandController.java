package com.epam.brest.web_app.controller;

import com.epam.brest.model.Band;
import com.epam.brest.service.faker.BandDtoFakerService;
import com.epam.brest.service.BandDtoService;
import com.epam.brest.service.BandService;
import com.epam.brest.web_app.validator.BandValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * MVC controller.
 */
@Controller
@ConditionalOnExpression(
        "'${app.httpClient}'=='RestTemplate' or '${app.httpClient}'=='WebClient'"
)
public class BandController {

    private final BandDtoService bandDtoService;

    private final BandService bandService;

    private final BandDtoFakerService bandDtoFakerService;

    private final BandValidator bandValidator;

    private final Logger logger = LogManager.getLogger(BandController.class);

    public BandController(BandDtoService bandDtoService, BandService bandService, BandDtoFakerService bandDtoFakerService, BandValidator bandValidator) {
        this.bandDtoService = bandDtoService;
        this.bandService = bandService;
        this.bandDtoFakerService = bandDtoFakerService;
        this.bandValidator = bandValidator;
    }

    @GetMapping(value = "/bands")
    public String bands(Model model) {
        logger.debug("go to page bands");
        model.addAttribute("bands", bandDtoService.findAllWithCountTrack());
        return "bands";
    }

    @GetMapping(value = "/bands/fill")
    public String fillFakeBands(@RequestParam(value = "size", required = false)
                                            Integer size,
                                @RequestParam(value = "language", required = false)
                                            String language,
                                Model model) {
        logger.debug("go to page bands");
        model.addAttribute("bands", bandDtoFakerService.fillFakeBandsDto(size, language));
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
    public String addBand(Band band, BindingResult result) {
        logger.debug("addBand({})", band);
        bandValidator.validate(band, result);
        if (result.hasErrors()) {
            return "band";
        }
        this.bandService.create(band);
        return "redirect:/bands";
    }

    @PostMapping(value = "/band/{id}")
    public String updateBand(Band band, BindingResult result) {
        logger.debug("updateBand({})", band);
        bandValidator.validate(band, result);
        if (result.hasErrors()) {
            return "band";
        }
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