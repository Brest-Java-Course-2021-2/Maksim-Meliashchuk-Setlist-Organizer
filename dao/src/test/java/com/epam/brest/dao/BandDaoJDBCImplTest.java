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
class BandDaoJDBCImplTest {

    private final Logger logger = LogManager.getLogger(BandDaoJDBCImplTest.class);

    private final BandDaoJDBCImpl bandDaoJDBC;

    public BandDaoJDBCImplTest(@Autowired BandDao bandDaoJDBC) {
        this.bandDaoJDBC = (BandDaoJDBCImpl) bandDaoJDBC;
    }

    @Test
    void findAll() {
        logger.debug("Execute test: findAll()");
        assertNotNull(bandDaoJDBC);
        assertNotNull(bandDaoJDBC.findAll());
    }

    @Test
    void create() {
        logger.debug("Execute test: create()");
        assertNotNull(bandDaoJDBC);
        int bandsSizeBefore = bandDaoJDBC.count();
        Band band = new Band("Gods Tower", "Band of metal");
        Integer newBandId = bandDaoJDBC.create(band);
        assertNotNull(newBandId);
        assertEquals(bandsSizeBefore, bandDaoJDBC.count() - 1);
    }

    @Test
    void tryToCreateBandNotUniqueException() {
        logger.debug("Execute test: tryToCreateBandNotUniqueException()");
        assertNotNull(bandDaoJDBC);
        Band band = new Band("Gods Tower");
        assertThrows(NotUniqueException.class, () -> {
            bandDaoJDBC.create(band);
            bandDaoJDBC.create(band);
        });
    }

    @Test
    void shouldCount() {
        assertNotNull(bandDaoJDBC);
        Integer quantity = bandDaoJDBC.count();
        assertNotNull(quantity);
        assertTrue(quantity > 0);
        assertEquals(Integer.valueOf(3), quantity);
    }

    @Test
    void getBandById() {
        List<Band> bands = bandDaoJDBC.findAll();
        if (bands.size() == 0) {
            bandDaoJDBC.create(new Band("5Diez"));
            bands = bandDaoJDBC.findAll();
        }

        Band bandSrc = bands.get(0);
        Band bandDst = bandDaoJDBC.getBandById(bandSrc.getBandId());
        assertEquals(bandSrc.getBandName(), bandDst.getBandName());
    }

    @Test
    void updateBand() {
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
    void deleteBand() {
        bandDaoJDBC.create(new Band("5Diez"));
        List<Band> bands = bandDaoJDBC.findAll();

        bandDaoJDBC.delete(bands.get(bands.size() - 1).getBandId());
        assertEquals(bands.size() - 1, bandDaoJDBC.findAll().size());
    }


}