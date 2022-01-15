package com.epam.brest.model.dto;

import java.time.LocalDate;

public class TrackDto {

    private Integer trackId;

    private String trackName;

    private String trackBandName;

    private Integer trackTempo;

    private Integer trackDuration;

    private String trackDetails;

    private String trackLink;

    private LocalDate trackReleaseDate;

    public TrackDto() {
    }

    public TrackDto(String trackName) {
        this.trackName = trackName;
    }

    public Integer getTrackId() {
        return trackId;
    }

    public void setTrackId(Integer trackId) {
        this.trackId = trackId;
    }

    public String getTrackName() {
        return trackName;
    }

    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }

    public String getTrackBandName() {
        return trackBandName;
    }

    public void setTrackBandName(String trackBandId) {
        this.trackBandName = trackBandId;
    }

    public Integer getTrackTempo() {
        return trackTempo;
    }

    public void setTrackTempo(Integer trackTempo) {
        this.trackTempo = trackTempo;
    }

    public Integer getTrackDuration() {
        return trackDuration;
    }

    public void setTrackDuration(Integer trackDuration) {
        this.trackDuration = trackDuration;
    }

    public String getTrackDetails() {
        return trackDetails;
    }

    public void setTrackDetails(String trackDetails) {
        this.trackDetails = trackDetails;
    }

    public String getTrackLink() {
        return trackLink;
    }

    public void setTrackLink(String trackLink) {
        this.trackLink = trackLink;
    }

    public LocalDate getTrackReleaseDate() {
        return trackReleaseDate;
    }

    public void setTrackReleaseDate(LocalDate trackReleaseDate) {
        this.trackReleaseDate = trackReleaseDate;
    }

    @Override
    public String toString() {
        return "Track{" +
                "trackId=" + trackId +
                ", trackName='" + trackName + '\'' +
                ", trackBandName='" + trackBandName + '\'' +
                ", trackTempo='" + trackTempo + '\'' +
                ", trackDuration='" + trackDuration + '\'' +
                ", trackDetails='" + trackDetails + '\'' +
                ", trackLink='" + trackLink + '\'' +
                ", trackReleaseDate='" + trackReleaseDate + '\'' +
                '}';
    }
}
