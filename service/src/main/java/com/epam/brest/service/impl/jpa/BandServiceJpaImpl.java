package com.epam.brest.service.impl.jpa;

import com.epam.brest.dao.jpa.entity.BandEntity;
import com.epam.brest.dao.jpa.mapper.BandToEntityMapper;
import com.epam.brest.dao.jpa.repository.BandRepository;
import com.epam.brest.model.Band;
import com.epam.brest.service.BandService;
import com.epam.brest.service.exception.BandNotFoundException;
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
public class BandServiceJpaImpl implements BandService {
    private final BandRepository bandRepository;
    private final BandToEntityMapper mapper;

    @Override
    public Band getBandById(Integer bandId) {
        log.info("getBandById({})", bandId);
        BandEntity bandEntity = bandRepository
                .findById(bandId)
                .orElseThrow(() -> new BandNotFoundException(bandId));
        return mapper.bandEntityToBand(bandEntity);
    }

    @Override
    public List<Band> findAllBands() {
        log.info("findAllBands()");
        Iterable<BandEntity> iterable = bandRepository.findAll();
        return StreamSupport.stream(iterable.spliterator(), false)
                .map(mapper::bandEntityToBand)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Integer create(Band band) {
        log.info("create()");
        BandEntity bandEntity = mapper.bandToBandEntity(band);
        return bandRepository.save(bandEntity).getBandId();
    }

    @Override
    @Transactional
    public Integer update(Band band) {
        log.info("update()");
        if (!bandRepository.existsById(band.getBandId()))
            throw new BandNotFoundException(band.getBandId());
        BandEntity bandEntity = mapper.bandToBandEntity(band);
        return bandRepository.save(bandEntity).getBandId();
    }

    @Override
    @Transactional
    public Integer delete(Integer bandId) {
        log.info("delete({})", bandId);
        BandEntity bandEntity = bandRepository
                .findById(bandId)
                .orElseThrow(() -> new BandNotFoundException(bandId));
        return bandRepository.deleteByBandId(bandEntity.getBandId());
    }

    @Override
    public Integer count() {
        log.info("count()");
        return Math.toIntExact(bandRepository.count());
    }

    @Override
    @Transactional
    public void deleteAllBands() {
        log.info("deleteAllBands()");
        bandRepository.deleteAll();
        bandRepository.resetStartBandId();
    }
}
