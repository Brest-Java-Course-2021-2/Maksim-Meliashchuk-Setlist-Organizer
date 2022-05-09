package com.epam.brest.rest;

import com.epam.brest.model.Band;
import com.epam.brest.service.BandService;
import com.epam.brest.service.excel.BandExportExcelService;
import com.epam.brest.service.excel.BandImportExcelService;
import com.epam.brest.service.faker.BandFakerService;
import com.epam.brest.service.xml.BandXmlService;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Collection;

/**
 * REST controller.
 */
@Tag(name = "Band", description = "the Band API")
@RestController
@CrossOrigin
@SecurityRequirement(name = "keycloakAuth")
public class BandController {

    private final Logger logger = LogManager.getLogger(BandController.class);

    private final BandService bandService;
    private final BandExportExcelService bandExportExcelService;
    private final BandFakerService bandFakerService;
    private final BandImportExcelService bandImportExcelService;
    private final BandXmlService bandXmlService;

    public BandController(BandService bandService, BandExportExcelService bandExportExcelService,
                          BandFakerService bandFakerService, BandImportExcelService bandImportExcelService,
                          BandXmlService bandXmlService) {
        this.bandService = bandService;
        this.bandExportExcelService = bandExportExcelService;
        this.bandFakerService = bandFakerService;
        this.bandImportExcelService = bandImportExcelService;
        this.bandXmlService = bandXmlService;
    }

    @Operation(summary = "Get information for all bands based on their IDs")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "A set of bands",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Band.class))) })
    })
    @GetMapping(value = "/bands")
    public final Collection<Band> bands() {
        logger.debug("bands()");
        return bandService.findAllBands();
    }

    @Operation(summary = "Get information for fake bands based on their IDs")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "A set of fake bands",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Band.class))) })
    })
    @GetMapping(value = "/bands/fill")
    public final Collection<Band> bandsFake(@RequestParam(defaultValue = "1", value = "size", required = false)
                                                Integer size,
                                            @RequestParam(defaultValue = "EN", value = "language", required = false)
                                                String language) {
        logger.debug("fillFakeBands()");
        return bandFakerService.fillFakeBands(size, "language");
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
        return new ResponseEntity<>(result, HttpStatus.OK);
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
        logger.debug("delete({})", id);
        int result =  bandService.delete(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Operation(summary = "Export information for all bands based on their IDs to Excel")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully export to Excel",
                    content = { @Content(mediaType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
                            schema = @Schema(implementation = MultipartFile.class, format = "binary")) }),
    })
    @GetMapping(value = "/bands/export/excel")
    public final void exportToExcelAllBands(HttpServletResponse response) {
        logger.debug("exportToExcelAllBands()");
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=Bands.xlsx";
        response.setHeader(headerKey, headerValue);
        bandExportExcelService.exportBandsExcel(response);
    }

    @Operation(summary = "Import information in the table 'Band' from Excel")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Band(s) have been imported. Returns the number of bands imported.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Integer.class)) })})
    @PostMapping(value = "/bands/import/excel", consumes = MediaType.MULTIPART_FORM_DATA_VALUE )
    public ResponseEntity<Integer> importBandFromExcel(@RequestParam(value ="file") final MultipartFile files) throws IOException {
        logger.debug("importBandFromExcel({})", files.getName());
        int result =  bandImportExcelService.importBandsExcel(files).size();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Operation(summary = "Export information for all bands based on their IDs to XML")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully export to XML",
                    content = { @Content(mediaType = "application/xml",
                            schema = @Schema(implementation = MultipartFile.class, format = "binary")) }),
    })
    @GetMapping(value = "/bands/export/xml")
    public final void exportToXmlAllBands(HttpServletResponse response) throws IOException {
        logger.debug("exportToXmlAllBands()");
        response.setContentType("application/xml");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=Bands.xml";
        response.setHeader(headerKey, headerValue);
        bandXmlService.exportBandsXml(response);
    }
}