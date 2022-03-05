package com.epam.brest.service.impl;

import com.epam.brest.model.Band;
import com.epam.brest.service.BandFakerService;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BandFakerServiceImplTest {

    private BandFakerService bandFakerService = new BandFakerServiceImpl();

    @Test
    public void fillFakeBandsTest() {
        Integer size = 50;
        List<Band> fakerObjects = bandFakerService.fillFakeBands(size, "EN");
        assertNotNull(fakerObjects);
        assertTrue(fakerObjects.size() > 0);
    }

}