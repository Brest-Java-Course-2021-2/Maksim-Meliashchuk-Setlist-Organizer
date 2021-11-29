package com.epam.brest.dao;

import com.epam.brest.model.Track;
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
public class TrackDaoJDBCImplIT {

    private final Logger logger = LogManager.getLogger(TrackDaoJDBCImplIT.class);

    private final TrackDaoJDBCImpl trackDaoJDBC;

    public TrackDaoJDBCImplIT(@Autowired TrackDao trackDaoJDBC) {
        this.trackDaoJDBC = (TrackDaoJDBCImpl) trackDaoJDBC;
    }

    @Test
    void findAll() {
        logger.debug("Track execute test: findAll()");
        assertNotNull(trackDaoJDBC);
        assertNotNull(trackDaoJDBC.findAll());
    }

    @Test
    void getTrackById() {
    }

    @Test
    void create() {
        logger.debug("Track execute test: create()");
        assertNotNull(trackDaoJDBC);
        int tracksSizeBefore = trackDaoJDBC.count();
        Track track = new Track("new track");
        Integer newTrackId = trackDaoJDBC.create(track);
        assertNotNull(newTrackId);
        assertEquals(tracksSizeBefore, trackDaoJDBC.count() - 1);
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    @Test
    void shouldCount() {
        logger.debug("Track execute test: shouldCount()");
        assertNotNull(trackDaoJDBC);
        Integer quantity = trackDaoJDBC.count();
        assertNotNull(quantity);
        assertTrue(quantity > 0);
        assertEquals(Integer.valueOf(3), quantity);
    }
}
