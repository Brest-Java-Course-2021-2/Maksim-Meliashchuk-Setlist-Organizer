package com.epam.brest.web_app;

import com.epam.brest.model.Band;
import com.epam.brest.model.Track;
import com.epam.brest.service.BandService;
import com.epam.brest.service.TrackDtoService;
import com.epam.brest.service.TrackService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;

/**
 * MVC controller.
 */
@Controller
public class TrackController {

    private final TrackService trackService;

    private final BandService bandService;

    private final TrackDtoService trackDtoService;

    private final Logger logger = LogManager.getLogger(TrackController.class);

    public TrackController(TrackService trackService, BandService bandService, TrackDtoService trackDtoService) {
        this.trackService = trackService;
        this.bandService = bandService;
        this.trackDtoService = trackDtoService;
    }

    @GetMapping(value = "/repertoire")
    public String repertoire(Model model) {
        logger.debug("Go to page repertoire");
        model.addAttribute("tracks", trackDtoService.findAllTracksWithBandName());
        return "repertoire";
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
    public String addTrack(Track track) {
        logger.debug("addTrack({})", track);
        this.trackService.create(track);
        return "redirect:/repertoire";
    }

    @PostMapping(value = "/track/{id}")
    public String updateBand(Track track) {
        logger.debug("updateTrack({}, {})", track);
        this.trackService.update(track);
        return "redirect:/repertoire";
    }

    @GetMapping(value = "/track/{id}/delete")
    public final String deleteTrackById(@PathVariable Integer id, Model model) {
        logger.debug("delete({},{})", id, model);
        trackService.delete(id);
        return "redirect:/repertoire";
    }

}
