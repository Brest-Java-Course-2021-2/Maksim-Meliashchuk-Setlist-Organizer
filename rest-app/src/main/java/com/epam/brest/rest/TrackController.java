package com.epam.brest.rest;

import com.epam.brest.model.Band;
import com.epam.brest.model.Track;
import com.epam.brest.service.BandService;
import com.epam.brest.service.TrackDtoService;
import com.epam.brest.service.TrackService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

/**
 * REST controller.
 */
@RestController
public class TrackController {
    private final TrackService trackService;

    private final Logger logger = LogManager.getLogger(TrackController.class);

    public TrackController(TrackService trackService) {
        this.trackService = trackService;
    }

    @PostMapping(path = "/repertoire", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Integer> createTrack(@RequestBody Track track) {
        logger.debug("createTrack({})", track);
        Integer id = trackService.create(track);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @PutMapping(value = "/repertoire", consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<Integer> updateTrack(@RequestBody Track track) {
        logger.debug("updateTrack({})", track);
        int result = trackService.update(track);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(value = "/repertoire/{id}")
    public final Track getTrackById(@PathVariable Integer id) {
        logger.debug("getTrackById()");
        return trackService.getTrackById(id);
    }

    //TODO INPLEMENTATION Metods

    @PostMapping(value = "/track")
    public String addTrack(Track track, BindingResult result) {
        logger.debug("addTrack({})", track);
        this.trackService.create(track);
        return "redirect:/repertoire";
    }

    @PostMapping(value = "/track/{id}")
    public String updateBand(Track track, BindingResult result) {
        logger.debug("updateTrack({}, {})", track);
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
        model.addAttribute("fromDate", fromDate);
        model.addAttribute("toDate", toDate);
        return "repertoire";
    }

}
