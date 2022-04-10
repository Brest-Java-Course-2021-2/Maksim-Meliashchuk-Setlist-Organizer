package com.epam.brest.dao.jpa.mapper;

import com.epam.brest.dao.jpa.entity.BandEntity;
import com.epam.brest.dao.jpa.entity.TrackEntity;
import com.epam.brest.dao.jpa.repository.BandRepository;
import com.epam.brest.model.Track;
import lombok.RequiredArgsConstructor;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
@RequiredArgsConstructor
public abstract class TrackToEntityMapper {

    @Autowired
    protected BandRepository bandRepository;

    public abstract TrackEntity trackToTrackEntity(Track track);

    @Mapping(target = "trackBandId", expression = "java(trackEntity.getBand().getBandId())")
    public abstract Track trackEntityToTrack(TrackEntity trackEntity);

    @AfterMapping
    public void setBand(Track track, @MappingTarget TrackEntity.TrackEntityBuilder trackEntity) {
        trackEntity.band(bandRepository.findById(track.getTrackBandId()).orElse(new BandEntity()));
    }
}