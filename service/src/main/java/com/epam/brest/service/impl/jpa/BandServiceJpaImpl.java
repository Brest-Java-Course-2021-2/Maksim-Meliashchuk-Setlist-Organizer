package com.epam.brest.service.impl.jpa;

import com.epam.brest.dao.jpa.entity.BandEntity;
import com.epam.brest.dao.jpa.mapper.BandToEntityMapper;
import com.epam.brest.dao.jpa.repository.BandRepository;
import com.epam.brest.model.Band;
import com.epam.brest.service.BandService;
import com.epam.brest.service.exception.BandNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
@Profile("jpa")
public class BandServiceJpaImpl implements BandService {
    private final BandRepository bandRepository;
    private final BandToEntityMapper mapper;

    @Override
    public Band getBandById(Integer bandId) {
        BandEntity bandEntity = bandRepository
                .findById(bandId)
                .orElseThrow(() -> new BandNotFoundException(bandId));
        return mapper.bandEntityToBand(bandEntity);
    }

    @Override
    public List<Band> findAllBands() {
        Iterable<BandEntity> iterable = bandRepository.findAll();
        return StreamSupport.stream(iterable.spliterator(), false)
                .map(mapper::bandEntityToBand)
                .collect(Collectors.toList());
    }

    @Override
    public Integer create(Band band) {
        BandEntity bandEntity = mapper.bandToBandEntity(band);
        bandRepository.save(bandEntity);
        return bandEntity.getBandId();
    }

    @Override
    public Integer update(Band band) {
        if (!bandRepository.existsById(band.getBandId()))
            throw new BandNotFoundException(band.getBandId());

        BandEntity bandEntity = mapper.bandToBandEntity(band);
        bandRepository.save(bandEntity);
        return 1;
    }

    @Override
    public Integer delete(Integer bandId) {
        BandEntity bandEntity = bandRepository
                .findById(bandId)
                .orElseThrow(() -> new BandNotFoundException(bandId));
        Integer beforeCount = Math.toIntExact(bandRepository.count());
        bandRepository.delete(bandEntity);
        Integer afterCount = Math.toIntExact(bandRepository.count());
        return beforeCount - afterCount;
    }

    @Override
    public Integer count() {
        return Math.toIntExact(bandRepository.count());
    }
}
