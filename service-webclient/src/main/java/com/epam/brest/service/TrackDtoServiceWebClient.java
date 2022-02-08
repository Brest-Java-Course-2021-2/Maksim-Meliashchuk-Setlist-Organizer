package com.epam.brest.service;

import com.epam.brest.model.dto.TrackDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.util.List;

@Service
public class TrackDtoServiceWebClient implements TrackDtoService {

    private final Logger logger = LogManager.getLogger(TrackDtoServiceWebClient.class);

    private final String url;

    private final WebClient webClient;

    public TrackDtoServiceWebClient(String url, WebClient webClient) {
        this.url = url;
        this.webClient = webClient;
    }

    @Override
    public List<TrackDto> findAllTracksWithBandName() {
        logger.debug("findAllTracksWithBandName()");
        ParameterizedTypeReference<List<TrackDto>> typeReference = new ParameterizedTypeReference<>(){};
        return webClient
                .get()
                .uri(url)
                .retrieve()
                .bodyToMono(typeReference)
                .block();
    }

    @Override
    public List<TrackDto> findAllTracksWithBandNameByBandId(Integer bandId) {
        logger.debug("findAllTracksWithBandNameByBandId({})", bandId);
        ParameterizedTypeReference<List<TrackDto>> typeReference = new ParameterizedTypeReference<>() {};
        return webClient
                .get()
                .uri(url + "/band/" + bandId)
                .retrieve()
                .bodyToMono(typeReference)
                .block();
    }

    @Override
    public List<TrackDto> findAllTracksWithReleaseDateFilter(LocalDate fromDate, LocalDate toDate) {
        logger.debug("findAllTracksWithReleaseDateFilter()");
        ParameterizedTypeReference<List<TrackDto>> typeReference = new ParameterizedTypeReference<>() {};
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(url)
                        .queryParam("fromDate", fromDate)
                        .queryParam("toDate", toDate)
                        .build())
                .retrieve()
                .bodyToMono(typeReference)
                .block();
    }

}
