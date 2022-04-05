package com.epam.brest.dao.jpa.mapper;

import com.epam.brest.dao.jpa.entity.BandEntity;
import com.epam.brest.dao.jpa.entity.TrackEntity;
import com.epam.brest.model.BandDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
class BandEntityToDtoMapperTest {

    @Test
    void bandEntityToDto() {
        log.debug("bandEntityToDto()");
        BandEntity band = BandEntity.builder()
                .bandId(1)
                .bandName("test band")
                .bandDetails("test details")
                .build();
        TrackEntity track1 = TrackEntity.builder()
                .band(band)
                .trackDuration(100)
                .trackName("Test track1")
                .build();
        TrackEntity track2 = TrackEntity.builder()
                .band(band)
                .trackDuration(200)
                .trackName("Test track2")
                .build();
        band.setTracks(Arrays.asList(track1, track2));
        BandDto bandDto = BandEntityToDtoMapper.MAPPER.bandEntityToDto(band);
        assertEquals(bandDto.getBandId(), band.getBandId());
        assertEquals(bandDto.getBandName(), band.getBandName());
        assertEquals(bandDto.getBandDetails(), band.getBandDetails());
        assertEquals(bandDto.getBandCountTrack(), band.getTracks().size());
        assertEquals(bandDto.getBandRepertoireDuration(), track1.getTrackDuration() + track2.getTrackDuration());
    }
}