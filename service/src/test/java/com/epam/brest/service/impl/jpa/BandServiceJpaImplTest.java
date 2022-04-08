package com.epam.brest.service.impl.jpa;

import com.epam.brest.dao.jpa.entity.BandEntity;
import com.epam.brest.dao.jpa.mapper.BandToEntityMapper;
import com.epam.brest.dao.jpa.mapper.BandToEntityMapperImpl;
import com.epam.brest.dao.jpa.repository.BandRepository;
import com.epam.brest.model.Band;
import com.epam.brest.service.BandService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Slf4j
class BandServiceJpaImplTest {

    @Mock
    private BandRepository bandRepository;

    private BandService bandService;

    private BandToEntityMapper mapper;

    @BeforeEach
    void initUseCase() {
        mapper = new BandToEntityMapperImpl();
        bandService = new BandServiceJpaImpl(bandRepository, mapper);
    }

    @Test
    void getBandByIdTest() {
        log.debug("getBandByIdTest()");
        Integer id = 1;
        BandEntity band = BandEntity.builder()
                .bandId(id)
                .bandName("test band1")
                .bandDetails("test")
                .build();

        when(bandRepository.findById(id)).thenReturn(Optional.of(band));

        Band bandExtracted = bandService.getBandById(id);
        assertEquals(band.getBandName().toUpperCase(), bandExtracted.getBandName().toUpperCase());
        assertEquals(band.getBandDetails(), bandExtracted.getBandDetails());

        verify(bandRepository).findById(id);
    }

    @Test
    void findAllBandsTest() {
        log.debug("findAllBandsTest()");
        BandEntity band = BandEntity.builder()
                .bandName("test band1")
                .bandDetails("test")
                .build();

        when(bandRepository.findAll()).thenReturn(List.of(band));

        assertNotNull(bandService);
        List<Band> bands = bandService.findAllBands();
        assertNotNull(bands);
        assertTrue(bands.size() > 0);

        verify(bandRepository).findAll();
    }

    @Test
    void createBandTest() {
        log.debug("createBandTest()");
        Integer id = 1;
        BandEntity bandEntity = BandEntity.builder()
                .bandId(id)
                .bandName("test band1")
                .bandDetails("test")
                .build();

        when(bandRepository.save(any(BandEntity.class))).thenReturn(bandEntity);

        assertNotNull(bandService);
        Integer bandsSizeBefore = bandService.count();
        assertNotNull(bandsSizeBefore);
        Band band = Band.builder()
                .bandId(2)
                .bandName("P.O.D.")
                .build();
        Integer newBandId = bandService.create(band);
        assertEquals(newBandId, band.getBandId());

        verify(bandRepository).save(any(BandEntity.class));
    }

    @Test
    void updateTest() {
        log.debug("updateTest()");
        assertNotNull(bandService);
        Integer id = 1;
        BandEntity bandEntity = BandEntity.builder()
                .bandId(id)
                .bandName("test band1")
                .bandDetails("test")
                .build();

        when(bandRepository.existsById(any(Integer.class))).thenReturn(true);
        when(bandRepository.findById(id)).thenReturn(Optional.of(bandEntity));

        Band bandSrc = bandService.getBandById(id);
        bandSrc.setBandName(bandSrc.getBandName() + "#");
        bandSrc.setBandDetails(bandSrc.getBandDetails() + "#");
        bandEntity = mapper.bandToBandEntity(bandSrc);

        when(bandRepository.findById(id)).thenReturn(Optional.of(bandEntity));
        when(bandRepository.save(any(BandEntity.class))).thenReturn(bandEntity);

        bandService.update(bandSrc);
        Band bandDst = bandService.getBandById(bandSrc.getBandId());
        assertEquals(bandSrc.getBandName(), bandDst.getBandName());
        assertEquals(bandSrc.getBandDetails(), bandDst.getBandDetails());

        verify(bandRepository, times(2)).findById(id);
        verify(bandRepository).existsById(any(Integer.class));
        verify(bandRepository).save(any(BandEntity.class));
    }

    @Test
    void deleteTest() {
        log.debug("deleteTest()");
        Integer id = 1;
        BandEntity bandEntity = BandEntity.builder()
                .bandId(id)
                .bandName("test band1")
                .bandDetails("test")
                .build();

        when(bandRepository.findById(id)).thenReturn(Optional.of(bandEntity));
        when(bandRepository.count()).thenReturn(Long.valueOf(1));

        bandService.delete(id);

        verify(bandRepository).delete(bandEntity);
        verify(bandRepository).findById(id);
        verify(bandRepository, times(2)).count();
    }

    @Test
    void countTest() {
        log.debug("countTest()");
        int count = 1;

        when(bandRepository.count()).thenReturn(Long.valueOf(count));

        assertNotNull(bandService);
        Integer quantity = bandService.count();
        assertNotNull(quantity);
        assertTrue(quantity > 0);
        assertEquals(Integer.valueOf(count), quantity);

        verify(bandRepository).count();

    }
}