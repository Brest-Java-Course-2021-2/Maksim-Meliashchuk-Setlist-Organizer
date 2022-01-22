package com.epam.brest.rest;

import com.epam.brest.model.Band;
import com.epam.brest.model.Track;
import com.epam.brest.model.dto.TrackDto;
import com.epam.brest.service.BandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Band", description = "the Band API")
@RestController
@CrossOrigin
public class BandController {

    private final BandService bandService;

    private final Logger logger = LogManager.getLogger(BandController.class);

    public BandController(BandService bandService) {
        this.bandService = bandService;
    }

    @Operation(summary = "Get information for all bands based on their IDs")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "A set of bands",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Band.class)) })
    })
    @GetMapping(value = "/bands")
    public final Collection<Band> bands() {
        logger.debug("bands()");
        return bandService.findAllBands();
    }

    @Operation(summary = "Get information for a single band identified by its unique ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "A band",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Band.class)) }),
            @ApiResponse(responseCode = "404", description = "Trying to get a non-existent band",
                    content = @Content)})
    @GetMapping(value = "/bands/{id}")
    public final Band getBandById(@PathVariable Integer id) {
        logger.debug("getBandById()");
        return bandService.getBandById(id);
    }

    @Operation(summary = "Create a new band")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Band have been created. Returns the ID of the new band",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Integer.class)) }),
            @ApiResponse(responseCode = "400", description = "An attempt to create band with invalid fields",
                    content = @Content),
            @ApiResponse(responseCode = "422", description = "An attempt to create a non-unique band",
                    content = @Content)})
    @PostMapping(path = "/bands", consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<Integer> createBand(@Valid @RequestBody Band band) {
        logger.debug("createBand({})", band);
        Integer id = bandService.create(band);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @Operation(summary = "Update a band")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Band(s) have been updated. Returns the number of bands affected",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Integer.class)) }),
            @ApiResponse(responseCode = "400", description = "An attempt to create band with invalid fields",
                    content = @Content),
            @ApiResponse(responseCode = "422", description = "An attempt to create a non-unique band",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Trying to update a non-existent band",
                    content = @Content) })
    @PutMapping(value = "/bands", consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<Integer> updateBand(@Valid @RequestBody Band band) {
        logger.debug("updateBand({})", band);
        int result = bandService.update(band);
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @Operation(summary = "Delete a band")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Band(s) have been removed. Returns the number of bands affected",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Integer.class)) }),
            @ApiResponse(responseCode = "404", description = "Trying to delete a non-existent band",
                    content = @Content),
            @ApiResponse(responseCode = "422", description = "Attempting to delete a band with linked tracks",
                    content = @Content)})
    @DeleteMapping(value = "/bands/{id}", produces = {"application/json"})
    public ResponseEntity<Integer> deleteBand(@PathVariable Integer id) {
        logger.debug("delete({},{})", id);
        int result =  bandService.delete(id);
        return new ResponseEntity(result, HttpStatus.OK);
    }


}