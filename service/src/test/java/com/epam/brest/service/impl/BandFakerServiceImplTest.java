package com.epam.brest.service.impl;

import com.epam.brest.model.Band;
import com.epam.brest.service.BandFakerService;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BandFakerServiceImplTest {

    private final BandFakerService bandFakerService = new BandFakerServiceImpl();

    @Test
    public void fillFakeBandsTest() {
        Integer size = 50;
        List<Band> fakerObjects = bandFakerService.fillFakeBands(size, "EN");
        assertNotNull(fakerObjects);
        assertEquals(size, fakerObjects.size());
    }

}