package com.epam.brest.service;

import com.epam.brest.model.dto.TrackDto;

import java.util.List;

public interface TrackDtoService {

    List<TrackDto> findAllTracksWithBandName();

}
