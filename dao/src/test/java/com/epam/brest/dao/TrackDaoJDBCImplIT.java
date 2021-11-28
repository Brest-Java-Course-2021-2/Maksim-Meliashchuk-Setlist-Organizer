package com.epam.brest.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath*:test-db.xml", "classpath*:test-jdbc-conf.xml"})
@Transactional
public class TrackDaoJDBCImplIT {

    private final Logger logger = LogManager.getLogger(TrackDaoJDBCImplIT.class);

    private final TrackDaoJDBCImpl trackDaoJDBC;

    public TrackDaoJDBCImplIT(@Autowired TrackDao trackDaoJDBC) {
        this.trackDaoJDBC = (TrackDaoJDBCImpl) trackDaoJDBC;
    }

    @Test
    void findAll() {
        assertNotNull(trackDaoJDBC);
    }

    @Test
    void getTrackById() {
    }

    @Test
    void create() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    @Test
    void count() {
    }
}
