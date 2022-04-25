package com.epam.brest.service.impl.jdbc;

import com.epam.brest.dao.TrackDao;
import com.epam.brest.model.Track;
import com.epam.brest.service.TrackService;
import com.epam.brest.service.exception.TrackNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@Profile({"jdbc"})
public class TrackServiceImpl implements TrackService {

    private final Logger logger = LogManager.getLogger(TrackServiceImpl.class);

    private final TrackDao trackDao;


    public TrackServiceImpl(TrackDao trackDao) {
        this.trackDao = trackDao;
    }

    @Override
    @Transactional(readOnly = true)
    public Track getTrackById(Integer trackId) {
        logger.debug("Get track by id = {}", trackId);
        try {
            return this.trackDao.getTrackById(trackId);
        } catch (EmptyResultDataAccessException e) {
            throw new TrackNotFoundException(trackId);
        }
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
        try {
            trackDao.getTrackById(track.getTrackId());
            return this.trackDao.update(track);
        } catch (EmptyResultDataAccessException e) {
            throw new TrackNotFoundException(track.getTrackId());
        }
    }

    @Override
    @Transactional
    public Integer delete(Integer trackId) {
        logger.debug("Delete track with id = {}", trackId);
        try {
            trackDao.getTrackById(trackId);
            return this.trackDao.delete(trackId);
        } catch (EmptyResultDataAccessException e) {
            throw new TrackNotFoundException(trackId);
        }
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

    @Override
    public void deleteAllTracks() {
        logger.debug("deleteAllTracks()");
        trackDao.deleteAllTracks();
    }
}
