package com.epam.brest.service.impl.jdbc;

import com.epam.brest.dao.BandDao;
import com.epam.brest.model.Band;
import com.epam.brest.service.BandService;
import com.epam.brest.service.exception.BandNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Profile({"jdbc"})
public class BandServiceImpl implements BandService {

    private final Logger logger = LogManager.getLogger(BandServiceImpl.class);

    private final BandDao bandDao;

    public BandServiceImpl(BandDao bandDao) {
        this.bandDao = bandDao;
    }

    @Override
    @Transactional(readOnly = true)
    public Band getBandById(Integer bandId) {
        logger.debug("Get band by id = {}", bandId);
        try {
            return this.bandDao.getBandById(bandId);
        } catch (EmptyResultDataAccessException e) {
            throw new BandNotFoundException(bandId);
        }
    }

    @Override
    @Transactional
    public Integer create(Band band) {
        logger.debug("create({})", band);
        return this.bandDao.create(band);
    }

    @Override
    @Transactional
    public Integer update(Band band) {
        logger.debug("update({})", band);
        try {
            bandDao.getBandById(band.getBandId());
            return this.bandDao.update(band);
        } catch (EmptyResultDataAccessException e) {
            throw new BandNotFoundException(band.getBandId());
        }
    }

    @Override
    @Transactional
    public Integer delete(Integer bandId) {
        logger.debug("delete band with id = {}", bandId);
        try {
            bandDao.getBandById(bandId);
            return this.bandDao.delete(bandId);
        } catch (EmptyResultDataAccessException e) {
            throw new BandNotFoundException(bandId);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Integer count() {
        logger.debug("count()");
        return this.bandDao.count();
    }

    @Override
    @Transactional(readOnly = true)
   // @PreAuthorize("hasAnyRole('user', 'admin')")
    public List<Band> findAllBands() {
        logger.debug("findAll()");
        return this.bandDao.findAll();
    }

    @Override
    @Transactional
    public void deleteAllBands() {
        logger.debug("deleteAllBands()");
        bandDao.deleteAllBands();
    }
}
