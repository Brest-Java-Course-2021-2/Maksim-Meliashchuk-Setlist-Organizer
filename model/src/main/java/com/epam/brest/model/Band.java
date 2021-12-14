package com.epam.brest.model;

public class Band {

    private Integer bandId;

    private String bandName;

    private String bandDetails;

    public Band() {
    }

    public Band(String bandName) {
        this.bandName = bandName;
    }

    public Band(String bandName, String bandDetails) {
        this.bandName = bandName;
        this.bandDetails = bandDetails;
    }

    public Integer getBandId() {
        return bandId;
    }

    public Band setBandId(Integer bandId) {
        this.bandId = bandId;
        return this;
    }

    public String getBandName() {
        return bandName;
    }

    public Band setBandName(String bandName) {
        this.bandName = bandName;
        return this;
    }

    public String getBandDetails() {
        return bandDetails;
    }

    public Band setBandDetails(String bandDetails) {
        this.bandDetails = bandDetails;
        return this;
    }

    @Override
    public String toString() {
        return "Band{" +
                "bandId=" + bandId +
                ", bandName='" + bandName + '\'' +
                ", bandDetails='" + bandDetails + '\'' +
                '}';
    }
}
