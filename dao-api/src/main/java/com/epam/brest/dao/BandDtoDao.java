package com.epam.brest.dao;

import com.epam.brest.model.BandDto;

import java.util.List;

/**
 * BandDto DAO Interface.
 */
public interface BandDtoDao {

    /**
     * Get all bands with track count by band.
     *
     * @return bands list.
     */
    List<BandDto> findAllWithCountTrack();

}