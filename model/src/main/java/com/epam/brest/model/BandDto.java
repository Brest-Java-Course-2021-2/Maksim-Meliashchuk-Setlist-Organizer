package com.epam.brest.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * POJO Band for model.
 * There is a no args constructor, getters and setters for fields, override equals, hashcode and toString methods.
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name="BandDto", description = "Band with the count of tracks and the duration of the repertoire")
public class BandDto {

    private Integer bandId;

    private String bandName;

    private Integer bandCountTrack;

    private Integer bandRepertoireDuration;

    private String bandDetails;

}
