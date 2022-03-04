package com.epam.brest.openapi.delegateimpl;

import com.epam.brest.model.TrackDto;
import com.epam.brest.openapi.api.TracksDtoApiDelegate;
import com.epam.brest.service.TrackDtoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TracksDtoDelegateImpl implements TracksDtoApiDelegate {

    private static final Logger LOGGER = LoggerFactory.getLogger(TracksDtoDelegateImpl.class);

    private final TrackDtoService trackDtoService;

    public TracksDtoDelegateImpl(TrackDtoService trackDtoService) {
        this.trackDtoService = trackDtoService;
    }

    @Override
    public ResponseEntity<List<TrackDto>> findAllTracksWithBandName() {
        LOGGER.debug("findAllTracksWithBandName()");
        List<TrackDto> resultList = trackDtoService.findAllTracksWithBandName();
        return new ResponseEntity<>(resultList, HttpStatus.OK);
    }
}
