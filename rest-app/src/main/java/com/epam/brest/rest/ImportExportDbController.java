package com.epam.brest.rest;

import com.epam.brest.service.export_import_db.DataBaseZipRestoreService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "Import/Export database", description = "create database archive / restore database from archive")
@SecurityRequirement(name = "keycloakAuth")
public class ImportExportDbController {

    private final DataBaseZipRestoreService dataBaseZipRestoreService;

    @Operation(summary = "Exporting the database as XML and stored as ZIP archive")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully export the database as ZIP archive",
                    content = {@Content(mediaType = "application/zip",
                            schema = @Schema(implementation = MultipartFile.class, format = "binary"))}),
    })
    @GetMapping("/downloadZipFile")
    public void downloadZipFile(HttpServletResponse response) throws IOException {
        log.debug("downloadZipFile()");
        response.setContentType("application/zip");
        response.setHeader("Content-Disposition", "attachment; filename=download.zip");
        dataBaseZipRestoreService.exportData(response);
    }

    @Operation(summary = "Restore database from ZIP archive")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Database have been restored.",
                    content = {@Content(mediaType = "application/zip")})})
    @PostMapping(value = "/uploadZipFile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void uploadingZipFile(@RequestParam(value = "file") final MultipartFile files) throws IOException, SAXException {
        log.debug("uploadingZipFile ({})", files.getName());
        dataBaseZipRestoreService.importData(files);
    }

}
