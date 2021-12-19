package com.epam.brest.service.impl;

import com.epam.brest.dao.TrackDao;
import com.epam.brest.model.Track;
import com.epam.brest.service.TrackService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TrackServiceImpl implements TrackService {

    private final Logger logger = LogManager.getLogger(TrackServiceImpl.class);

    private TrackDao trackDao;


    public TrackServiceImpl(TrackDao trackDao) {
        this.trackDao = trackDao;
    }

    @Override
    @Transactional(readOnly = true)
    public Track getTrackById(Integer trackId) {
        logger.debug("Get track by id = {}", trackId);
        return this.trackDao.getTrackById(trackId);
    }

    @Override
    @Transactional
    public Integer create(Track track) {
        logger.debug("Track create({})", track);
        return this.trackDao.create(track);
    }

    @Override
    @Transactional
    public Integer update(Track track) {
        logger.debug("Track update({})", track);
        return this.trackDao.update(track);
    }

    @Override
    @Transactional
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

    @Override
    @Transactional(readOnly = true)
    public List<Track> findAllTracks() {
        logger.debug("TrackService findAllTracks()");
        return this.trackDao.findAll();
    }
}
