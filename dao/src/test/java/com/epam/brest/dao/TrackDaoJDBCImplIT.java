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

import java.time.LocalDate;
import java.util.List;

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
        List<Track> tracks = trackDaoJDBC.findAll();
        assertNotNull(tracks);
        assertTrue(tracks.size() > 0);
    }

    @Test
    void getTrackById() {
        logger.debug("Track execute test: getById()");
        List<Track> tracks = trackDaoJDBC.findAll();
        if (tracks.size() == 0) {
            Track track = new Track("new track");
            trackDaoJDBC.create(track);
            tracks = trackDaoJDBC.findAll();
        }
        Track trackSrc = tracks.get(0);
        Track trackDst = trackDaoJDBC.getTrackById(trackSrc.getTrackId());
        assertEquals(trackSrc.getTrackName(), trackDst.getTrackName());
    }

    @Test
    void create() {
        logger.debug("Track execute test: create()");
        assertNotNull(trackDaoJDBC);
        int tracksSizeBefore = trackDaoJDBC.count();
        Track track = new Track("new track");
        track.setTrackReleaseDate(LocalDate.of(2021, 11, 30));
        Integer newTrackId = trackDaoJDBC.create(track);
        assertNotNull(trackDaoJDBC.findAll());
        assertNotNull(newTrackId);
        assertEquals(tracksSizeBefore, trackDaoJDBC.count() - 1);
    }

    @Test
    void update() {
        logger.debug("Track execute test: update()");
        assertNotNull(trackDaoJDBC);
        List<Track> tracks = trackDaoJDBC.findAll();
        if (tracks.size() == 0) {
            Track track = new Track("new track");
            trackDaoJDBC.create(track);
            tracks = trackDaoJDBC.findAll();
        }
        Track trackSrc = tracks.get(0);
        trackSrc.setTrackName(trackSrc.getTrackName() + "#");
        trackSrc.setTrackDetails(trackSrc.getTrackDetails() + "#");
        trackSrc.setTrackTempo(trackSrc.getTrackTempo() + 1);
        trackSrc.setTrackLink(trackSrc.getTrackLink() + "#");
        trackSrc.setTrackDuration(trackSrc.getTrackDuration() + 1);
        trackSrc.setTrackReleaseDate(trackSrc.getTrackReleaseDate().plusMonths(1));
        trackSrc.setTrackBandId(trackSrc.getTrackBandId() + 1);
        trackDaoJDBC.update(trackSrc);
        Track trackDst = trackDaoJDBC.getTrackById(trackSrc.getTrackId());
        assertEquals(trackSrc.getTrackName(), trackDst.getTrackName());
        assertEquals(trackSrc.getTrackDetails(), trackDst.getTrackDetails());
        assertEquals(trackSrc.getTrackTempo(), trackDst.getTrackTempo());
        assertEquals(trackSrc.getTrackLink(), trackDst.getTrackLink());
        assertEquals(trackSrc.getTrackDuration(), trackDst.getTrackDuration());
        assertEquals(trackSrc.getTrackReleaseDate(), trackDst.getTrackReleaseDate());
        assertEquals(trackSrc.getTrackBandId(), trackDst.getTrackBandId());
    }

    @Test
    void delete() {
        logger.debug("Track execute test: delete()");
        Track track = new Track("new track");
        trackDaoJDBC.create(track);
        assertNotNull(trackDaoJDBC.findAll());
        List<Track> tracks = trackDaoJDBC.findAll();
        assertNotNull(tracks);
        trackDaoJDBC.delete(tracks.get(tracks.size() - 1).getTrackId());
        assertEquals(tracks.size() - 1, trackDaoJDBC.findAll().size());
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
