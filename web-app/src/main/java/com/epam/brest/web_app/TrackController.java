package com.epam.brest.web_app;

import com.epam.brest.model.Track;
import com.epam.brest.service.BandService;
import com.epam.brest.service.TrackDtoService;
import com.epam.brest.service.TrackService;
import com.epam.brest.web_app.validator.TrackValidator;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

/**
 * MVC controller.
 */
@Controller
public class TrackController {

    private final TrackService trackService;

    private final BandService bandService;

    private final TrackDtoService trackDtoService;

    private final TrackValidator trackValidator;

    private final Logger logger = LogManager.getLogger(TrackController.class);

    public TrackController(TrackService trackService, BandService bandService, TrackDtoService trackDtoService,
                           TrackValidator trackValidator) {
        this.trackService = trackService;
        this.bandService = bandService;
        this.trackDtoService = trackDtoService;
        this.trackValidator = trackValidator;
    }

    @GetMapping(value = "/track")
    public String gotoAddTrackPage(Model model) {
        logger.debug("gotoAddTrackPage({})", model);
        model.addAttribute("isNew", true);
        model.addAttribute("track", new Track());
        model.addAttribute("bands", bandService.findAllBands());
        model.addAttribute("localDate", LocalDate.now());
        return "track";
    }

    @GetMapping(value = "/track/{id}")
    public final String gotoEditBandPage(@PathVariable Integer id, Model model) {
        logger.debug("gotoEditTrackPage(id:{},model:{})", id, model);
        model.addAttribute("isNew", false);
        model.addAttribute("track", trackService.getTrackById(id));
        model.addAttribute("bands", bandService.findAllBands());
        return "track";
    }

    @PostMapping(value = "/track")
    public String addTrack(Track track, BindingResult result) {
        logger.debug("addTrack({})", track);
        trackValidator.validate(track, result);
        this.trackService.create(track);
        return "redirect:/repertoire";
    }

    @PostMapping(value = "/track/{id}")
    public String updateBand(Track track, BindingResult result) {
        logger.debug("updateTrack({}, {})", track);
        trackValidator.validate(track, result);
        if (result.hasErrors()) {
            return "track";
        }
        this.trackService.update(track);
        return "redirect:/repertoire";
    }

    @GetMapping(value = "/track/{id}/delete")
    public final String deleteTrackById(@PathVariable Integer id, Model model) {
        logger.debug("delete({},{})", id, model);
        trackService.delete(id);
        return "redirect:/repertoire";
    }

   @GetMapping(value = "/repertoire")
    public final String filterTrackByReleaseDate(@RequestParam(value = "fromDate", required = false)
                                                 @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fromDate,
                                                 @RequestParam(value = "toDate", required = false)
                                                 @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate toDate,
                                                 Model model) {
        logger.debug("filterTrackByReleaseDate({},{})", fromDate, toDate);
        model.addAttribute("tracks", trackDtoService.findAllTracksWithReleaseDateFilter(fromDate, toDate));
        model.addAttribute("fromDate", fromDate);
        model.addAttribute("toDate", toDate);
        return "repertoire";
    }

}
