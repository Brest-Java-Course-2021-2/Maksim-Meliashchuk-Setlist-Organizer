package com.epam.brest.dao;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath*:test-db.xml", "classpath*:test-jdbc-conf.xml"})
class BandDaoJDBCImplTest {

    private BandDaoJDBCImpl bandDaoJDBC;

    public BandDaoJDBCImplTest(@Autowired BandDao bandDaoJDBC) {
        this.bandDaoJDBC = (BandDaoJDBCImpl) bandDaoJDBC;
    }

    @Test
    void findAll() {
        assertNotNull(bandDaoJDBC);
        assertNotNull(bandDaoJDBC.findAll());
    }

}