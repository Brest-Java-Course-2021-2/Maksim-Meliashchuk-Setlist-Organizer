package com.epam.brest.service.impl.jpa;

import com.epam.brest.dao.jpa.entity.BandEntity;
import com.epam.brest.dao.jpa.entity.TrackEntity;
import com.epam.brest.dao.jpa.mapper.TrackToEntityMapper;
import com.epam.brest.dao.jpa.mapper.TrackToEntityMapperImpl;
import com.epam.brest.dao.jpa.repository.BandRepository;
import com.epam.brest.dao.jpa.repository.TrackRepository;
import com.epam.brest.model.Track;
import com.epam.brest.service.TrackService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Slf4j
class TrackServiceJpaImplTest {

    @Mock
    private TrackRepository trackRepository;

    @Mock
    private BandRepository bandRepository;

    private TrackService trackService;

    private TrackToEntityMapper mapper;

    @BeforeEach
    void initUseCase() {
        mapper = new TrackToEntityMapperImpl();
        ReflectionTestUtils.setField(mapper, "bandRepository", bandRepository);
        trackService = new TrackServiceJpaImpl(trackRepository, mapper);
    }

    @Test
    void getTrackByIdTest() {
        log.debug("getBandByIdTest()");
        Integer id = 1;
        BandEntity band = BandEntity.builder()
                .bandId(id)
                .bandName("test band1")
                .bandDetails("test")
                .build();
        TrackEntity trackEntity = TrackEntity.builder()
                .band(band)
                .trackName("Test track1")
                .build();

        when(trackRepository.findById(id)).thenReturn(Optional.of(trackEntity));

        Track track = trackService.getTrackById(id);
        assertEquals(trackEntity.getTrackName(), track.getTrackName());
        assertEquals(trackEntity.getTrackDetails(), track.getTrackDetails());

        verify(trackRepository).findById(id);
    }

    @Test
    void createTest() {
        log.debug("createTest()");
        BandEntity bandEntity = BandEntity.builder()
                .bandId(1)
                .bandName("test band1")
                .bandDetails("test")
                .build();
        TrackEntity trackEntity = TrackEntity.builder()
                .trackId(1)
                .band(bandEntity)
                .trackName("Test track1")
                .build();
        Track track = Track.builder()
                .trackBandId(1)
                .trackId(1)
                .trackName("Test Track")
                .build();
        when(trackRepository.save(any(TrackEntity.class))).thenReturn(trackEntity);
        when(bandRepository.findById(track.getTrackBandId())).thenReturn(Optional.of(bandEntity));

        assertNotNull(trackService);
        Integer tracksSizeBefore = trackService.count();
        assertNotNull(tracksSizeBefore);

        Integer newTrackId = trackService.create(track);
        assertEquals(newTrackId, trackEntity.getTrackId());

        verify(trackRepository).save(any(TrackEntity.class));
        verify(bandRepository).findById(track.getTrackBandId());

    }

    @Test
    void updateTest() {
        log.debug("updateTest()");
        Integer id = 1;
        BandEntity bandEntity = BandEntity.builder()
                .bandId(1)
                .bandName("test band1")
                .bandDetails("test")
                .build();
        TrackEntity trackEntity = TrackEntity.builder()
                .trackId(1)
                .band(bandEntity)
                .trackName("Test track1")
                .build();

        when(trackRepository.existsById(any(Integer.class))).thenReturn(true);
        when(trackRepository.findById(id)).thenReturn(Optional.of(trackEntity));

        Track trackSrc = trackService.getTrackById(id);
        trackSrc.setTrackName(trackSrc.getTrackName() + "#");
        trackSrc.setTrackDetails(trackSrc.getTrackDetails() + "#");
        trackEntity = mapper.trackToTrackEntity(trackSrc);

        when(trackRepository.findById(id)).thenReturn(Optional.of(trackEntity));
        when(trackRepository.save(any(TrackEntity.class))).thenReturn(trackEntity);

        trackService.update(trackSrc);
        Track trackDst = trackService.getTrackById(trackSrc.getTrackId());
        assertEquals(trackSrc.getTrackName(), trackDst.getTrackName());
        assertEquals(trackSrc.getTrackDetails(), trackDst.getTrackDetails());

        verify(trackRepository, times(2)).findById(id);
        verify(trackRepository).existsById(any(Integer.class));
        verify(trackRepository).save(any(TrackEntity.class));

    }

    @Test
    void deleteTest() {
        log.debug("deleteTest()");
        Integer id = 1;
        TrackEntity trackEntity = TrackEntity.builder()
                .trackId(1)
                .trackName("Test track1")
                .build();

        when(trackRepository.findById(id)).thenReturn(Optional.of(trackEntity));
        when(trackRepository.deleteByTrackId(id)).thenReturn(id);

        assertTrue(trackService.delete(id) > 0);

        verify(trackRepository).deleteByTrackId(trackEntity.getTrackId());
        verify(trackRepository).findById(id);

    }

    @Test
    void countTest() {
        log.debug("countTest()");
        int count = 1;

        when(trackRepository.count()).thenReturn(Long.valueOf(count));

        Integer quantity = trackService.count();
        assertNotNull(quantity);
        assertTrue(quantity > 0);
        assertEquals(Integer.valueOf(count), quantity);

        verify(trackRepository).count();
    }

    @Test
    void findAllTracksTest() {
        log.debug("findAllTracksTest()");
        BandEntity band = BandEntity.builder()
                .bandName("test band1")
                .bandDetails("test")
                .build();
        TrackEntity trackEntity = TrackEntity.builder()
                .band(band)
                .trackName("Test track1")
                .build();
        when(trackRepository.findAll()).thenReturn(List.of(trackEntity));

        assertNotNull(trackService);
        List<Track> tracks = trackService.findAllTracks();
        assertNotNull(tracks);
        assertTrue(tracks.size() > 0);

        verify(trackRepository).findAll();
    }

    @Test
    void deleteAllTracksTest() {
        log.debug("deleteAllTracksTest()");

        trackService.deleteAllTracks();

        verify(trackRepository).deleteAll();
        verify(trackRepository).resetStartTrackId();
    }

}