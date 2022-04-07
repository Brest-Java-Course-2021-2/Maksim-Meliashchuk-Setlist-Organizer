package com.epam.brest.dao.jpa.mapper;

import com.epam.brest.dao.jpa.entity.BandEntity;
import com.epam.brest.model.Band;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BandToEntityMapper {

    BandEntity bandToBandEntity(Band band);

    Band bandEntityToBand(BandEntity bandEntity);
}
