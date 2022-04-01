package com.epam.brest.service.impl.jdbc;

import com.epam.brest.dao.BandDtoDao;
import com.epam.brest.model.BandDto;
import com.epam.brest.service.BandDtoService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@Profile({"jdbc"})
public class BandDtoServiceImpl implements BandDtoService {

    private final Logger logger = LogManager.getLogger(BandDtoServiceImpl.class);

    private final BandDtoDao bandDtoDao;

    public BandDtoServiceImpl(BandDtoDao bandDtoDao) {
        this.bandDtoDao = bandDtoDao;
    }

    @Override
    public List<BandDto> findAllWithCountTrack() {
        logger.debug("findAllWithCountTrack()");
        return bandDtoDao.findAllWithCountTrack();
    }

}
