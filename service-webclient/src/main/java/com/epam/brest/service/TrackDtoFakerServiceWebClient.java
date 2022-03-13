package com.epam.brest.service;

import com.epam.brest.model.TrackDto;
import com.epam.brest.service.faker.TrackDtoFakerService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class TrackDtoFakerServiceWebClient implements TrackDtoFakerService {

    private final Logger logger = LogManager.getLogger(TrackDtoFakerServiceWebClient.class);

    private final String url;

    private final WebClient webClient;

    public TrackDtoFakerServiceWebClient(String url, WebClient webClient) {
        this.url = url;
        this.webClient = webClient;
    }

    @Override
    public List<TrackDto> fillFakeTracksDto(Integer size, String language) {
        logger.debug("fillFakeTracksDto({},{})", size, language);
        ParameterizedTypeReference<List<TrackDto>> typeReference = new ParameterizedTypeReference<>(){};
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(url)
                        .queryParam("size", size)
                        .queryParam("language", language)
                        .build())
                .retrieve()
                .bodyToMono(typeReference)
                .block();
    }
}
