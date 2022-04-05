package com.epam.brest.dao.jpa.mapper;

import com.epam.brest.dao.jpa.entity.TrackEntity;
import com.epam.brest.model.Track;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TrackToEntityMapper {

    TrackToEntityMapper MAPPER = Mappers.getMapper(TrackToEntityMapper.class);

    TrackEntity trackToTrackEntity(Track track);
    @Mapping(target = "trackBandId", expression = "java(trackEntity.getBand().getBandId())")
    Track trackEntityToTrack(TrackEntity trackEntity);
}
