package com.epam.brest.dao;

import com.epam.brest.dao.exception.NotUniqueException;
import com.epam.brest.model.Band;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath*:test-db.xml", "classpath*:test-jdbc-conf.xml"})
@Transactional
class BandDaoJDBCImplIT {

    private final Logger logger = LogManager.getLogger(BandDaoJDBCImplIT.class);

    private final BandDaoJDBCImpl bandDaoJDBC;

    public BandDaoJDBCImplIT(@Autowired BandDao bandDaoJDBC) {
        this.bandDaoJDBC = (BandDaoJDBCImpl) bandDaoJDBC;
    }

    @Test
    void testFindAll() {
        logger.debug("Band execute test: findAll()");
        assertNotNull(bandDaoJDBC);
        assertNotNull(bandDaoJDBC.findAll());
    }

    @Test
    void testCreate() {
        logger.debug("Band execute test: create()");
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
    void testShouldCount() {
        logger.debug("Band execute test: shouldCount()");
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
        logger.debug("Band execute test: updateBand()");
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
    void testDeleteBand() {
        logger.debug("Band execute test: deleteBand()");
        bandDaoJDBC.create(new Band("5Diez"));
        List<Band> bands = bandDaoJDBC.findAll();
        bandDaoJDBC.delete(bands.get(bands.size() - 1).getBandId());
        assertEquals(bands.size() - 1, bandDaoJDBC.findAll().size());
    }


}