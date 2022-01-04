package com.epam.brest.dao;

import com.epam.brest.SpringJdbcConfig;
import com.epam.brest.dao.exception.NotUniqueException;
import com.epam.brest.model.Band;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@DataJdbcTest
@Import({BandDaoJdbcImpl.class})
@PropertySource({"classpath:sql-band.properties"})
@ContextConfiguration(classes = SpringJdbcConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
@Rollback
class BandDaoJdbcImplIT {

    private final Logger logger = LogManager.getLogger(BandDaoJdbcImplIT.class);

    @Autowired
    private BandDaoJdbcImpl bandDaoJDBC;

   @Test
    void testFindAllBands() {
        logger.debug("Band execute test: testFindAllBands()");
        assertNotNull(1);
        assertNotNull(bandDaoJDBC);
        assertNotNull(bandDaoJDBC.findAll());
    }

 @Test
    void testCreateBand() {
        logger.debug("Band execute test: testCreateBand()");
        assertNotNull(bandDaoJDBC);
        int bandsSizeBefore = bandDaoJDBC.count();
        Band band = new Band("Gods Tower", "Band of metal");
        Integer newBandId = bandDaoJDBC.create(band);
        assertNotNull(newBandId);
        assertEquals(bandsSizeBefore, bandDaoJDBC.count() - 1);
    }

    @Test
    void testTryToCreateBandNotUniqueException() {
        logger.debug("Band execute test: tryToCreateBandNotUniqueException()");
        assertNotNull(bandDaoJDBC);
        Band band = new Band("Gods Tower");
        assertThrows(NotUniqueException.class, () -> {
            bandDaoJDBC.create(band);
            bandDaoJDBC.create(band);
        });
    }

    @Test
    void testShouldCountBands() {
        logger.debug("Band execute test: testShouldCountBands()");
        assertNotNull(bandDaoJDBC);
        Integer quantity = bandDaoJDBC.count();
        assertNotNull(quantity);
        assertTrue(quantity > 0);
        assertEquals(Integer.valueOf(3), quantity);
    }

    @Test
    void testGetBandById() {
        logger.debug("Band execute test: getBandById()");
        List<Band> bands = bandDaoJDBC.findAll();
        if (bands.size() == 0) {
            bandDaoJDBC.create(new Band("5Diez"));
            bands = bandDaoJDBC.findAll();
        }

        Band bandSrc = bands.get(0);
        Band bandDst = bandDaoJDBC.getBandById(bandSrc.getBandId());
        assertEquals(bandSrc.getBandName().toUpperCase(), bandDst.getBandName().toUpperCase());
    }

    @Test
    void testUpdateBand() {
        logger.debug("Band execute test: testUpdateBand()");
        List<Band> bands = bandDaoJDBC.findAll();
        if (bands.size() == 0) {
            bandDaoJDBC.create(new Band("5Diez"));
            bands = bandDaoJDBC.findAll();
        }
        Band bandSrc = bands.get(0);
        bandSrc.setBandName(bandSrc.getBandName() + "#");
        bandSrc.setBandDetails(bandSrc.getBandDetails() + "#");
        bandDaoJDBC.update(bandSrc);
        Band bandDst = bandDaoJDBC.getBandById(bandSrc.getBandId());
        assertEquals(bandSrc.getBandName(), bandDst.getBandName());
        assertEquals(bandSrc.getBandDetails(), bandDst.getBandDetails());
    }

    @Test
    void testUpdateBandNotUnique() {
        logger.debug("Band execute test: testUpdateBandNotUnique()");
        List<Band> bands = bandDaoJDBC.findAll();
        if (bands.size() == 0) {
            bandDaoJDBC.create(new Band("5Diez"));
            bands = bandDaoJDBC.findAll();
        }
        Band bandSrc = bands.get(0);
        bandSrc.setBandName(bandSrc.getBandName());
        bandSrc.setBandDetails(bandSrc.getBandDetails() + "#");
        assertThrows(NotUniqueException.class, () -> {
            bandDaoJDBC.update(bandSrc);
        });

    }

    @Test
    void testDeleteBand() {
        logger.debug("Band execute test: testDeleteBand()");
        bandDaoJDBC.create(new Band("5Diez"));
        List<Band> bands = bandDaoJDBC.findAll();
        bandDaoJDBC.delete(bands.get(bands.size() - 1).getBandId());
        assertEquals(bands.size() - 1, bandDaoJDBC.findAll().size());
    }

}
