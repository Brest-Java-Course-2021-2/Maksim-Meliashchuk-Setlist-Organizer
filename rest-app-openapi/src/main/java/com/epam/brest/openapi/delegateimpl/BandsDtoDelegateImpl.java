package com.epam.brest.openapi.delegateimpl;

import com.epam.brest.model.BandDto;
import com.epam.brest.openapi.api.BandsDtoApiDelegate;
import com.epam.brest.service.BandDtoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BandsDtoDelegateImpl implements BandsDtoApiDelegate {

    private static final Logger LOGGER = LoggerFactory.getLogger(BandsDtoDelegateImpl.class);

    private final BandDtoService bandDtoService;

    public BandsDtoDelegateImpl(BandDtoService bandDtoService) {
        this.bandDtoService = bandDtoService;
    }

    @Override
    public ResponseEntity<List<BandDto>> bandsDto() {
        LOGGER.debug("bandsDto()");
        List<BandDto> resultList = bandDtoService.findAllWithCountTrack();
        return new ResponseEntity<>(resultList, HttpStatus.OK);
    }

}
