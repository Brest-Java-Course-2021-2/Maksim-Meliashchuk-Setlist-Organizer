package com.epam.brest.service.impl;

import com.epam.brest.model.dto.BandDto;
import com.epam.brest.service.BandDtoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath*:test-db.xml", "classpath*:service-context-test.xml"})
@Transactional
public class BandDtoServiceImplIT {
    @Autowired
    BandDtoService bandDtoService;

    @Test
    public void shouldFindAllWithCountTrack() {
        List<BandDto> bands = bandDtoService.findAllWithCountTrack();
        assertNotNull(bands);
        assertTrue(bands.size() > 0);
        assertTrue(bands.get(0).getCountTrack() > 0);
    }

}
