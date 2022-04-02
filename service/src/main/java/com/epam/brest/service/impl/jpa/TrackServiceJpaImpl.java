package com.epam.brest.service.impl.jpa;

import com.epam.brest.dao.jpa.entity.TrackEntity;
import com.epam.brest.dao.jpa.mapper.TrackToEntityMapper;
import com.epam.brest.dao.jpa.repository.TrackRepository;
import com.epam.brest.model.Track;
import com.epam.brest.service.TrackService;
import com.epam.brest.service.exception.TrackNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
@Profile("jpa")
public class TrackServiceJpaImpl implements TrackService {
    private final TrackRepository trackRepository;
    private final TrackToEntityMapper mapper;

    @Override
    public Track getTrackById(Integer trackId) {
        TrackEntity trackEntity = trackRepository
                .findById(trackId)
                .orElseThrow(() -> new TrackNotFoundException(trackId));
        return mapper.trackEntityToTrack(trackEntity);
    }

    @Override
    public Integer create(Track track) {
        return null;
    }

    @Override
    public Integer update(Track track) {
        return null;
    }

    @Override
    public Integer delete(Integer trackId) {
        return null;
    }

    @Override
    public Integer count() {
        return null;
    }

    @Override
    public List<Track> findAllTracks() {
        Iterable<TrackEntity> iterable = trackRepository.findAll();
        return StreamSupport.stream(iterable.spliterator(), false)
                .map(mapper::trackEntityToTrack)
                .collect(Collectors.toList());
    }
}
