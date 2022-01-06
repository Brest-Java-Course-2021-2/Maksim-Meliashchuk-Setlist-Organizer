package com.epam.brest.rest;

import com.epam.brest.model.dto.TrackDto;
import com.epam.brest.service.TrackDtoService;
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

    @GetMapping(value = "/tracks_dto")
    public final Collection<TrackDto> findAllTracksWithBandName() {
        logger.debug("findAllTracksWithBandName()");
        return trackDtoService.findAllTracksWithBandName();
    }

    @GetMapping(value = "/repertoire/filter/band/{bandId}")
    public final Collection<TrackDto> findAllTracksWithBandNameByBandId(@PathVariable Integer bandId) {
        logger.debug("findAllTracksWithBandNameByBandId()");
        return trackDtoService.findAllTracksWithBandNameByBandId(bandId);
    }

    @GetMapping(value = "/repertoire/filter")
    public final Collection<TrackDto> findAllTracksWithReleaseDateFilter(@RequestParam(value = "fromDate", required = false)
                                                @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fromDate,
                                                @RequestParam(value = "toDate", required = false)
                                                @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate toDate) {
        logger.debug("filterTrackByReleaseDate({},{})", fromDate, toDate);
        return trackDtoService.findAllTracksWithReleaseDateFilter(fromDate, toDate);
    }
}
