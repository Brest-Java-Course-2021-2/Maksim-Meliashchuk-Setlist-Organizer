package com.epam.brest.service;

import com.epam.brest.model.BandDto;

import java.util.List;

public interface BandDtoService {

    /**
     * Get list of band Dto.
     *
     * @return list of band Dto.
     */

    List<BandDto> findAllWithCountTrack();

}
