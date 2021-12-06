package com.epam.brest.service.impl;

import com.epam.brest.model.dto.TrackDto;
import com.epam.brest.service.TrackDtoService;
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
@ContextConfiguration(locations = {"classpath*:test-db.xml", "classpath*:service-context-test.xml"})
@Transactional
class TrackDtoServiceImplIT {

    private final Logger logger = LogManager.getLogger(TrackDtoServiceImplIT.class);

    @Autowired
    TrackDtoService trackDtoService;

    @Test
    void testFindAllTracksWithBandName() {
        logger.debug("TrackDtoService execute test: testFindAllTracksWithBandName()");
        List<TrackDto> tracks = trackDtoService.findAllTracksWithBandName();
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
        assertEquals(2,tracks.size());
        tracks = trackDtoService.findAllTracksWithReleaseDateFilter(fromDate, fromDate);
        assertEquals(1,tracks.size());
        tracks = trackDtoService.findAllTracksWithReleaseDateFilter(toDate, toDate);
        assertEquals(0,tracks.size());
        tracks = trackDtoService.findAllTracksWithReleaseDateFilter(null, null);
        assertEquals(3,tracks.size());
    }


}