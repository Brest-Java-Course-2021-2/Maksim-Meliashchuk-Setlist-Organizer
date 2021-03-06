package com.epam.brest.service.impl.jdbc;

import com.epam.brest.model.TrackDto;
import com.epam.brest.service.TrackDtoService;
import com.epam.brest.service.config.TrackServiceTestConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@Import({TrackServiceTestConfig.class})
@PropertySource({"classpath:sql-track.properties"})
@Transactional
@Rollback
@ActiveProfiles({"test","jdbc"})
class TrackDtoServiceImplIT {

    private final Logger logger = LogManager.getLogger(TrackDtoServiceImplIT.class);

    @Autowired
    private TrackDtoService trackDtoService;

    @Test
    void testFindAllTracksWithBandName() {
        logger.debug("TrackDtoService execute test: testFindAllTracksWithBandName()");
        List<TrackDto> tracks = trackDtoService.findAllTracksWithBandName();
        assertNotNull(tracks);
        assertTrue(tracks.size() > 0);
        assertTrue(tracks.get(0).getTrackBandName().length() > 0);
    }

    @Test
    void testFindAllTracksWithBandNameByBandId() {
        logger.debug("TrackDtoService execute test: testFindAllTracksWithBandNameByBandId()");
        int id = 3;
        List<TrackDto> tracks = trackDtoService.findAllTracksWithBandNameByBandId(id);
        assertNotNull(tracks);
        assertTrue(tracks.size() > 0);
        assertTrue(tracks.get(0).getTrackBandName().length() > 0);
    }

    @Test
    void testFindAllTracksWithReleaseDateFilter() {
        logger.debug("TrackDtoService execute test: testFindAllTracksWithReleaseDateFilter");
        LocalDate fromDate = LocalDate.parse("2012-03-12");
        LocalDate toDate =  LocalDate.parse("2020-01-01");
        assertNotNull(trackDtoService.findAllTracksWithReleaseDateFilter(fromDate, toDate));
        List<TrackDto> tracks = trackDtoService.findAllTracksWithReleaseDateFilter(fromDate, toDate);
        assertEquals(1, tracks.size());
        tracks = trackDtoService.findAllTracksWithReleaseDateFilter(fromDate, null);
        assertEquals(2,tracks.size());
        tracks = trackDtoService.findAllTracksWithReleaseDateFilter(null, toDate);
        assertEquals(3,tracks.size());
        tracks = trackDtoService.findAllTracksWithReleaseDateFilter(fromDate, fromDate);
        assertEquals(0,tracks.size());
        tracks = trackDtoService.findAllTracksWithReleaseDateFilter(toDate, toDate);
        assertEquals(0,tracks.size());
        tracks = trackDtoService.findAllTracksWithReleaseDateFilter(null, null);
        assertEquals(4,tracks.size());
    }


}
