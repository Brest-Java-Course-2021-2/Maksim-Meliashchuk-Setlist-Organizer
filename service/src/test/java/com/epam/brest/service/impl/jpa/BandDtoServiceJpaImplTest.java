package com.epam.brest.service.impl.jpa;

import com.epam.brest.dao.jpa.entity.BandEntity;
import com.epam.brest.dao.jpa.entity.TrackEntity;
import com.epam.brest.dao.jpa.mapper.BandEntityToDtoMapper;
import com.epam.brest.dao.jpa.mapper.BandEntityToDtoMapperImpl;
import com.epam.brest.dao.jpa.repository.BandRepository;
import com.epam.brest.model.BandDto;
import com.epam.brest.service.BandDtoService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Slf4j
class BandDtoServiceJpaImplTest {

    @Mock
    private BandRepository bandRepository;

    private BandDtoService bandDtoService;

    @BeforeEach
    void initUseCase() {
        BandEntityToDtoMapper mapper = new BandEntityToDtoMapperImpl();
        bandDtoService = new BandDtoServiceJpaImpl(bandRepository, mapper);
    }

    @Test
    void findAllWithCountTrackTest() {
        log.debug("findAllWithCountTrackTest()");
        TrackEntity track1 = TrackEntity.builder()
                .trackName("Test track1")
                .trackDuration(100)
                .build();
        TrackEntity track2 = TrackEntity.builder()
                .trackDuration(200)
                .trackName("Test track2")
                .build();
        BandEntity band1 = BandEntity.builder()
                .bandName("test band1")
                .bandDetails("test")
                .tracks(List.of(track1))
                .build();
        BandEntity band2 = BandEntity.builder()
                .bandName("test band2")
                .bandDetails("test")
                .tracks(List.of(track2))
                .build();
        when(bandRepository.findAll()).thenReturn(Arrays.asList(band1, band2));
        List<BandDto> bands = bandDtoService.findAllWithCountTrack();
        assertNotNull(bands);
        assertTrue(bands.size() > 0);
        assertTrue(bands.get(0).getBandCountTrack() > 0);
    }
}