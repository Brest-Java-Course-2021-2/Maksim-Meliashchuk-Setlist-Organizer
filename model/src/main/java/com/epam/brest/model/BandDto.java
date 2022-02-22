package com.epam.brest.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * POJO Band for model.
 * There is a no args constructor, getters and setters for fields, override equals, hashcode and toString methods.
 */

@Data
@Schema(name="BandDto", description = "BandDto")
public class BandDto {

    private Integer bandId;

    private String bandName;

    private Integer bandCountTrack;

    private Integer bandRepertoireDuration;

    private String bandDetails;

}
