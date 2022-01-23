package com.epam.brest.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * Band for model.
 * There is a no args constructor, getters and setters for fields, override equals, hashcode and toString methods.
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(name="Band", description = "Band")
public class Band {

    private Integer bandId;

    @Schema(name = "Name", description = "name of the band")
    @NotEmpty(message = "Please provide band name!")
    @Size(max=100, message = "Band name size have to be <= {max} symbols!")
    private String bandName;

    @Schema(name = "Details", description = "details about the band")
    @Size(max=1000, message = "Band details size have to be <= {max} symbols!")
    private String bandDetails;

    public Band(String bandName) {
        this.bandName = bandName;
    }

    public Band(String bandName, String bandDetails) {
        this.bandName = bandName;
        this.bandDetails = bandDetails;
    }

}
