package com.epam.brest.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;

/**
 * POJO Track for model.
 * There is a no args constructor, getters and setters for fields, override equals, hashcode and toString methods.
 */

@Data
@Schema(name="TrackDto", description = "TrackDto")
public class TrackDto {

    private Integer trackId;

    private String trackName;

    private String trackBandName;

    private Integer trackBandId;

    private Integer trackTempo;

    private Integer trackDuration;

    private String trackDetails;

    private String trackLink;

    private LocalDate trackReleaseDate;

}