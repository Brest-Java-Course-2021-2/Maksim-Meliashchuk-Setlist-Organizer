package com.epam.brest.dao.jpa.mapper;

import com.epam.brest.dao.jpa.entity.TrackEntity;
import com.epam.brest.model.Track;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TrackToEntityMapper {
    TrackEntity trackToTrackEntity(Track track);
    Track trackEntityToTrack(TrackEntity trackEntity);
}
