package com.epam.brest.service.impl;

import com.epam.brest.dao.BandDao;
import com.epam.brest.model.Band;
import com.epam.brest.service.BandService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class BandServiceImpl implements BandService {

    private final Logger logger = LogManager.getLogger(BandServiceImpl.class);

    private final BandDao bandDao;

    public BandServiceImpl(BandDao bandDao) {
        this.bandDao = bandDao;
    }

    @Override
    @Transactional
    public Integer create(Band band) {
        logger.debug("create({})", band);
        return this.bandDao.create(band);
    }

    @Override
    @Transactional(readOnly = true)
    public Integer count() {
        logger.debug("count()");
        return this.bandDao.count();
    }
}
