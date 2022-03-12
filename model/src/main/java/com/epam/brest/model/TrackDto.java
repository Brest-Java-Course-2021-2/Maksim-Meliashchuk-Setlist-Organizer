package com.epam.brest.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * POJO Track for model.
 * There is a no args constructor, getters and setters for fields, override equals, hashcode and toString methods.
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name="TrackDto", description = "Track with the band name")
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
