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
        int bandsSizeBefore = bandDaoJDBC.findAll().size();
        Band band = new Band("Gods Tower");
        Integer newBandId = bandDaoJDBC.create(band);
        assertNotNull(newBandId);
        assertEquals(bandsSizeBefore, bandDaoJDBC.findAll().size() - 1);
    }

    @Test
    void tryToCreateBandNotUniqueException() {
        logger.debug("Execute test: tryToCreateBandNotUniqueException()");
        assertNotNull(bandDaoJDBC);
        Band band = new Band("Vicious crusade");
        assertThrows(NotUniqueException.class, () -> {
            bandDaoJDBC.create(band);
            bandDaoJDBC.create(band);
        });
    }

}