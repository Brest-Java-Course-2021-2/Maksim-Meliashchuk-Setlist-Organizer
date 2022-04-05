package com.epam.brest.dao.jpa.mapper;

import com.epam.brest.dao.jpa.entity.BandEntity;
import com.epam.brest.dao.jpa.entity.TrackEntity;
import com.epam.brest.model.Track;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
class TrackToEntityMapperTest {

    @Test
    void trackToTrackEntity() {
        log.debug("trackToTrackEntity()");
        Track track = Track.builder()
                .trackBandId(1)
                .trackName("Test track")
                .trackDetails("details")
                .trackDuration(100)
                .trackReleaseDate(LocalDate.parse("2020-01-01"))
                .trackLink("https://yputube.com/testing")
                .trackTempo(120)
                .trackId(1)
                .build();
        TrackEntity trackEntity = TrackToEntityMapper.MAPPER.trackToTrackEntity(track);
        assertEquals(track.getTrackId(), trackEntity.getTrackId());
        assertEquals(track.getTrackName(), trackEntity.getTrackName());
        assertEquals(track.getTrackDetails(), trackEntity.getTrackDetails());
        assertEquals(track.getTrackTempo(), trackEntity.getTrackTempo());
        assertEquals(track.getTrackDuration(), trackEntity.getTrackDuration());
        assertEquals(track.getTrackReleaseDate(), trackEntity.getTrackReleaseDate());
    }

    @Test
    void trackEntityToTrack() {
        log.debug("trackEntityToTrack()");
        BandEntity band = BandEntity.builder()
                .bandId(1)
                .bandName("test band")
                .bandDetails("test details")
                .build();
        TrackEntity trackEntity = TrackEntity.builder()
                .band(band)
                .trackName("Test track")
                .trackDetails("details")
                .trackDuration(100)
                .trackReleaseDate(LocalDate.parse("2020-01-01"))
                .trackLink("https://yputube.com/testing")
                .trackTempo(120)
                .trackId(1)
                .build();
        Track track = TrackToEntityMapper.MAPPER.trackEntityToTrack(trackEntity);
        assertEquals(track.getTrackId(), trackEntity.getTrackId());
        assertEquals(track.getTrackName(), trackEntity.getTrackName());
        assertEquals(track.getTrackDetails(), trackEntity.getTrackDetails());
        assertEquals(track.getTrackTempo(), trackEntity.getTrackTempo());
        assertEquals(track.getTrackDuration(), trackEntity.getTrackDuration());
        assertEquals(track.getTrackReleaseDate(), trackEntity.getTrackReleaseDate());
        assertEquals(track.getTrackBandId(), trackEntity.getBand().getBandId());
    }
}