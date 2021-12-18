package com.epam.brest.service;

import com.epam.brest.model.dto.BandDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class BandDtoServiceRest implements BandDtoService {

    private final Logger logger = LogManager.getLogger(BandDtoServiceRest.class);

    private String url;

    private RestTemplate restTemplate;

    public BandDtoServiceRest(String url, RestTemplate restTemplate) {
        this.url = url;
        this.restTemplate = restTemplate;
    }

    @Override
    public List<BandDto> findAllWithCountTrack() {
        logger.debug("findAllWithCountTrack()");
        ResponseEntity responseEntity = restTemplate.getForEntity(url, List.class);
        return (List<BandDto>) responseEntity.getBody();
    }
}
