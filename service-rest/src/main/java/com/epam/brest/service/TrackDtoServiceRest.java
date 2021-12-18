package com.epam.brest.service;

import com.epam.brest.model.dto.TrackDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.util.List;

@Service
public class TrackDtoServiceRest implements TrackDtoService {

    private final Logger logger = LogManager.getLogger(TrackDtoServiceRest.class);

    private String url;

    private RestTemplate restTemplate;

    public TrackDtoServiceRest(String url, RestTemplate restTemplate) {
        this.url = url;
        this.restTemplate = restTemplate;
    }

    @Override
    public List<TrackDto> findAllTracksWithBandName() {
        logger.debug("findAllTracksWithBandName()");
        ResponseEntity responseEntity = restTemplate.getForEntity(url, List.class);
        return (List<TrackDto>) responseEntity.getBody();
    }

    @Override
    public List<TrackDto> findAllTracksWithReleaseDateFilter(LocalDate fromDate, LocalDate toDate) {
        logger.debug("findAllTracksWithReleaseDateFilter()");
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("fromDate", fromDate)
                .queryParam("toDate", toDate);
        ResponseEntity responseEntity = restTemplate.getForEntity(uriComponentsBuilder.toUriString(), List.class);
        return (List<TrackDto>) responseEntity.getBody();
    }
}
