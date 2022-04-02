package com.epam.brest.service.impl.jpa;

import com.epam.brest.dao.jpa.repository.BandRepository;
import com.epam.brest.model.BandDto;
import com.epam.brest.service.BandDtoService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Profile({"jpa"})
public class BandDtoServiceJpaImpl implements BandDtoService {
    private final BandRepository bandRepository;

    @Override
    public List<BandDto> findAllWithCountTrack() {
        return null;
    }
}
