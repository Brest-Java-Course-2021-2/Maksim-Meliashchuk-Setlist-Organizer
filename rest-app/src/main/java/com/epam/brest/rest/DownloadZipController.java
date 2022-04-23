package com.epam.brest.rest;

import com.epam.brest.service.zip.DownloadZipService;
import com.epam.brest.service.xml.BandExportXmlService;
import com.epam.brest.service.xml.TrackExportXmlService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
public class DownloadZipController {

    private final DownloadZipService downloadZipService;
    private final BandExportXmlService bandExportXmlService;
    private final TrackExportXmlService trackExportXmlService;

    @Operation(summary = "Exporting the database as XML and stored as ZIP archive")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully export the database as ZIP archive",
                    content = { @Content(mediaType = "application/zip",
                            schema = @Schema(implementation = MultipartFile.class, format = "binary")) }),
    })
    @GetMapping("/downloadZipFile")
    public void downloadZipFile(HttpServletResponse response) throws IOException {
        log.debug("downloadZipFile()");
        response.setContentType("application/zip");
        response.setHeader("Content-Disposition", "attachment; filename=download.zip");
        Map<String, String> listOfFiles = new HashMap<>();
        listOfFiles.put("Bands.xml", bandExportXmlService.exportBandsXmlAsString());
        listOfFiles.put("Tracks.xml", trackExportXmlService.exportTracksXmlAsString());
        downloadZipService.downloadZipFile(response, listOfFiles);
    }

}
