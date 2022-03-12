package com.epam.brest.service;

import com.epam.brest.model.BandDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class BandDtoFakerServiceWebClient implements BandDtoFakerService {

    private final Logger logger = LogManager.getLogger(BandDtoFakerServiceWebClient.class);

    private final String url;

    private final WebClient webClient;

    public BandDtoFakerServiceWebClient(String url, WebClient webClient) {
        this.url = url;
        this.webClient = webClient;
    }

    @Override
    public List<BandDto> fillFakeBandsDto(Integer size, String language) {
        logger.debug("fillFakeBandsDto({},{})", size, language);
        ParameterizedTypeReference<List<BandDto>> typeReference = new ParameterizedTypeReference<>() {};
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
