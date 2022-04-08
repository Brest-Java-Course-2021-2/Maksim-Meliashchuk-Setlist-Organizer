package com.epam.brest.service.impl.jpa;

import com.epam.brest.dao.jpa.entity.TrackEntity;
import com.epam.brest.dao.jpa.mapper.TrackToEntityMapper;
import com.epam.brest.dao.jpa.repository.TrackRepository;
import com.epam.brest.model.Track;
import com.epam.brest.service.TrackService;
import com.epam.brest.service.exception.BandNotFoundException;
import com.epam.brest.service.exception.TrackNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
@Profile("jpa")
@Slf4j
public class TrackServiceJpaImpl implements TrackService {
    private final TrackRepository trackRepository;
    private final TrackToEntityMapper mapper;

    @Override
    public Track getTrackById(Integer trackId) {
        log.info("getTrackById({})", trackId);
        TrackEntity trackEntity = trackRepository
                .findById(trackId)
                .orElseThrow(() -> new TrackNotFoundException(trackId));
        return mapper.trackEntityToTrack(trackEntity);
    }

    @Override
    public Integer create(Track track) {
        log.info("create()");
        TrackEntity trackEntity = mapper.trackToTrackEntity(track);
        trackRepository.save(trackEntity);
        return trackEntity.getTrackId();
    }

    @Override
    public Integer update(Track track) {
        log.info("update()");
        Integer result = 1;
        if (!trackRepository.existsById(track.getTrackId()))
            throw new BandNotFoundException(track.getTrackId());
        TrackEntity trackEntity = mapper.trackToTrackEntity(track);
        trackRepository.save(trackEntity);
        return result;
    }

    @Override
    public Integer delete(Integer trackId) {
        log.info("getTrackById({})", trackId);
        TrackEntity trackEntity = trackRepository
                .findById(trackId)
                .orElseThrow(() -> new BandNotFoundException(trackId));
        Integer beforeCount = Math.toIntExact(trackRepository.count());
        trackRepository.delete(trackEntity);
        Integer afterCount = Math.toIntExact(trackRepository.count());
        return beforeCount - afterCount;
    }

    @Override
    public Integer count() {
        log.info("count()");
        return Math.toIntExact(trackRepository.count());
    }

    @Override
    public List<Track> findAllTracks() {
        log.info("findAllTracks()");
        Iterable<TrackEntity> iterable = trackRepository.findAll();
        return StreamSupport.stream(iterable.spliterator(), false)
                .map(mapper::trackEntityToTrack)
                .collect(Collectors.toList());
    }
}
