package com.epam.brest.service.impl;

import com.epam.brest.dao.TrackDtoDao;
import com.epam.brest.model.dto.TrackDto;
import com.epam.brest.service.TrackDtoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TrackDtoServiceImpl implements TrackDtoService {

    private final TrackDtoDao trackDtoDao;

    public TrackDtoServiceImpl(TrackDtoDao trackDtoDao) {
        this.trackDtoDao = trackDtoDao;
    }

    @Override
    public List<TrackDto> findAllTracksWithBandName() {
        return trackDtoDao.findAllTracksWithBandName();
    }
}
