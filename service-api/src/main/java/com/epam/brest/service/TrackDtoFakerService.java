package com.epam.brest.service;

import com.epam.brest.model.TrackDto;

import java.util.List;

public interface TrackDtoFakerService {

    List<TrackDto> fillFakeTracksDto(Integer size, String language);

}
