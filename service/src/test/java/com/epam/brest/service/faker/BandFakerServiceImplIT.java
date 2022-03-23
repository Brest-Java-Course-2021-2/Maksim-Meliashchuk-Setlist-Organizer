package com.epam.brest.service.faker;

import com.epam.brest.model.Band;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BandFakerServiceImplIT {

    private final Logger logger = LogManager.getLogger(BandFakerServiceImplIT.class);

    private final BandFakerService bandFakerService = new BandFakerServiceImpl();

    @Test
    public void fillFakeBandsTest() {
        logger.debug("fillFakeBandsTest()");
        Integer size = 50;
        List<Band> fakerObjects = bandFakerService.fillFakeBands(size, "EN");
        assertNotNull(fakerObjects);
        assertEquals(size, fakerObjects.size());
    }

}