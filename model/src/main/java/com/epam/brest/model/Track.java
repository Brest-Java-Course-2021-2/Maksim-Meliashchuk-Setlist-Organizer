package com.epam.brest.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;

/**
 * Track entity.
 * There is a no args constructor, getters and setters for fields, override equals, hashcode and toString methods.
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Schema(name="Track", description = "Track")
public class Track {

    @Schema(name = "trackId", description = "ID of the track", example = "1")
    @JacksonXmlProperty(isAttribute = true)
    private Integer trackId;

    @NonNull
    @Schema(name = "trackName", description = "name of track", example = "New song")
    @NotEmpty(message = "Please provide track name!")
    @Size(max=100, message = "Track name size have to be <= {max} symbols!")
    private String trackName;

    @Schema(name = "trackBandId", description = "ID of the band", example = "1")
    @Positive(message = "Band id should be positive")
    private Integer trackBandId;

    @Schema(name = "trackTempo", description = "track tempo, value in bpm", example = "120")
    @Positive(message = "Track tempo cannot be less than zero!")
    private Integer trackTempo;

    @Schema(name = "trackDuration", description = "duration of the track play in ms")
    @Min(value = 0, message = "Track duration cannot be less than zero!")
    private Integer trackDuration;

    @Schema(name = "trackDetails", description = "details about the track")
    @Size(max=2000, message = "Track details size have to be <= {max} symbols!")
    private String trackDetails;

    @Schema(name = "trackLink", description = "the link to the track if it is posted on the web",
            example = "https://www.youtube.com/watch?v=Mp6W0IzLlW8&ab_channel=TheMuse")
    @Size(max=255, message = "Track link size have to be <= {max} symbols!")
    @URL(message = "Track link is not valid. The link must contain http or https!")
    private String trackLink;

    @Schema(name = "trackReleaseDate", description = "track release date", example = "2020-12-01")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate trackReleaseDate;

}
