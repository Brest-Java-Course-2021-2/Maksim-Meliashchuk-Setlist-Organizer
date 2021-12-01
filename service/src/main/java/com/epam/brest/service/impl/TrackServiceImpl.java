package com.epam.brest.service.impl;

import com.epam.brest.dao.TrackDao;
import com.epam.brest.model.Track;
import com.epam.brest.service.TrackService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TrackServiceImpl implements TrackService {

    private final Logger logger = LogManager.getLogger(TrackServiceImpl.class);

    private final TrackDao trackDao;

    public TrackServiceImpl(TrackDao trackDao) {
        this.trackDao = trackDao;
    }

    @Override
    public Track getTrackById(Integer trackId) {
        logger.debug("Get track by id = {}", trackId);
        return this.trackDao.getTrackById(trackId);
    }

    @Override
    public Integer create(Track track) {
        logger.debug("Track create({})", track);
        return this.trackDao.create(track);
    }

    @Override
    public Integer update(Track track) {
        logger.debug("Track update({})", track);
        return this.trackDao.update(track);
    }

    @Override
    public Integer delete(Integer trackId) {
        logger.debug("Track delete({})", trackId);
        logger.debug("Delete track with id = {}", trackId);
        return this.trackDao.delete(trackId);
    }

    @Override
    @Transactional(readOnly = true)
    public Integer count() {
        logger.debug("TrackService count()");
        return this.trackDao.count();
    }
}
