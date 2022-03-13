package com.epam.brest.service.faker;

import com.epam.brest.model.BandDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class BandDtoFakerServiceImplTest {

    private final Logger logger = LogManager.getLogger(BandDtoFakerServiceImplTest.class);

    private final BandDtoFakerService bandDtoFakerService = new BandDtoFakerServiceImpl();

    @Test
    void fillFakeBandsDto() {
        logger.debug("fillFakeBandsDto()");
        Integer size = 50;
        List<BandDto> fakerObjects = bandDtoFakerService.fillFakeBandsDto(size, "EN");
        assertNotNull(fakerObjects);
        assertEquals(size, fakerObjects.size());
    }
}