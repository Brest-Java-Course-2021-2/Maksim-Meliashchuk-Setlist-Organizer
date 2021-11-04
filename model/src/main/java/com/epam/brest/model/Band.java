package com.epam.brest.model;

public class Band {

    private Integer bandId;

    private String bandName;

    private String bandDetails;

    private boolean bandActivity;

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

    public boolean isBandActivity() {
        return bandActivity;
    }

    public void setBandActivity(boolean bandActivity) {
        this.bandActivity = bandActivity;
    }
}
