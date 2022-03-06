package com.epam.brest.service.impl;

import com.epam.brest.model.BandDto;
import com.epam.brest.service.BandDtoFakerService;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class BandDtoFakerServiceImplTest {

    private BandDtoFakerService bandDtoFakerService = new BandDtoFakerServiceImpl();

    @Test
    void fillFakeBandsDto() {
        Integer size = 50;
        List<BandDto> fakerObjects = bandDtoFakerService.fillFakeBandsDto(size, "EN");
        assertNotNull(fakerObjects);
        assertEquals(size, fakerObjects.size());
    }
}