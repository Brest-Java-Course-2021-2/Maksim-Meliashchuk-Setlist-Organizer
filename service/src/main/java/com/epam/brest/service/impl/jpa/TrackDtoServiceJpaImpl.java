package com.epam.brest.service.impl.jpa;

import com.epam.brest.model.TrackDto;
import com.epam.brest.service.TrackDtoService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Profile("jpa")
public class TrackDtoServiceJpaImpl implements TrackDtoService {
    @Override
    public List<TrackDto> findAllTracksWithBandName() {
        return null;
    }

    @Override
    public List<TrackDto> findAllTracksWithBandNameByBandId(Integer bandId) {
        return null;
    }

    @Override
    public List<TrackDto> findAllTracksWithReleaseDateFilter(LocalDate fromDate, LocalDate toDate) {
        return null;
    }
}
