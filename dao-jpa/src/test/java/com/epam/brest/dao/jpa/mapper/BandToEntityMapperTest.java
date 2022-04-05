package com.epam.brest.dao.jpa.mapper;

import com.epam.brest.dao.jpa.entity.BandEntity;
import com.epam.brest.model.Band;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
class BandToEntityMapperTest {

    @Test
    void bandToBandEntity() {
        log.debug("bandToBandEntity()");
        Band band = Band.builder()
                .bandId(1)
                .bandName("Band")
                .bandDetails("Details")
                .build();
        BandEntity bandEntity = BandToEntityMapper.MAPPER.bandToBandEntity(band);
        assertEquals(band.getBandId(),bandEntity.getBandId());
        assertEquals(band.getBandName(),bandEntity.getBandName());
        assertEquals(band.getBandDetails(), bandEntity.getBandDetails());
    }

    @Test
    void bandEntityToBand() {
        log.debug("bandEntityToBand()");
        BandEntity bandEntity = BandEntity.builder()
                .bandId(1)
                .bandName("Band")
                .bandDetails("Details")
                .build();
        Band band = BandToEntityMapper.MAPPER.bandEntityToBand(bandEntity);
        assertEquals(band.getBandId(), bandEntity.getBandId());
        assertEquals(band.getBandName(), bandEntity.getBandName());
        assertEquals(band.getBandDetails(), bandEntity.getBandDetails());
    }
}