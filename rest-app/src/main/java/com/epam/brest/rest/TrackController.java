package com.epam.brest.rest;

import com.epam.brest.model.Track;
import com.epam.brest.model.dto.TrackDto;
import com.epam.brest.service.TrackService;
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
@Tag(name = "Track", description = "the Track API")
@RestController
@CrossOrigin
public class TrackController {
    private final TrackService trackService;

    private final Logger logger = LogManager.getLogger(TrackController.class);

    public TrackController(TrackService trackService) {
        this.trackService = trackService;
    }

    @Operation(summary = "Get information for all tracks based on their IDs")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "A set of tracks",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TrackDto.class)) })
    })
    @GetMapping(value = "/repertoire")
    public final Collection<Track> tracks() {
        logger.debug("tracks()");
        return trackService.findAllTracks();
    }

    @Operation(summary = "Get information for a single track identified by its unique ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "A track",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Track.class)) }),
            @ApiResponse(responseCode = "404", description = "Trying to get a non-existent track",
                    content = @Content)})
    @GetMapping(value = "/repertoire/{id}")
    public final Track getTrackById(@PathVariable Integer id) {
        logger.debug("getTrackById()");
        return trackService.getTrackById(id);
    }

    @Operation(summary = "Create a new track")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Track have been created. Returns the ID of the new track",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Integer.class)) }),
            @ApiResponse(responseCode = "400", description = "An attempt to create track with invalid fields",
                    content = @Content)})
    @PostMapping(path = "/repertoire", consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<Integer> createTrack(@Valid @RequestBody Track track) {
        logger.debug("createTrack({})", track);
        Integer id = trackService.create(track);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @Operation(summary = "Update a track")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Track(s) have been updated. Returns the number of tracks affected",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Integer.class)) }),
            @ApiResponse(responseCode = "400", description = "Trying to update track with invalid fields",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Trying to update a non-existent track",
                    content = @Content) })
    @PutMapping(value = "/repertoire", consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<Integer> updateTrack(@Valid @RequestBody Track track) {
        logger.debug("updateTrack({})", track);
        int result = trackService.update(track);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Operation(summary = "Delete a track")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Track(s) have been removed. Returns the number of tracks affected",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Integer.class)) }),
            @ApiResponse(responseCode = "404", description = "Trying to delete a non-existent track",
                    content = @Content)})
    @DeleteMapping(value = "/repertoire/{id}", produces = {"application/json"})
    public ResponseEntity<Integer> deleteBand(@PathVariable Integer id) {
        logger.debug("delete({})", id);
        int result =  trackService.delete(id);
        return new ResponseEntity(result, HttpStatus.OK);
    }

}
