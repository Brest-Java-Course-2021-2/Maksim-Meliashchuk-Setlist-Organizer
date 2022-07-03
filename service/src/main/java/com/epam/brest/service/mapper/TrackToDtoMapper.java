package com.epam.brest.service.mapper;

import com.epam.brest.model.Track;
import com.epam.brest.model.TrackDto;
import com.epam.brest.service.BandService;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
@RequiredArgsConstructor
public abstract class TrackToDtoMapper {

    @Autowired
    protected BandService bandService;

    @Mapping(target = "trackBandName", expression = "java(bandService.getBandById(track.getTrackBandId()).getBandName())")
    public abstract TrackDto trackToDto (Track track);

}
