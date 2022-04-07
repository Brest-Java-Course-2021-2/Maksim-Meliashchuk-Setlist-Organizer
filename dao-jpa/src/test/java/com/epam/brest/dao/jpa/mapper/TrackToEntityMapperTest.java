package com.epam.brest.dao.jpa.mapper;

import com.epam.brest.dao.jpa.entity.BandEntity;
import com.epam.brest.dao.jpa.entity.TrackEntity;
import com.epam.brest.dao.jpa.repository.BandRepository;
import com.epam.brest.model.Track;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@Slf4j
@ExtendWith(MockitoExtension.class)
class TrackToEntityMapperTest {

    @Mock
    protected BandRepository bandRepository;

    private TrackToEntityMapper mapper
            = Mappers.getMapper(TrackToEntityMapper.class);

    @BeforeEach
    void initUseCase() {
        mapper.bandRepository = bandRepository;
    }

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
        BandEntity bandEntity = BandEntity.builder()
                .bandId(1)
                .bandName("Band")
                .bandDetails("Details")
                .build();
        when(bandRepository.findById(track.getTrackBandId())).thenReturn(Optional.of(bandEntity));
        TrackEntity trackEntity = mapper.trackToTrackEntity(track);
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
        Track track = mapper.trackEntityToTrack(trackEntity);
        assertEquals(track.getTrackId(), trackEntity.getTrackId());
        assertEquals(track.getTrackName(), trackEntity.getTrackName());
        assertEquals(track.getTrackDetails(), trackEntity.getTrackDetails());
        assertEquals(track.getTrackTempo(), trackEntity.getTrackTempo());
        assertEquals(track.getTrackDuration(), trackEntity.getTrackDuration());
        assertEquals(track.getTrackReleaseDate(), trackEntity.getTrackReleaseDate());
        assertEquals(track.getTrackBandId(), trackEntity.getBand().getBandId());
    }
}
