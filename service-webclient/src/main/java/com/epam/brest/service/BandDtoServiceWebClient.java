package com.epam.brest.service;

import com.epam.brest.model.BandDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class BandDtoServiceWebClient implements BandDtoService {

    private final Logger logger = LogManager.getLogger(BandDtoServiceWebClient.class);

    private final String url;

    private final WebClient webClient;

    public BandDtoServiceWebClient(String url, WebClient webClient) {
        this.url = url;
        this.webClient = webClient;
    }

    @Override
    public List<BandDto> findAllWithCountTrack() {
        logger.debug("findAllWithCountTrack()");
        ParameterizedTypeReference<List<BandDto>> typeReference = new ParameterizedTypeReference<>(){};
        return webClient
                .get()
                .uri(url)
                .retrieve()
                .bodyToMono(typeReference)
                .block();
    }
}
