package com.epam.brest.rest;

import com.epam.brest.model.TrackDto;
import com.epam.brest.service.TrackDtoService;
import com.epam.brest.service.excel.TrackDtoExportExcelService;
import com.epam.brest.service.faker.TrackDtoFakerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Collection;

/**
 * REST controller.
 */
@Tag(name = "Tracks", description = "the Tracks API")
@RestController
@CrossOrigin
public class TrackDtoController {

    private static final Logger logger = LogManager.getLogger(TrackDtoController.class);

    private final TrackDtoService trackDtoService;

    private final TrackDtoFakerService trackDtoFakerService;

    private final TrackDtoExportExcelService trackDtoExportExcelService;

    public TrackDtoController(TrackDtoService trackDtoService, TrackDtoFakerService trackDtoFakerService,
                              TrackDtoExportExcelService trackDtoExportExcelService) {
        this.trackDtoService = trackDtoService;
        this.trackDtoFakerService = trackDtoFakerService;
        this.trackDtoExportExcelService = trackDtoExportExcelService;
    }

    @Operation(summary = "Get information for all tracks with their band names")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "A set of tracks",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = TrackDto.class))) })
    })
    @GetMapping(value = "/tracks_dto")
    public final Collection<TrackDto> findAllTracksWithBandName() {
        logger.debug("findAllTracksWithBandName()");
        return trackDtoService.findAllTracksWithBandName();
    }

    @Operation(summary = "Filled fake tracks with their band names")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "A set of fake tracks",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = TrackDto.class))) })
    })
    @GetMapping(value = "/tracks_dto/fill")
    public final Collection<TrackDto> fillTracksDtoFake(@RequestParam(defaultValue = "1", value = "size", required = false)
                                                                    Integer size,
                                                        @RequestParam(defaultValue = "EN", value = "language", required = false)
                                                                    String language) {
        logger.debug("findAllTracksWithBandName()");
        return trackDtoFakerService.fillFakeTracksDto(size, language);
    }

    @Operation(summary = "Get information about band's tracks")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "A set of tracks",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = TrackDto.class))) }),
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
                            array = @ArraySchema(schema = @Schema(implementation = TrackDto.class))) })
    })
    @GetMapping(value = "/repertoire/filter")
    public final Collection<TrackDto> findAllTracksWithReleaseDateFilter(@RequestParam(value = "fromDate", required = false)
                                                @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fromDate,
                                                @RequestParam(value = "toDate", required = false)
                                                @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate toDate) {
        logger.debug("filterTrackByReleaseDate({},{})", fromDate, toDate);
        return trackDtoService.findAllTracksWithReleaseDateFilter(fromDate, toDate);
    }

    @Operation(summary = "Export information for all tracks with their band names to Excel")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully export to Excel",
                    content = { @Content(mediaType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
                            schema = @Schema(implementation = MultipartFile.class, format = "binary"))})
    })
    @GetMapping(value = "tracks_dto/export/excel")
    public final void exportToExcelAllTracksWithBandName(HttpServletResponse response) throws IOException {
        logger.debug("exportToExcelAllTracksWithBandName()");
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=TracksDto.xlsx";
        response.setHeader(headerKey, headerValue);
        trackDtoExportExcelService.exportTracksDtoExcel(response);
    }
}
