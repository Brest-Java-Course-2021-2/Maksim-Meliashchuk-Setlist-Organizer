package com.epam.brest.rest;

import com.epam.brest.model.dto.TrackDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Version", description = "the version Setlist Organizer API")
@RestController
@CrossOrigin
public class VersionController {
    private final static String VERSION = "1.0.0";

    @Operation(summary = "Get information for the API version")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "A version",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class)) })
    })
    @GetMapping(value = "/version")
    public String version() {
        return VERSION;
    }

}