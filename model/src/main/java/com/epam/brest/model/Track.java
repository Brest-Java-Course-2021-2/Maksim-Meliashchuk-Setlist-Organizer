package com.epam.brest.model;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class Track {

    private Integer trackId;

    private String trackName;

    private Integer trackBandId;

    private Integer trackTempo;

    private Integer trackDuration;

    private String trackDetails;

    private String trackLink;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
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
