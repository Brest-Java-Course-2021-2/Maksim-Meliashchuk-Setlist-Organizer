package com.epam.brest.rest;

import com.epam.brest.dao.jdbc.BandDtoDaoJdbcImpl;
import com.epam.brest.model.BandDto;
import com.epam.brest.service.BandDtoService;
import com.epam.brest.service.excel.BandDtoExportExcelService;
import com.epam.brest.service.faker.BandDtoFakerService;
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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

@Tag(name = "Bands", description = "the Bands API")
@RestController
@CrossOrigin
@SecurityRequirement(name = "keycloakAuth")
public class BandDtoController {

    private static final Logger logger = LogManager.getLogger(BandDtoDaoJdbcImpl.class);

    private final BandDtoService bandDtoService;
    private final BandDtoExportExcelService bandDtoExportExcelService;
    private final BandDtoFakerService bandDtoFakerService;

    public BandDtoController(BandDtoService bandDtoService, BandDtoExportExcelService bandDtoExportExcelService,
                             BandDtoFakerService bandDtoFakerService) {
        this.bandDtoService = bandDtoService;
        this.bandDtoExportExcelService = bandDtoExportExcelService;
        this.bandDtoFakerService = bandDtoFakerService;
    }

    @Operation(summary = "Get information for all bands with their repertoire duration and track count")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "A set of bands",
                    content = { @Content(mediaType = "application/json",
                           array = @ArraySchema(schema = @Schema(implementation = BandDto.class))) })
    })
    @GetMapping(value = "/bands_dto")
    public final Collection<BandDto> bandsDto() {
        logger.debug("bandDto()");
        return bandDtoService.findAllWithCountTrack();
    }

    @Operation(summary = "Filled fake bands with their repertoire duration and number of tracks")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "A set of fake bands",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = BandDto.class))) })
    })
    @GetMapping(value = "/bands_dto/fill")
    public final Collection<BandDto> fillBandsDtoFake(@RequestParam(defaultValue = "1", value = "size", required = false)
                                                    Integer size,
                                                      @RequestParam(defaultValue = "EN", value = "language", required = false)
                                                    String language) {
        logger.debug("bandsDtoFake()");
        return bandDtoFakerService.fillFakeBandsDto(size, language);
    }

    @Operation(summary = "Export information for all bands with their repertoire duration and track count to Excel")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully export to Excel",
                    content = { @Content(mediaType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
                            schema = @Schema(implementation = MultipartFile.class, format = "binary")) }),
    })
    @GetMapping(value = "bands_dto/export/excel")
    public final void exportToExcelAllBandsDto(HttpServletResponse response) {
        logger.debug("exportToExcelAllBands()");
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=BandsDto.xlsx";
        response.setHeader(headerKey, headerValue);
        bandDtoExportExcelService.exportBandsDtoExcel(response);
    }
}