package com.epam.brest.rest;

import com.epam.brest.model.Track;
import com.epam.brest.service.TrackService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

/**
 * REST controller.
 */
@RestController
@CrossOrigin
public class TrackController {
    private final TrackService trackService;

    private final Logger logger = LogManager.getLogger(TrackController.class);

    public TrackController(TrackService trackService) {
        this.trackService = trackService;
    }

    @GetMapping(value = "/repertoire")
    public final Collection<Track> tracks() {
        logger.debug("tracks()");
        return trackService.findAllTracks();
    }

    @GetMapping(value = "/repertoire/{id}")
    public final Track getTrackById(@PathVariable Integer id) {
        logger.debug("getTrackById()");
        return trackService.getTrackById(id);
    }

    @PostMapping(path = "/repertoire", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Integer> createTrack(@Valid @RequestBody Track track) {
        logger.debug("createTrack({})", track);
        Integer id = trackService.create(track);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @PutMapping(value = "/repertoire", consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<Integer> updateTrack(@Valid @RequestBody Track track) {
        logger.debug("updateTrack({})", track);
        int result = trackService.update(track);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping(value = "/repertoire/{id}", produces = {"application/json"})
    public ResponseEntity<Integer> deleteBand(@PathVariable Integer id) {
        logger.debug("delete({})", id);
        int result =  trackService.delete(id);
        return new ResponseEntity(result, HttpStatus.OK);
    }

}
