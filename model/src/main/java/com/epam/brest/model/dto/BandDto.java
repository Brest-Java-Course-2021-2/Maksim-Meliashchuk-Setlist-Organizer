package com.epam.brest.model.dto;

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
     * Average track count of the Band.
     */
    private Integer countTrack;

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

    /**
     *Returns <code>Integer</code> representation of track count
     * for the Band.
     * @return countTrack track count
     */

    public Integer getCountTrack() {
        return countTrack;
    }

    /**
     * Sets the band's average track count.
     * @param countTrack track count
     */

    public void setCountTrack(Integer countTrack) {
        this.countTrack = countTrack;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "BandDto{"
                + "bandId=" + bandId
                + ", bandName='" + bandName + '\''
                + ", countTrack=" + countTrack
                + '}';
    }
}
