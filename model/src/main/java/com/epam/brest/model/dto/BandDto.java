package com.epam.brest.model.dto;

import lombok.Data;

/**
 * POJO Band for model.
 * There is a no args constructor, getters and setters for fields, override equals, hashcode and toString methods.
 */

@Data
public class BandDto {

    private Integer bandId;

    private String bandName;

    private Integer bandCountTrack;

    private Integer bandRepertoireDuration;

    private String bandDetails;

}
