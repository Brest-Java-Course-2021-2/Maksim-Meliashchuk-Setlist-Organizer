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

    public void setBandId(Integer bandId) {
        this.bandId = bandId;
    }

    public String getBandName() {
        return bandName;
    }

    public void setBandName(String bandName) {
        this.bandName = bandName;
    }

    public String getBandDetails() {
        return bandDetails;
    }

    public void setBandDetails(String bandDetails) {
        this.bandDetails = bandDetails;
    }

    @Override
    public String toString() {
        return "Department{" +
                "departmentId=" + bandId +
                ", departmentName='" + bandName + '\'' +
                '}';
    }
}
