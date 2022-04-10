package com.epam.brest.service.impl.jpa;

import com.epam.brest.dao.jpa.entity.BandEntity;
import com.epam.brest.dao.jpa.mapper.BandEntityToDtoMapper;
import com.epam.brest.dao.jpa.repository.BandRepository;
import com.epam.brest.model.BandDto;
import com.epam.brest.service.BandDtoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
@Profile({"jpa"})
@Slf4j
public class BandDtoServiceJpaImpl implements BandDtoService {
    private final BandRepository bandRepository;
    private final BandEntityToDtoMapper mapper;

    @Override
    public List<BandDto> findAllWithCountTrack() {
        log.info("findAllWithCountTrack()");
        Iterable<BandEntity> iterable = bandRepository.findAll();
        return StreamSupport.stream(iterable.spliterator(), false)
                .map(mapper::bandEntityToDto)
                .collect(Collectors.toList());
    }
}
