package com.epam.brest.dao.jpa.mapper;

import com.epam.brest.dao.jpa.entity.BandEntity;
import com.epam.brest.dao.jpa.entity.TrackEntity;
import com.epam.brest.model.BandDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", imports = TrackEntity.class)
public interface BandEntityToDtoMapper {

    BandEntityToDtoMapper MAPPER = Mappers.getMapper(BandEntityToDtoMapper.class);

    @Mapping(target = "bandCountTrack", expression = "java(bandEntity.getTracks().size())")
    @Mapping(target = "bandRepertoireDuration",
            expression = "java(bandEntity.getTracks().stream().mapToInt(TrackEntity::getTrackDuration).sum())")
    BandDto bandEntityToDto(BandEntity bandEntity);
}
