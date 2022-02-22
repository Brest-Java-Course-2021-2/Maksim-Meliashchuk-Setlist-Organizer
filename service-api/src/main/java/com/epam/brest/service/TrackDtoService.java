package com.epam.brest.service;

import com.epam.brest.model.TrackDto;

import java.time.LocalDate;
import java.util.List;

public interface TrackDtoService {

    List<TrackDto> findAllTracksWithBandName();

    List<TrackDto> findAllTracksWithBandNameByBandId(Integer bandId);

    List<TrackDto> findAllTracksWithReleaseDateFilter(LocalDate fromDate, LocalDate toDate);

}
