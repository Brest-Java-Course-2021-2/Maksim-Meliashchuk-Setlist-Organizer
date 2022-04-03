package com.epam.brest.service.impl.jpa;

import com.epam.brest.dao.jpa.entity.TrackEntity;
import com.epam.brest.dao.jpa.mapper.TrackEntityToDtoMapper;
import com.epam.brest.dao.jpa.repository.TrackRepository;
import com.epam.brest.model.TrackDto;
import com.epam.brest.service.TrackDtoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
@Profile("jpa")
@Slf4j
public class TrackDtoServiceJpaImpl implements TrackDtoService {
    private final TrackRepository trackRepository;
    private final TrackEntityToDtoMapper mapper;

    @Override
    public List<TrackDto> findAllTracksWithBandName() {
        log.info("findAllTracksWithBandName()");
        Iterable<TrackEntity> iterable = trackRepository.findAll();
        return StreamSupport.stream(iterable.spliterator(), false)
                .map(mapper::trackEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<TrackDto> findAllTracksWithBandNameByBandId(Integer bandId) {
        log.info("findAllTracksWithBandNameByBandId({})", bandId);
        Iterable<TrackEntity> iterable = trackRepository.findByBand_BandIdEquals(bandId);
        return StreamSupport.stream(iterable.spliterator(), false)
                .map(mapper::trackEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<TrackDto> findAllTracksWithReleaseDateFilter(LocalDate fromDate, LocalDate toDate) {
        log.info("findAllTracksWithReleaseDateFilter({},{})", fromDate, toDate);

        if (fromDate == null && toDate == null) {
            Iterable<TrackEntity> iterable = trackRepository.findAll();
            return StreamSupport.stream(iterable.spliterator(), false)
                    .map(mapper::trackEntityToDto)
                    .collect(Collectors.toList());
        }
        if (fromDate == null){
            Iterable<TrackEntity> iterable = trackRepository.findByTrackReleaseDateBefore(toDate);
            return StreamSupport.stream(iterable.spliterator(), false)
                    .map(mapper::trackEntityToDto)
                    .collect(Collectors.toList());
        }else if (toDate == null){
            Iterable<TrackEntity> iterable = trackRepository.findByTrackReleaseDateAfter(fromDate);
            return StreamSupport.stream(iterable.spliterator(), false)
                    .map(mapper::trackEntityToDto)
                    .collect(Collectors.toList());
        }else {
            Iterable<TrackEntity> iterable = trackRepository.findByTrackReleaseDateBetween(fromDate, toDate);
            return StreamSupport.stream(iterable.spliterator(), false)
                    .map(mapper::trackEntityToDto)
                    .collect(Collectors.toList());
        }
    }
}
