package com.epam.brest.service.faker;

import com.epam.brest.model.TrackDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TrackDtoFakerServiceImplIT {

    private final Logger logger = LogManager.getLogger(BandFakerServiceImplIT.class);

    private final TrackDtoFakerService trackDtoFakerService = new TrackDtoFakerServiceImpl();

    @Test
    void fillFakeTracksDto() {
        logger.debug("fillFakeTracksDto()");
        Integer size = 50;
        List<TrackDto> fakerObjects = trackDtoFakerService.fillFakeTracksDto(size, "EN");
        assertNotNull(fakerObjects);
        assertEquals(size, fakerObjects.size());
    }
}