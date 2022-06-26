package com.epam.brest.rest;

import com.epam.brest.model.Track;
import com.epam.brest.model.kafka.EventType;
import com.epam.brest.model.kafka.RepertoireEvent;
import com.epam.brest.service.TrackService;
import com.epam.brest.service.excel.TrackExportExcelService;
import com.epam.brest.service.excel.TrackImportExcelService;
import com.epam.brest.service.faker.TrackFakerService;
import com.epam.brest.service.kafka.ProducerService;
import com.epam.brest.service.xml.TrackXmlService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * REST controller.
 */
@Tag(name = "Track", description = "the Track API")
@RestController
@CrossOrigin
@SecurityRequirement(name = "keycloakOAuth")
public class TrackController {

    @Value("${kafka.topics.repertoire-changed}")
    private String repertoireChangedTopic;
    private final TrackService trackService;
    private final TrackFakerService trackFakerService;
    private final TrackExportExcelService trackExportExcelService;
    private final TrackImportExcelService trackImportExcelService;
    private final TrackXmlService trackXmlService;
    private final ProducerService producerService;

    private final Logger logger = LogManager.getLogger(TrackController.class);

    public TrackController(TrackService trackService, TrackFakerService trackFakerService,
                           TrackExportExcelService trackExportExcelService, TrackImportExcelService trackImportExcelService,
                           TrackXmlService trackXmlService, ProducerService producerService) {
        this.trackService = trackService;
        this.trackFakerService = trackFakerService;
        this.trackExportExcelService = trackExportExcelService;
        this.trackImportExcelService = trackImportExcelService;
        this.trackXmlService = trackXmlService;
        this.producerService = producerService;
    }

    @Operation(summary = "Get information for all tracks based on their IDs")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "A set of tracks",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Track.class)))})
    })
    @GetMapping(value = "/repertoire")
    public final Collection<Track> tracks() {
        logger.debug("tracks()");
        List<Track> trackList = trackService.findAllTracks();
        trackList.forEach(track ->
                track.add(linkTo(methodOn(TrackController.class).getTrackById(track.getTrackId())).withSelfRel(),
                        linkTo(methodOn(TrackController.class).createTrack(track)).withRel("createTrack"),
                        linkTo(methodOn(TrackController.class).updateTrack(track)).withRel("updateTrack"),
                        linkTo(methodOn(TrackController.class).deleteTrack(track.getTrackId())).withRel("deleteTrack")));
        return trackList;
    }

    @Operation(summary = "Fill information for fake tracks based on their IDs")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "A set of fake tracks",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Track.class)))})
    })
    @GetMapping(value = "/repertoire/fill")
    public final Collection<Track> tracksFake(@RequestParam(defaultValue = "1", value = "size", required = false)
                                              Integer size,
                                              @RequestParam(defaultValue = "EN", value = "language", required = false)
                                              String language) {
        logger.debug("tracksFake()");
        return trackFakerService.fillFakeTracks(size, language);
    }

    @Operation(summary = "Get information for a single track identified by its unique ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "A track",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Track.class))}),
            @ApiResponse(responseCode = "404", description = "Trying to get a non-existent track",
                    content = @Content)})
    @GetMapping(value = "/repertoire/{id}")
    public ResponseEntity<Track> getTrackById(@PathVariable Integer id) {
        logger.debug("getTrackById()");
        Track track = trackService.getTrackById(id);
        track.add(linkTo(methodOn(TrackController.class).getTrackById(track.getTrackId())).withSelfRel(),
                linkTo(methodOn(TrackController.class).createTrack(track)).withRel("createTrack"),
                linkTo(methodOn(TrackController.class).updateTrack(track)).withRel("updateTrack"),
                linkTo(methodOn(TrackController.class).deleteTrack(track.getTrackId())).withRel("deleteTrack"));
        return ResponseEntity.ok(track);
    }

    @Operation(summary = "Create a new track")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Track have been created. Returns the ID of the new track",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Integer.class))}),
            @ApiResponse(responseCode = "400", description = "An attempt to create track with invalid fields",
                    content = @Content)})
    @PostMapping(path = "/repertoire", consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<Integer> createTrack(@Valid @RequestBody Track track) {
        logger.debug("createTrack({})", track);
        Integer id = trackService.create(track);
        producerService.sendRepertoireMessage(repertoireChangedTopic, new RepertoireEvent(EventType.CREATE_TRACK, track));
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @Operation(summary = "Update a track")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Track(s) have been updated. Returns the number of tracks affected",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Integer.class))}),
            @ApiResponse(responseCode = "400", description = "Trying to update track with invalid fields",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Trying to update a non-existent track",
                    content = @Content)})
    @PutMapping(value = "/repertoire", consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<Integer> updateTrack(@Valid @RequestBody Track track) {
        logger.debug("updateTrack({})", track);
        int result = trackService.update(track);
        producerService.sendRepertoireMessage(repertoireChangedTopic, new RepertoireEvent(EventType.UPDATE_TRACK, track));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Operation(summary = "Delete a track")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Track(s) have been removed. Returns the number of tracks affected",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Integer.class))}),
            @ApiResponse(responseCode = "404", description = "Trying to delete a non-existent track",
                    content = @Content)})
    @DeleteMapping(value = "/repertoire/{id}", produces = {"application/json"})
    public ResponseEntity<Integer> deleteTrack(@PathVariable Integer id) {
        logger.debug("delete({})", id);
        var track = trackService.getTrackById(id);
        int result = trackService.delete(id);
        producerService.sendRepertoireMessage(repertoireChangedTopic,
                new RepertoireEvent(EventType.DELETE_TRACK, track));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Operation(summary = "Export information for all tracks based on their IDs to Excel")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully export to Excel",
                    content = {@Content(mediaType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
                            schema = @Schema(implementation = MultipartFile.class, format = "binary"))})
    })
    @GetMapping(value = "/repertoire/export/excel")
    public final void exportToExcelAllTracks(HttpServletResponse response) {
        logger.debug("exportToExcelAllTracks()");
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=Tracks.xlsx";
        response.setHeader(headerKey, headerValue);
        trackExportExcelService.exportTracksExcel(response);
    }

    @Operation(summary = "Import information in the table 'Track' from Excel")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Track(s) have been imported. Returns the number of tracks imported.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Integer.class))})})
    @PostMapping(value = "/repertoire/import/excel", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Integer> importTrackFromExcel(@RequestParam(value = "file") final MultipartFile files) throws IOException {
        logger.debug("importTrackFromExcel({})", files.getName());
        int result = trackImportExcelService.importTrackExcel(files).size();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Operation(summary = "Export information for all tracks based on their IDs to XML")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully export to XML",
                    content = {@Content(mediaType = "application/xml",
                            schema = @Schema(implementation = MultipartFile.class, format = "binary"))}),
    })
    @GetMapping(value = "/repertoire/export/xml")
    public final void exportToXmlAlTracks(HttpServletResponse response) throws IOException {
        logger.debug("exportToXmlAlTracks()");
        response.setContentType("application/xml");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=Tracks.xml";
        response.setHeader(headerKey, headerValue);
        trackXmlService.exportTracksXml(response);
    }

}
