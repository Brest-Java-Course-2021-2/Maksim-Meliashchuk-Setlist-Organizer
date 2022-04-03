package com.epam.brest.dao.jpa.mapper;

import com.epam.brest.dao.jpa.entity.TrackEntity;
import com.epam.brest.model.Track;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TrackToEntityMapper {
    TrackEntity trackToTrackEntity(Track track);
    @Mapping(target = "trackBandId", expression = "java(trackEntity.getBand().getBandId())")
    Track trackEntityToTrack(TrackEntity trackEntity);
}
