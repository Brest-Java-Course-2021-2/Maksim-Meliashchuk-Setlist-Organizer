package com.epam.brest.service.impl.jdbc;

import com.epam.brest.dao.exception.NotUniqueException;
import com.epam.brest.model.Band;
import com.epam.brest.service.BandService;
import com.epam.brest.service.TrackService;
import com.epam.brest.service.config.BandServiceTestConfig;
import com.epam.brest.service.config.TrackServiceTestConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@Import({BandServiceTestConfig.class, TrackServiceTestConfig.class})
@Transactional
@Rollback
@ActiveProfiles({"test","jdbc"})
@Sql({"/create-db.sql", "/init-db.sql"})
class BandServiceImplIT {

    private final Logger logger = LogManager.getLogger(BandServiceImplIT.class);

    @Autowired
    private BandService bandService;

    @Autowired
    private TrackService trackService;


    @Test
    void testGetBandById () {
        logger.debug("Band service execute test: testGetBandById()");
        assertNotNull(bandService);
        Band band = Band.builder()
                .bandName("Test band")
                .build();
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
        Band band = Band.builder()
                .bandName("P.O.D.")
                .build();
        Integer newBandId = bandService.create(band);
        assertNotNull(newBandId);
        assertEquals(bandsSizeBefore, bandService.count() - 1);
    }

    @Test
    void testDelete() {
        logger.debug("Band service execute test: testDelete()");
        Integer bandId = 2;
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
    void testDeleteAllBands() {
        logger.debug("Band service execute test: testDeleteAllBands()");
        assertNotNull(bandService);
        List<Band> bandsBefore = bandService.findAllBands();
        assertTrue(bandsBefore.size() > 0);
        trackService.deleteAllTracks();
        bandService.deleteAllBands();
        assertEquals(0, bandService.findAllBands().size());
    }

    @Test
    void testTryToCreateEqualsBands() {
        logger.debug("Band service execute test: testTryToCreateEqualsBands()");
        assertNotNull(bandService);
        Band band = Band.builder()
                .bandName("Offspring")
                .build();
        assertThrows(NotUniqueException.class, () -> {
            bandService.create(band);
            bandService.create(band);
        });
    }

}