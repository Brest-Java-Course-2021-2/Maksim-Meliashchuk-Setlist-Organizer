package com.epam.brest.dao;

import com.epam.brest.model.dto.BandDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath*:test-db.xml", "classpath*:test-jdbc-conf.xml"})
@Transactional
class BandDtoDaoJdbcImplIT {

    private final Logger logger = LogManager.getLogger(BandDtoDaoJdbcImplIT.class);

    private final BandDtoDaoJdbcImpl bandDtoDaoJdbc;

    BandDtoDaoJdbcImplIT(@Autowired BandDtoDao bandDtoDaoJdbc) {
        this.bandDtoDaoJdbc = (BandDtoDaoJdbcImpl) bandDtoDaoJdbc;
    }

    @Test
    void testFindAllWithCountTrack() {
        logger.debug("Track execute test: findAllWithCountTrack()");
        assertNotNull(bandDtoDaoJdbc);
        assertNotNull(bandDtoDaoJdbc.findAllWithCountTrack());
        List<BandDto> bands = bandDtoDaoJdbc.findAllWithCountTrack();
        assertNotNull(bands);
        assertTrue(bands.size() > 0);
    }
}