package com.epam.brest.service.impl.jdbc;

import com.epam.brest.dao.TrackDtoDao;
import com.epam.brest.model.TrackDto;
import com.epam.brest.service.TrackDtoService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
@Profile({"jdbc"})
public class TrackDtoServiceImpl implements TrackDtoService {

    private final Logger logger = LogManager.getLogger(TrackDtoServiceImpl.class);

    private final TrackDtoDao trackDtoDao;

    public TrackDtoServiceImpl(TrackDtoDao trackDtoDao) {
        this.trackDtoDao = trackDtoDao;
    }

    @Override
    public List<TrackDto> findAllTracksWithBandName() {
        logger.debug("findAllTracksWithBandName()");
        return trackDtoDao.findAllTracksWithBandName();
    }

    @Override
    public List<TrackDto> findAllTracksWithBandNameByBandId(Integer bandId) {
        logger.debug("findAllTracksWithBandNameByBandId({bandId})", bandId);
        return trackDtoDao.findAllTracksWithBandNameByBandId(bandId);
    }

    @Override
    public List<TrackDto> findAllTracksWithReleaseDateFilter(LocalDate fromDate, LocalDate toDate) {
        logger.debug("findAllTracksWithReleaseDateFilter({fromDate},{toDate})", fromDate, toDate);
        return trackDtoDao.findAllTracksWithReleaseDateFilter(fromDate, toDate);
    }
}
