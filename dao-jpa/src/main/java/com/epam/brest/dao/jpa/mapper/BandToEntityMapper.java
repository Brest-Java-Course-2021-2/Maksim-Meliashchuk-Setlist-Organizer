package com.epam.brest.dao.jpa.mapper;

import com.epam.brest.dao.jpa.entity.BandEntity;
import com.epam.brest.model.Band;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface BandToEntityMapper {

    BandToEntityMapper MAPPER = Mappers.getMapper(BandToEntityMapper.class);

    BandEntity bandToBandEntity(Band band);

    Band bandEntityToBand(BandEntity bandEntity);
}
