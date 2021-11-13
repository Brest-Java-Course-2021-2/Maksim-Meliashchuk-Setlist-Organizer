package com.epam.brest.service.impl;

import com.epam.brest.dao.BandDtoDao;
import com.epam.brest.model.dto.BandDto;
import com.epam.brest.service.BandDtoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BandDtoServiceImpl implements BandDtoService {

    private final BandDtoDao bandDtoDao;

    public BandDtoServiceImpl(BandDtoDao bandDtoDao) {
        this.bandDtoDao = bandDtoDao;
    }

    @Override
    public List<BandDto> findAllWithCountTrack() {
        return bandDtoDao.findAllWithCountTrack();
    }

}
