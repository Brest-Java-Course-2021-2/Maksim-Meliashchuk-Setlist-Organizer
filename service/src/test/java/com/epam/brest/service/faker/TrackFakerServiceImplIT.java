package com.epam.brest.service.faker;

import com.epam.brest.model.Track;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TrackFakerServiceImplIT {

    private final Logger logger = LogManager.getLogger(TrackFakerServiceImplIT.class);

    private final TrackFakerService trackFakerService = new TrackFakerServiceImpl();

    @Test
    void fillFakeTracks() {
        logger.debug("fillFakeTracks()");
        Integer size = 50;
        List<Track> fakerObjects = trackFakerService.fillFakeTracks(size, "EN");
        assertNotNull(fakerObjects);
        assertEquals(size, fakerObjects.size());
    }
}