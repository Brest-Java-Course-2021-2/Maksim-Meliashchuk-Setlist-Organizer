package com.epam.brest.dao.jpa.mapper;

import com.epam.brest.dao.jpa.entity.TrackEntity;
import com.epam.brest.model.TrackDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TrackEntityToDtoMapper {

    TrackEntityToDtoMapper MAPPER = Mappers.getMapper(TrackEntityToDtoMapper.class);

    @Mapping(target = "trackBandId", expression = "java(trackEntity.getBand().getBandId())")
    @Mapping(target = "trackBandName", expression = "java(trackEntity.getBand().getBandName())")
    TrackDto trackEntityToDto(TrackEntity trackEntity);
}
