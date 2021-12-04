package com.epam.brest.service.impl;

import com.epam.brest.dao.BandDao;
import com.epam.brest.model.Band;
import com.epam.brest.service.BandService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class BandServiceImpl implements BandService {

    private final Logger logger = LogManager.getLogger(BandServiceImpl.class);

    private final BandDao bandDao;

    public BandServiceImpl(BandDao bandDao) {
        this.bandDao = bandDao;
    }

    @Override
    public Band getBandById(Integer bandId) {
        logger.debug("Get band by id = {}", bandId);
        return this.bandDao.getBandById(bandId);
    }

    @Override
    public Integer create(Band band) {
        logger.debug("create({})", band);
        return this.bandDao.create(band);
    }

    @Override
    public Integer update(Band band) {
        logger.debug("update({})", band);
        return this.bandDao.update(band);
    }

    @Override
    public Integer delete(Integer bandId) {
        logger.debug("delete({})", bandId);
        logger.debug("delete band with id = {}", bandId);
        return this.bandDao.delete(bandId);
    }

    @Override
    @Transactional(readOnly = true)
    public Integer count() {
        logger.debug("count()");
        return this.bandDao.count();
    }

    @Override
    public List<Band> findAllBands() {
        logger.debug("findAll()");
        return this.bandDao.findAll();
    }
}
