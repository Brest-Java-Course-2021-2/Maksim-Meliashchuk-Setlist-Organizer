package com.epam.brest.dao;

import com.epam.brest.dao.jdbc.TrackDaoJdbcImpl;
import com.epam.brest.model.Track;
import com.epam.brest.SpringJdbcConfig;
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

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJdbcTest
@Import({TrackDaoJdbcImpl.class})
@PropertySource({"classpath:sql-track.properties"})
@ContextConfiguration(classes = SpringJdbcConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
@Rollback
public class TrackDaoJdbcImplIT {

    private final Logger logger = LogManager.getLogger(TrackDaoJdbcImplIT.class);

    @Autowired
    private TrackDaoJdbcImpl trackDaoJDBC;

    @Test
    void testFindAllTracks() {
        logger.debug("Track execute test: testFindAllTracks()");
        assertNotNull(trackDaoJDBC);
        assertNotNull(trackDaoJDBC.findAll());
        List<Track> tracks = trackDaoJDBC.findAll();
        assertNotNull(tracks);
        assertTrue(tracks.size() > 0);
    }

    @Test
    void testGetTrackById() {
        logger.debug("Track execute test: testGetTrackById()");
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
    void testCreateTrack() {
        logger.debug("Track execute test: testCreateTrack()");
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
    void testUpdateTrack() {
        logger.debug("Track execute test: testUpdateTrack()");
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
        trackSrc.setTrackBandId(trackSrc.getTrackBandId() - 1);
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
    void testDeleteTrack() {
        logger.debug("Track execute test: testDeleteTrack()");
        Track track = new Track("new track");
        trackDaoJDBC.create(track);
        assertNotNull(trackDaoJDBC.findAll());
        List<Track> tracks = trackDaoJDBC.findAll();
        assertNotNull(tracks);
        trackDaoJDBC.delete(tracks.get(tracks.size() - 1).getTrackId());
        assertEquals(tracks.size() - 1, trackDaoJDBC.findAll().size());
    }

    @Test
    void testShouldCount() {
        logger.debug("Track execute test: shouldCount()");
        assertNotNull(trackDaoJDBC);
        Integer quantity = trackDaoJDBC.count();
        assertNotNull(quantity);
        assertTrue(quantity > 0);
        assertEquals(Integer.valueOf(4), quantity);
    }
}
