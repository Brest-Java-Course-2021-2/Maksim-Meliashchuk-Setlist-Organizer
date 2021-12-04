package com.epam.brest.dao;

import com.epam.brest.model.dto.TrackDto;
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
class TrackDtoDaoJdbcImplIT {

    private final Logger logger = LogManager.getLogger(TrackDtoDaoJdbcImplIT.class);

    private final TrackDtoDaoJdbcImpl trackDtoDaoJdbc;

    TrackDtoDaoJdbcImplIT(@Autowired TrackDtoDao trackDtoDaoJdbc) {
        this.trackDtoDaoJdbc = (TrackDtoDaoJdbcImpl) trackDtoDaoJdbc;
    }

    @Test
    void testFindAllTracksWithBandName() {
        logger.debug("Track execute test: testFindAllTracksWithBandName()");
        assertNotNull(trackDtoDaoJdbc);
        assertNotNull(trackDtoDaoJdbc.findAllTracksWithBandName());
        List<TrackDto> tracks = trackDtoDaoJdbc.findAllTracksWithBandName();
        assertNotNull(tracks);
        assertTrue(tracks.size() > 0);
    }
}