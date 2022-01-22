package com.epam.brest.model;

import io.swagger.v3.oas.annotations.media.Schema;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;

public class Track {

    private Integer trackId;

    @Schema(name = "Name", description = "name of track", example = "New song")
    @NotEmpty(message = "Please provide track name!")
    @Size(max=100, message = "Track name size have to be <= {max} symbols!")
    private String trackName;

    @Positive(message = "Band id should be positive")
    private Integer trackBandId;

    @Schema(name = "Tempo", description = "track tempo, value in bpm", example = "120")
    @Positive(message = "Track tempo cannot be less than zero!")
    private Integer trackTempo;

    @Schema(name = "Duration", description = "duration of the track play in ms")
    @Min(value = 0, message = "Track duration cannot be less than zero!")
    private Integer trackDuration;

    @Schema(name = "Details", description = "details about the track")
    @Size(max=2000, message = "Track details size have to be <= {max} symbols!")
    private String trackDetails;

    @Schema(name = "Link", description = "the link to the track if it is posted on the web",
            example = "https://www.youtube.com/watch?v=Mp6W0IzLlW8&ab_channel=TheMuse")
    @Size(max=255, message = "Track link size have to be <= {max} symbols!")
    @URL(message = "Track link is not valid. The link must contain http or https!")
    private String trackLink;

    @Schema(name = "Details", description = "track release date", example = "2020-12-01")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate trackReleaseDate;

    public Track() {
    }

    public Track(String trackName) {
        this.trackName = trackName;
    }

    public Integer getTrackId() {
        return trackId;
    }

    public Track setTrackId(Integer trackId) {
        this.trackId = trackId;
        return this;
    }

    public String getTrackName() {
        return trackName;
    }

    public Track setTrackName(String trackName) {
        this.trackName = trackName;
        return this;
    }

    public Integer getTrackBandId() {
        return trackBandId;
    }

    public Track setTrackBandId(Integer trackBandId) {
        this.trackBandId = trackBandId;
        return this;
    }

    public Integer getTrackTempo() {
        return trackTempo;
    }

    public Track setTrackTempo(Integer trackTempo) {
        this.trackTempo = trackTempo;
        return this;
    }

    public Integer getTrackDuration() {
        return trackDuration;
    }

    public Track setTrackDuration(Integer trackDuration) {
        this.trackDuration = trackDuration;
        return this;
    }

    public String getTrackDetails() {
        return trackDetails;
    }

    public Track setTrackDetails(String trackDetails) {
        this.trackDetails = trackDetails;
        return this;
    }

    public String getTrackLink() {
        return trackLink;
    }

    public Track setTrackLink(String trackLink) {
        this.trackLink = trackLink;
        return this;
    }

    public LocalDate getTrackReleaseDate() {
        return trackReleaseDate;
    }

    public Track setTrackReleaseDate(LocalDate trackReleaseDate) {
        this.trackReleaseDate = trackReleaseDate;
        return this;
    }

    @Override
    public String toString() {
        return "Track{" +
                "trackId=" + trackId +
                ", trackName='" + trackName + '\'' +
                ", trackBandId='" + trackBandId + '\'' +
                ", trackTempo='" + trackTempo + '\'' +
                ", trackDuration='" + trackDuration + '\'' +
                ", trackDetails='" + trackDetails + '\'' +
                ", trackLink='" + trackLink + '\'' +
                ", trackReleaseDate='" + trackReleaseDate + '\'' +
                '}';
    }
}
