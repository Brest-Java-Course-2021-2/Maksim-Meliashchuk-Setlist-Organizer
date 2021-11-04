package com.epam.brest.model;

import java.time.LocalDate;

public class Track {

    private Integer trackId;

    private String trackName;

    private Integer bandId;

    private Integer trackTempo;

    private Integer trackDuration;

    private String trackDetails;

    private String trackLink;

    private LocalDate releaseDate;

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

    public Integer getBandId() {
        return bandId;
    }

    public void setBandId(Integer bandId) {
        this.bandId = bandId;
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

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }
}
