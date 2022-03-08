package com.epam.brest.service;

import com.epam.brest.model.BandDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
//TODO Faker bands&data resttemplate
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
        ParameterizedTypeReference<List<BandDto>> typeReference = new ParameterizedTypeReference<>(){};
        ResponseEntity<List<BandDto>> responseEntity = restTemplate.exchange(url, HttpMethod.GET,null, typeReference);
        return responseEntity.getBody();
    }
}
