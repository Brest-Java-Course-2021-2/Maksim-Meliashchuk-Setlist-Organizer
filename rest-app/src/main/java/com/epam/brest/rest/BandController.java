package com.epam.brest.rest;

import com.epam.brest.model.Band;
import com.epam.brest.service.BandService;
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
public class BandController {

    private final BandService bandService;

    private final Logger logger = LogManager.getLogger(BandController.class);

    public BandController(BandService bandService) {
        this.bandService = bandService;
    }

    @GetMapping(value = "/bands")
    public final Collection<Band> bands() {
        logger.debug("bands()");
        return bandService.findAllBands();
    }

    @GetMapping(value = "/bands/{id}")
    public final Band getBandById(@PathVariable Integer id) {
        logger.debug("getBandById()");
        return bandService.getBandById(id);
    }

    @PostMapping(path = "/bands", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Integer> createBand(@Valid @RequestBody Band band) {
        logger.debug("createBand({})", band);
        Integer id = bandService.create(band);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @PutMapping(value = "/bands", consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<Integer> updateBand(@Valid @RequestBody Band band) {
        logger.debug("updateBand({})", band);
        int result = bandService.update(band);
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @DeleteMapping(value = "/bands/{id}", produces = {"application/json"})
    public ResponseEntity<Integer> deleteBand(@PathVariable Integer id) {
        logger.debug("delete({},{})", id);
        int result =  bandService.delete(id);
        return new ResponseEntity(result, HttpStatus.OK);
    }


}