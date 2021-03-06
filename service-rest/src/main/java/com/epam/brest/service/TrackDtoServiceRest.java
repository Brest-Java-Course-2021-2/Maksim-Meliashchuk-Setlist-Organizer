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

import java.time.LocalDate;
import java.util.List;

@Service
public class TrackDtoServiceRest implements TrackDtoService {

    private final Logger logger = LogManager.getLogger(TrackDtoServiceRest.class);

    private final String url;

    private final RestTemplate restTemplate;

    public TrackDtoServiceRest(String url, RestTemplate restTemplate) {
        this.url = url;
        this.restTemplate = restTemplate;
    }

    @Override
    public List<TrackDto> findAllTracksWithBandName() {
        logger.debug("findAllTracksWithBandName()");
        ParameterizedTypeReference<List<TrackDto>> typeReference = new ParameterizedTypeReference<>(){};
        ResponseEntity<List<TrackDto>> responseEntity =
                                            restTemplate.exchange(url, HttpMethod.GET,null, typeReference);
        return responseEntity.getBody();
    }

    @Override
    public List<TrackDto> findAllTracksWithBandNameByBandId(Integer bandId) {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(url)
                .path(String.format("/band/%d", bandId));
        ParameterizedTypeReference<List<TrackDto>> typeReference = new ParameterizedTypeReference<>(){};
        ResponseEntity<List<TrackDto>> responseEntity =
                restTemplate.exchange(uriComponentsBuilder.toUriString(), HttpMethod.GET,null, typeReference);
        return responseEntity.getBody();
    }

    @Override
    public List<TrackDto> findAllTracksWithReleaseDateFilter(LocalDate fromDate, LocalDate toDate) {
        logger.debug("findAllTracksWithReleaseDateFilter()");
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("fromDate", fromDate)
                .queryParam("toDate", toDate);
        ParameterizedTypeReference<List<TrackDto>> typeReference = new ParameterizedTypeReference<>(){};
        ResponseEntity<List<TrackDto>> responseEntity =
                restTemplate.exchange(uriComponentsBuilder.toUriString(), HttpMethod.GET,null, typeReference);
        return responseEntity.getBody();
    }
}
