package com.epam.brest.service.impl;

import com.epam.brest.dao.exception.NotUniqueException;
import com.epam.brest.model.Band;
import com.epam.brest.service.BandService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath*:test-db.xml", "classpath*:service-context-test.xml"})
@Transactional
class BandServiceImplIT {

    @Autowired
    BandService bandService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void shouldCount() {
        assertNotNull(bandService);
        Integer quantity = bandService.count();
        assertNotNull(quantity);
        assertTrue(quantity > 0);
        assertEquals(Integer.valueOf(3), quantity);
    }

    @Test
    void create() {
        assertNotNull(bandService);
        Integer bandsSizeBefore = bandService.count();
        assertNotNull(bandsSizeBefore);
        Band band = new Band("P.O.D.");
        Integer newBandId = bandService.create(band);
        assertNotNull(newBandId);
        assertEquals(bandsSizeBefore, bandService.count() - 1);
    }

    @Test
    void tryToCreateEqualsBands() {
        assertNotNull(bandService);
        Band band = new Band("Offspring");
        assertThrows(NotUniqueException.class, () -> {
            bandService.create(band);
            bandService.create(band);
        });
    }
}