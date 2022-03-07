package com.epam.brest.service.impl;

import com.epam.brest.model.Track;
import com.epam.brest.service.TrackFakerService;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TrackFakerServiceImplTest {

    private final TrackFakerService trackFakerService = new TrackFakerServiceImpl();

    @Test
    void fillFakeTracks() {
        Integer size = 50;
        List<Track> fakerObjects = trackFakerService.fillFakeTracks(size, "EN");
        assertNotNull(fakerObjects);
        assertEquals(size, fakerObjects.size());
    }
}