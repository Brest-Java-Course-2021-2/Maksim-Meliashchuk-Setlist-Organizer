package com.epam.brest.dao;

import com.epam.brest.model.Band;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath*:test-db.xml", "classpath*:test-jdbc-conf.xml"})
class BandDaoJDBCImplTest {

    private final BandDaoJDBCImpl bandDaoJDBC;

    public BandDaoJDBCImplTest(@Autowired BandDao bandDaoJDBC) {
        this.bandDaoJDBC = (BandDaoJDBCImpl) bandDaoJDBC;
    }

    @Test
    void findAll() {
        assertNotNull(bandDaoJDBC);
        assertNotNull(bandDaoJDBC.findAll());
    }

    @Test
    void create() {
        assertNotNull(bandDaoJDBC);
        int departmentsSizeBefore = bandDaoJDBC.findAll().size();
        Band band = new Band("Gods Tower");
        Integer newBandId = bandDaoJDBC.create(band);
        assertNotNull(newBandId);
        assertEquals(departmentsSizeBefore, bandDaoJDBC.findAll().size() - 1);
    }

    @Test
    void tryToCreateBandNotUniqueException() {
        assertNotNull(bandDaoJDBC);
        Band band = new Band("Vicious crusade");
        assertThrows(NotUniqueException.class, () -> {
            bandDaoJDBC.create(band);
            bandDaoJDBC.create(band);
        });
    }

}