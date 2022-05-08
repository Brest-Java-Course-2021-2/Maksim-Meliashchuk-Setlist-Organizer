package com.epam.brest.service;

import com.epam.brest.model.BandDto;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface BandDtoService {

    /**
     * Get list of band Dto.
     *
     * @return list of band Dto.
     */
    @PreAuthorize("hasAnyRole('user', 'admin')")
    List<BandDto> findAllWithCountTrack();

}
