package com.epam.brest.service.impl;

import com.epam.brest.dao.TrackDtoDao;
import com.epam.brest.model.TrackDto;
import com.epam.brest.service.TrackDtoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class TrackDtoServiceImpl implements TrackDtoService {

    private TrackDtoDao trackDtoDao;

    public TrackDtoServiceImpl(TrackDtoDao trackDtoDao) {
        this.trackDtoDao = trackDtoDao;
    }

    @Override
    public List<TrackDto> findAllTracksWithBandName() {
        return trackDtoDao.findAllTracksWithBandName();
    }

    @Override
    public List<TrackDto> findAllTracksWithBandNameByBandId(Integer bandId) {
        return trackDtoDao.findAllTracksWithBandNameByBandId(bandId);
    }

    @Override
    public List<TrackDto> findAllTracksWithReleaseDateFilter(LocalDate fromDate, LocalDate toDate) {
        return trackDtoDao.findAllTracksWithReleaseDateFilter(fromDate, toDate);
    }
}
