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

import java.time.LocalDate;
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

    @Test
    void testFindAllTracksWithReleaseDateFilter() {
        logger.debug("Track execute test: testFindAllTracksWithReleaseDateFilter()");
        assertNotNull(trackDtoDaoJdbc);
        LocalDate fromDate = LocalDate.parse("2012-03-12");
        LocalDate toDate =  LocalDate.parse("2020-01-01");
        assertNotNull(trackDtoDaoJdbc.findAllTracksWithReleaseDateFilter(fromDate, toDate));
        List<TrackDto> tracks = trackDtoDaoJdbc.findAllTracksWithReleaseDateFilter(fromDate, toDate);
        assertEquals(1, tracks.size());
        tracks = trackDtoDaoJdbc.findAllTracksWithReleaseDateFilter(fromDate, null);
        assertEquals(1,tracks.size());
        tracks = trackDtoDaoJdbc.findAllTracksWithReleaseDateFilter(null, toDate);
        assertEquals(0,tracks.size());
        tracks = trackDtoDaoJdbc.findAllTracksWithReleaseDateFilter(fromDate, fromDate);
        assertEquals(1,tracks.size());
        tracks = trackDtoDaoJdbc.findAllTracksWithReleaseDateFilter(toDate, toDate);
        assertEquals(0,tracks.size());
        tracks = trackDtoDaoJdbc.findAllTracksWithReleaseDateFilter(null, null);
        assertEquals(3,tracks.size());
    }
}