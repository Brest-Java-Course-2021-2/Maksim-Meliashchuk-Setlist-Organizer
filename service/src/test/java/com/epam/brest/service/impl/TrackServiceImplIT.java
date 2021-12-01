package com.epam.brest.service.impl;

import com.epam.brest.model.Band;
import com.epam.brest.model.Track;
import com.epam.brest.service.BandService;
import com.epam.brest.service.TrackService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath*:test-db.xml", "classpath*:service-context-test.xml"})
@Transactional
class TrackServiceImplIT {

    private final Logger logger = LogManager.getLogger(TrackServiceImplIT.class);

    @Autowired
    TrackService trackService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testGetTrackById() {
        logger.debug("TrackService execute test: testGetTrackById()");
        assertNotNull(trackService);
        Track track = new Track("Test track");
        Integer trackId = trackService.create(track);
        Track trackExtracted = trackService.getTrackById(trackId);
        assertEquals(track.getTrackName(), trackExtracted.getTrackName());
        assertEquals(track.getTrackDetails(), trackExtracted.getTrackDetails());
        assertEquals(track.getTrackTempo(), trackExtracted.getTrackTempo());
        assertEquals(track.getTrackLink(), trackExtracted.getTrackLink());
        assertEquals(track.getTrackDuration(), trackExtracted.getTrackDuration());
        assertEquals(track.getTrackReleaseDate(), trackExtracted.getTrackReleaseDate());
        assertEquals(track.getTrackBandId(), trackExtracted.getTrackBandId());
    }

    @Test
    void testCreate() {
        logger.debug("TrackService execute test: testCreate()");
        assertNotNull(trackService);
        Integer bandsSizeBefore = trackService.count();
        assertNotNull(bandsSizeBefore);
        Track track = new Track("Test track");
        Integer newTrackId = trackService.create(track);
        assertNotNull(newTrackId);
        assertEquals(bandsSizeBefore, trackService.count() - 1);
    }

    @Test
    void testUpdate() {
        logger.debug("TrackService execute test: testUpdate()");
        assertNotNull(trackService);
        Integer trackId = 1;
        Track trackSrc = trackService.getTrackById(trackId);
        trackSrc.setTrackName(trackSrc.getTrackName() + "#");
        trackSrc.setTrackDetails(trackSrc.getTrackDetails() + "#");
        trackSrc.setTrackTempo(trackSrc.getTrackTempo() + 1);
        trackSrc.setTrackLink(trackSrc.getTrackLink() + "#");
        trackSrc.setTrackDuration(trackSrc.getTrackDuration() + 1);
        trackSrc.setTrackReleaseDate(trackSrc.getTrackReleaseDate().plusMonths(1));
        trackSrc.setTrackBandId(trackSrc.getTrackBandId() + 1);
        trackService.update(trackSrc);
        Track trackDst = trackService.getTrackById(trackSrc.getTrackId());
        assertEquals(trackSrc.getTrackName(), trackDst.getTrackName());
        assertEquals(trackSrc.getTrackDetails(), trackDst.getTrackDetails());
        assertEquals(trackSrc.getTrackTempo(), trackDst.getTrackTempo());
        assertEquals(trackSrc.getTrackLink(), trackDst.getTrackLink());
        assertEquals(trackSrc.getTrackDuration(), trackDst.getTrackDuration());
        assertEquals(trackSrc.getTrackReleaseDate(), trackDst.getTrackReleaseDate());
        assertEquals(trackSrc.getTrackBandId(), trackDst.getTrackBandId());
    }

    @Test
    void testDelete() {
        logger.debug("TrackService execute test: testDelete()");
        assertNotNull(trackService);
        Integer bandsSizeBefore = trackService.count();
        assertNotNull(bandsSizeBefore);
        trackService.delete(trackService.count() - 1);
        assertEquals(bandsSizeBefore, trackService.count() + 1);
    }

    @Test
    void testCount() {
        logger.debug("TrackService execute test: testCount()");
        assertNotNull(trackService);
        Integer quantity = trackService.count();
        assertNotNull(quantity);
        assertTrue(quantity > 0);
        assertEquals(Integer.valueOf(3), quantity);
    }
}