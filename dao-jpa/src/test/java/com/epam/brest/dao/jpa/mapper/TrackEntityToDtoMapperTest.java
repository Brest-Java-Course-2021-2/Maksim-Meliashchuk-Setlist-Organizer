package com.epam.brest.dao.jpa.mapper;

import com.epam.brest.dao.jpa.entity.BandEntity;
import com.epam.brest.dao.jpa.entity.TrackEntity;
import com.epam.brest.model.TrackDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
class TrackEntityToDtoMapperTest {

    @Test
    void trackEntityToDto() {
        log.debug("trackEntityToDto()");
        BandEntity band = BandEntity.builder()
                .bandId(1)
                .bandName("test band")
                .bandDetails("test details")
                .build();
        TrackEntity track = TrackEntity.builder()
                .band(band)
                .trackName("Test track")
                .trackDetails("details")
                .trackDuration(100)
                .trackReleaseDate(LocalDate.parse("2020-01-01"))
                .trackLink("https://yputube.com/testing")
                .trackTempo(120)
                .trackId(1)
                .build();
        TrackDto trackDto = TrackEntityToDtoMapper.MAPPER.trackEntityToDto(track);
        assertEquals(track.getTrackId(), trackDto.getTrackId());
        assertEquals(track.getTrackName(), trackDto.getTrackName());
        assertEquals(track.getTrackDetails(), trackDto.getTrackDetails());
        assertEquals(track.getTrackTempo(), trackDto.getTrackTempo());
        assertEquals(track.getTrackDuration(), trackDto.getTrackDuration());
        assertEquals(track.getTrackReleaseDate(), trackDto.getTrackReleaseDate());
        assertEquals(track.getTrackLink(), trackDto.getTrackLink());
        assertEquals(track.getBand().getBandId(), trackDto.getTrackBandId());
        assertEquals(track.getBand().getBandName(), trackDto.getTrackBandName());

    }
}