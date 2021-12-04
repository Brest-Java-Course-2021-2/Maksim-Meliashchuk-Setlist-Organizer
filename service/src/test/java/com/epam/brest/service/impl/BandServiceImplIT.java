package com.epam.brest.service.impl;

import com.epam.brest.dao.exception.NotUniqueException;
import com.epam.brest.model.Band;
import com.epam.brest.service.BandService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath*:test-db.xml", "classpath*:service-context-test.xml"})
@Transactional
class BandServiceImplIT {

    private final Logger logger = LogManager.getLogger(BandServiceImplIT.class);

    @Autowired
    BandService bandService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testGetBandById () {
        logger.debug("Band service execute test: testGetBandById()");
        assertNotNull(bandService);
        Band band = new Band("Test band");
        Integer bandId = bandService.create(band);
        Band bandExtracted = bandService.getBandById(bandId);
        assertEquals(band.getBandName().toUpperCase(), bandExtracted.getBandName());
        assertEquals(band.getBandDetails(), bandExtracted.getBandDetails());
    }

    @Test
    void testUpdate () {
        logger.debug("Band service execute test: testUpdate()");
        assertNotNull(bandService);
        Integer bandId = 1;
        Band bandSrc = bandService.getBandById(bandId);
        bandSrc.setBandName(bandSrc.getBandName() + "#");
        bandSrc.setBandDetails(bandSrc.getBandDetails() + "#");
        bandService.update(bandSrc);
        Band bandDst = bandService.getBandById(bandSrc.getBandId());
        assertEquals(bandSrc.getBandName(), bandDst.getBandName());
        assertEquals(bandSrc.getBandDetails(), bandDst.getBandDetails());
    }

    @Test
    void testShouldCount() {
        logger.debug("Band service execute test: testShouldCount()");
        assertNotNull(bandService);
        Integer quantity = bandService.count();
        assertNotNull(quantity);
        assertTrue(quantity > 0);
        assertEquals(Integer.valueOf(3), quantity);
    }

    @Test
    void testCreate() {
        logger.debug("Band service execute test: testCreate()");
        assertNotNull(bandService);
        Integer bandsSizeBefore = bandService.count();
        assertNotNull(bandsSizeBefore);
        Band band = new Band("P.O.D.");
        Integer newBandId = bandService.create(band);
        assertNotNull(newBandId);
        assertEquals(bandsSizeBefore, bandService.count() - 1);
    }

    @Test
    void testDelete() {
        logger.debug("Band service execute test: testDelete()");
        Integer bandId = 3;
        assertNotNull(bandService);
        Integer bandsSizeBefore = bandService.count();
        assertNotNull(bandsSizeBefore);
        bandService.delete(bandId);
        assertEquals(bandsSizeBefore, bandService.count() + 1);
    }

    @Test
    void testFindAll() {
        logger.debug("Band service execute test: testFindAll()");
        assertNotNull(bandService);
        assertNotNull(bandService.findAllBands());
        List<Band> bands = bandService.findAllBands();
        assertNotNull(bands);
        assertTrue(bands.size() > 0);
    }

    @Test
    void testTryToCreateEqualsBands() {
        logger.debug("Band service execute test: testTryToCreateEqualsBands()");
        assertNotNull(bandService);
        Band band = new Band("Offspring");
        assertThrows(NotUniqueException.class, () -> {
            bandService.create(band);
            bandService.create(band);
        });
    }

}