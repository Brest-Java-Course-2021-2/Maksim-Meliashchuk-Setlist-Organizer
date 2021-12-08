package com.epam.brest.rest;

import com.epam.brest.dao.BandDtoDaoJdbcImpl;
import com.epam.brest.model.dto.BandDto;
import com.epam.brest.service.BandDtoService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;


@RestController
public class BandDtoController {

    private static final Logger logger = LogManager.getLogger(BandDtoDaoJdbcImpl.class);

    private final BandDtoService bandDtoService;

    public BandDtoController(BandDtoService bandDtoService) {
        this.bandDtoService = bandDtoService;
    }

    @GetMapping(value = "/bands_dto")
    public final Collection<BandDto> getBandById(@PathVariable Integer id) {
        logger.debug("band()");
        return bandDtoService.findAllWithCountTrack();
    }
}