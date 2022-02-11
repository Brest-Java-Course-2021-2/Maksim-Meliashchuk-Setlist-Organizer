package com.epam.brest.service.impl;

import com.epam.brest.model.dto.BandDto;
import com.epam.brest.service.BandDtoService;
import com.epam.brest.service.config.BandServiceTestConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@Import({BandServiceTestConfig.class})
@PropertySource({"classpath:sql-band.properties"})
@Transactional
@Rollback
public class BandDtoServiceImplIT {

    private final Logger logger = LogManager.getLogger(BandDtoServiceImplIT.class);

    @Autowired
    private BandDtoService bandDtoService;

    @BeforeEach
    void setUp() {
    }

    @Test
    public void testShouldFindAllWithCountTrack() {
        logger.debug("BandDtoService execute test: testShouldFindAllWithCountTrack()");
        List<BandDto> bands = bandDtoService.findAllWithCountTrack();
        assertNotNull(bands);
        assertTrue(bands.size() > 0);
        assertTrue(bands.get(0).getBandCountTrack() > 0);
    }

}
