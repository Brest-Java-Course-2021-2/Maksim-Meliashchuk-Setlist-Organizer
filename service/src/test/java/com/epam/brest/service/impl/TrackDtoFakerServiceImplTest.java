package com.epam.brest.service.impl;

import com.epam.brest.model.TrackDto;
import com.epam.brest.service.TrackDtoFakerService;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TrackDtoFakerServiceImplTest {

    private final TrackDtoFakerService trackDtoFakerService = new TrackDtoFakerServiceImpl();

    @Test
    void fillFakeTracksDto() {
        Integer size = 50;
        List<TrackDto> fakerObjects = trackDtoFakerService.fillFakeTracksDto(size, "EN");
        assertNotNull(fakerObjects);
        assertEquals(size, fakerObjects.size());
    }
}