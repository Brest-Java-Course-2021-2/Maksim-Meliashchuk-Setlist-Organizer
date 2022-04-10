package com.epam.brest.service.impl.jpa;

import com.epam.brest.dao.jpa.entity.BandEntity;
import com.epam.brest.dao.jpa.entity.TrackEntity;
import com.epam.brest.dao.jpa.mapper.TrackEntityToDtoMapper;
import com.epam.brest.dao.jpa.mapper.TrackEntityToDtoMapperImpl;
import com.epam.brest.dao.jpa.repository.TrackRepository;
import com.epam.brest.model.TrackDto;
import com.epam.brest.service.TrackDtoService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Slf4j
class TrackDtoServiceJpaImplTest {

    @Mock
    private TrackRepository trackRepository;

    private TrackDtoService trackDtoService;

    @Captor
    private ArgumentCaptor<LocalDate> localDateArgumentCaptor;

    @BeforeEach
    void initUseCase() {
        TrackEntityToDtoMapper mapper = new TrackEntityToDtoMapperImpl();
        trackDtoService = new TrackDtoServiceJpaImpl(trackRepository, mapper);
    }

    @Test
    void findAllTracksWithBandNameTest() {
        log.debug("findAllTracksWithBandNameTest()");
        BandEntity band = BandEntity.builder()
                .bandName("test band")
                .bandDetails("test")
                .build();
        TrackEntity track1 = TrackEntity.builder()
                .band(band)
                .trackName("Test track1")
                .trackDuration(100)
                .build();
        TrackEntity track2 = TrackEntity.builder()
                .band(band)
                .trackDuration(200)
                .trackName("Test track2")
                .build();

        when(trackRepository.findAll()).thenReturn(List.of(track1, track2));

        List<TrackDto> tracks = trackDtoService.findAllTracksWithBandName();

        assertNotNull(tracks);
        assertTrue(tracks.size() > 0);

        verify(trackRepository).findAll();
    }

    @Test
    void findAllTracksWithBandNameByBandId() {
        log.debug("findAllTracksWithBandNameByBandId()");
        BandEntity band = BandEntity.builder()
                .bandId(1)
                .bandName("test band")
                .bandDetails("test")
                .build();
        TrackEntity track1 = TrackEntity.builder()
                .band(band)
                .trackName("Test track1")
                .trackDuration(100)
                .build();
        TrackEntity track2 = TrackEntity.builder()
                .band(band)
                .trackDuration(200)
                .trackName("Test track2")
                .build();

        when(trackRepository.findByBand_BandIdEquals(any(Integer.class))).thenReturn(List.of(track1, track2));

        List<TrackDto> tracks = trackDtoService.findAllTracksWithBandNameByBandId(band.getBandId());

        assertNotNull(tracks);
        assertTrue(tracks.size() > 0);

        verify(trackRepository).findByBand_BandIdEquals(any(Integer.class));
    }

    @Test
    void findAllTracksWithReleaseDateFilter() {
        log.debug("findAllTracksWithReleaseDateFilter()");
        LocalDate from = LocalDate.parse("2020-01-01");
        LocalDate to = LocalDate.parse("2021-01-01");
        BandEntity band1 = BandEntity.builder()
                .bandId(1)
                .bandName("test band1")
                .build();
        BandEntity band2 = BandEntity.builder()
                .bandId(2)
                .bandName("test band2")
                .build();
        TrackEntity track1 = TrackEntity.builder()
                .band(band1)
                .trackReleaseDate(LocalDate.parse("2019-02-02"))
                .trackName("Test track1")
                .build();
        TrackEntity track2 = TrackEntity.builder()
                .band(band2)
                .trackReleaseDate(LocalDate.parse("2020-02-02"))
                .trackName("Test track2")
                .build();

        when(trackRepository.findByTrackReleaseDateBetween(localDateArgumentCaptor.capture(), localDateArgumentCaptor.capture())).thenReturn(List.of(track1, track2));
        when(trackRepository.findByTrackReleaseDateBefore(localDateArgumentCaptor.capture())).thenReturn(List.of(track1, track2));
        when(trackRepository.findByTrackReleaseDateAfter(localDateArgumentCaptor.capture())).thenReturn(List.of(track1, track2));
        when(trackRepository.findAll()).thenReturn(List.of(track1, track2));

        List<TrackDto> tracks = trackDtoService.findAllTracksWithReleaseDateFilter(from, to);
        List<TrackDto> tracks1 = trackDtoService.findAllTracksWithReleaseDateFilter(to, null);
        List<TrackDto> tracks2 = trackDtoService.findAllTracksWithReleaseDateFilter(null, from);
        List<TrackDto> tracks3 = trackDtoService.findAllTracksWithReleaseDateFilter(null, null);

        assertTrue(tracks.size() > 0);
        assertTrue(tracks1.size() > 0);
        assertTrue(tracks2.size() > 0);
        assertTrue(tracks3.size() > 0);

        verify(trackRepository).findByTrackReleaseDateBetween(localDateArgumentCaptor.capture(),
                localDateArgumentCaptor.capture());
        verify(trackRepository).findByTrackReleaseDateBefore(localDateArgumentCaptor.capture());
        verify(trackRepository).findByTrackReleaseDateAfter(localDateArgumentCaptor.capture());
        verify(trackRepository).findAll();

    }
}