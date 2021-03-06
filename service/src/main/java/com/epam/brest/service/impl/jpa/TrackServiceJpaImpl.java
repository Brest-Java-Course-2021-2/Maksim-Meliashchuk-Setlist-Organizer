package com.epam.brest.service.impl.jpa;

import com.epam.brest.dao.jpa.entity.TrackEntity;
import com.epam.brest.dao.jpa.mapper.TrackToEntityMapper;
import com.epam.brest.dao.jpa.repository.TrackRepository;
import com.epam.brest.model.Track;
import com.epam.brest.service.TrackService;
import com.epam.brest.service.exception.TrackNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
@Profile("jpa")
@Slf4j
@Transactional(readOnly = true)
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
    @Transactional
    public Integer create(Track track) {
        log.info("create()");
        TrackEntity trackEntity = mapper.trackToTrackEntity(track);
        return trackRepository.save(trackEntity).getTrackId();
    }

    @Override
    @Transactional
    public Integer update(Track track) {
        log.info("update()");
        if (!trackRepository.existsById(track.getTrackId()))
            throw new TrackNotFoundException(track.getTrackId());
        TrackEntity trackEntity = mapper.trackToTrackEntity(track);
        return trackRepository.save(trackEntity).getTrackId();
    }

    @Override
    @Transactional
    public Integer delete(Integer trackId) {
        log.info("getTrackById({})", trackId);
        TrackEntity trackEntity = trackRepository
                .findById(trackId)
                .orElseThrow(() -> new TrackNotFoundException(trackId));
        return trackRepository.deleteByTrackId(trackEntity.getTrackId());
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

    @Override
    @Transactional
    public void deleteAllTracks() {
        log.info("deleteAllTracks()");
        trackRepository.deleteAll();
        trackRepository.resetStartTrackId();
    }
}
