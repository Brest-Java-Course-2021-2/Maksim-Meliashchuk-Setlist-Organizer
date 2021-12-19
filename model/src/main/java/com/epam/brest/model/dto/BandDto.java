package com.epam.brest.model.dto;

import java.util.Objects;

/**
 * POJO Band for model.
 */

public class BandDto {

    /**
     * Band Id.
     */
    private Integer bandId;

    /**
     * Band Name.
     */
    private String bandName;

    /**
     * Track count of the Band.
     */
    private Integer bandCountTrack;

    /**
     * Duration of the repertoire of the Band.
     */
    private Integer bandRepertoireDuration;

    /**
     * Details of the Band.
     */
    private String bandDetails;

    /**
     * Constructor without arguments.
     */
    public BandDto() {
    }

    /**
     * Constructor with band name.
     *
     * @param bandName band name
     */
    public BandDto(String bandName) {
        this.bandName = bandName;
    }

    /**
     * Returns <code>Integer</code> representation of this bandId.
     *
     * @return bandId Band Id.
     */
    public Integer getBandId() {
        return bandId;
    }

    /**
     * Sets the band's identifier.
     *
     * @param bandId Band Id.
     */
    public void setBandId(final Integer bandId) {
        this.bandId = bandId;
    }

    /**
     * Returns <code>String</code> representation of this bandName.
     *
     * @return bandName Band Name.
     */
    public String getBandName() {
        return bandName;
    }

    /**
     * Sets the band's name.
     *
     * @param bandName Band Name.
     */
    public void setBandName(final String bandName) {
        this.bandName = bandName;
    }

    public String getBandDetails() {
        return bandDetails;
    }

    public void setBandDetails(String bandDetails) {
        this.bandDetails = bandDetails;
    }

    /**
     *Returns <code>Integer</code> representation of track count
     * for the Band.
     * @return countTrack track count
     */


    public Integer getBandCountTrack() {
        return bandCountTrack;
    }

    /**
     * Sets the band's track count.
     * @param bandCountTrack track count
     */

    public void setBandCountTrack(Integer bandCountTrack) {
        this.bandCountTrack = bandCountTrack;
    }

    /**
     *Returns <code>Integer</code> duration of the repertoire of the band
     * for the Band.
     * @return bandRepertoireDuration
     */


    public Integer getBandRepertoireDuration() {
        return bandRepertoireDuration;
    }

    /**
     *Sets <code>Integer</code> duration of the repertoire of the band
     * for the Band.
     * @param  bandRepertoireDuration
     */

    public void setBandRepertoireDuration(Integer bandRepertoireDuration) {
        this.bandRepertoireDuration = bandRepertoireDuration;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "BandDto{"
                + "bandId=" + bandId
                + ", bandName='" + bandName + '\''
                + ", bandDetails='" + bandDetails + '\''
                + ", bandCountTrack=" + bandCountTrack
                + ", bandRepertoireDuration=" + bandRepertoireDuration
                + '}';
    }

}
