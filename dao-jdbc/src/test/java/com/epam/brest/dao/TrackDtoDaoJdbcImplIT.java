package com.epam.brest.dao;

import com.epam.brest.SpringJdbcConfig;
import com.epam.brest.dao.jdbc.TrackDtoDaoJdbcImpl;
import com.epam.brest.model.TrackDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@DataJdbcTest
@Import({TrackDtoDaoJdbcImpl.class})
@ContextConfiguration(classes = SpringJdbcConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
@Rollback
@ActiveProfiles({"dev", "jdbc"})
class TrackDtoDaoJdbcImplIT {

    private final Logger logger = LogManager.getLogger(TrackDtoDaoJdbcImplIT.class);

    @Autowired
    private TrackDtoDaoJdbcImpl trackDtoDaoJdbc;

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
    void testFindAllTracksWithBandNameByBandId() {
        logger.debug("Track execute test: testFindAllTracksWithBandNameByBandId()");
        int id = 3;
        assertNotNull(trackDtoDaoJdbc);
        assertNotNull(trackDtoDaoJdbc.findAllTracksWithBandNameByBandId(id));
        List<TrackDto> tracks = trackDtoDaoJdbc.findAllTracksWithBandNameByBandId(id);
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
        assertEquals(2,tracks.size());
        tracks = trackDtoDaoJdbc.findAllTracksWithReleaseDateFilter(null, toDate);
        assertEquals(3,tracks.size());
        tracks = trackDtoDaoJdbc.findAllTracksWithReleaseDateFilter(fromDate, fromDate);
        assertEquals(0,tracks.size());
        tracks = trackDtoDaoJdbc.findAllTracksWithReleaseDateFilter(toDate, toDate);
        assertEquals(0,tracks.size());
        tracks = trackDtoDaoJdbc.findAllTracksWithReleaseDateFilter(null, null);
        assertEquals(4,tracks.size());
    }
}
