package com.epam.brest.service;

import com.epam.brest.model.TrackDto;
import org.springframework.security.access.prepost.PreAuthorize;

import java.time.LocalDate;
import java.util.List;

public interface TrackDtoService {
    @PreAuthorize("hasAnyRole('user', 'admin')")
    List<TrackDto> findAllTracksWithBandName();

    @PreAuthorize("hasAnyRole('user', 'admin')")
    List<TrackDto> findAllTracksWithBandNameByBandId(Integer bandId);

    @PreAuthorize("hasAnyRole('user', 'admin')")
    List<TrackDto> findAllTracksWithReleaseDateFilter(LocalDate fromDate, LocalDate toDate);

}
