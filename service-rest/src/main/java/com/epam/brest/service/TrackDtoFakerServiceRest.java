package com.epam.brest.service;

import com.epam.brest.model.TrackDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Service
public class TrackDtoFakerServiceRest implements TrackDtoFakerService {

    private final Logger logger = LogManager.getLogger(BandDtoFakerServiceRest.class);

    private final String url;

    private final RestTemplate restTemplate;

    public TrackDtoFakerServiceRest(String url, RestTemplate restTemplate) {
        this.url = url;
        this.restTemplate = restTemplate;
    }

    @Override
    public List<TrackDto> fillFakeTracksDto(Integer size, String language) {
        logger.debug("fillFakeTracksDto({},{})", size, language);
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("size", size)
                .queryParam("language", language);
        ParameterizedTypeReference<List<TrackDto>> typeReference = new ParameterizedTypeReference<>(){};
        ResponseEntity<List<TrackDto>> responseEntity = restTemplate
                .exchange(uriComponentsBuilder.toUriString(), HttpMethod.GET,null, typeReference);
        return responseEntity.getBody();
    }
}
