package com.epam.brest.rest;

import com.epam.brest.model.dto.TrackDto;
import com.epam.brest.service.TrackDtoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collection;

/**
 * REST controller.
 */
@RestController
@CrossOrigin
public class TrackDtoController {

    private static final Logger logger = LogManager.getLogger(TrackDtoController.class);

    private final TrackDtoService trackDtoService;

    public TrackDtoController(TrackDtoService trackDtoService) {
        this.trackDtoService = trackDtoService;
    }

    @Operation(summary = "Get information for all tracks with their band names")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "A set of tracks",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TrackDto.class)) })
    })
    @GetMapping(value = "/tracks_dto")
    public final Collection<TrackDto> findAllTracksWithBandName() {
        logger.debug("findAllTracksWithBandName()");
        return trackDtoService.findAllTracksWithBandName();
    }

    @Operation(summary = "Get information about band's tracks")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "A set of tracks",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TrackDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid band id supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Band not found",
                    content = @Content) })
    @GetMapping(value = "/repertoire/filter/band/{bandId}")
    public final Collection<TrackDto> findAllTracksWithBandNameByBandId(@PathVariable Integer bandId) {
        logger.debug("findAllTracksWithBandNameByBandId()");
        return trackDtoService.findAllTracksWithBandNameByBandId(bandId);
    }

    @Operation(summary = "Get information for tracks with their release dates between {fromDate} and {toDate}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "A set of tracks",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TrackDto.class)) })
    })
    @GetMapping(value = "/repertoire/filter")
    public final Collection<TrackDto> findAllTracksWithReleaseDateFilter(@RequestParam(value = "fromDate", required = false)
                                                @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fromDate,
                                                @RequestParam(value = "toDate", required = false)
                                                @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate toDate) {
        logger.debug("filterTrackByReleaseDate({},{})", fromDate, toDate);
        return trackDtoService.findAllTracksWithReleaseDateFilter(fromDate, toDate);
    }
}
