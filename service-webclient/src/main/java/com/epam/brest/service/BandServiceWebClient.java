package com.epam.brest.service;

import com.epam.brest.model.Band;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

public class BandServiceWebClient implements BandService {

    private final Logger logger = LogManager.getLogger(BandServiceWebClient.class);

    private final String url;

    private final WebClient webClient;

    public BandServiceWebClient(String url, WebClient webClient) {
        this.url = url;
        this.webClient = webClient;
    }

    @Override
    public Band getBandById(Integer bandId) {
        logger.debug("findById({})", bandId);
        return webClient
                .get()
                .uri(url + "/" + bandId)
                .retrieve()
                .bodyToMono(Band.class)
                .block();
    }

    @Override
    public List<Band> findAllBands() {
        logger.debug("findAllBands()");
        ParameterizedTypeReference<List<Band>> typeReference = new ParameterizedTypeReference<>() {
        };
        return webClient
                .get()
                .uri(url)
                .retrieve()
                .bodyToMono(typeReference)
                .block();
    }

    @Override
    public Integer create(Band band) {
        logger.debug("create()");
        return webClient
                .post()
                .uri(url)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(Mono.just(band), Band.class)
                .retrieve()
                .bodyToMono(Integer.class)
                .block();
    }

    @Override
    public Integer update(Band band) {
        logger.debug("update()");
        return webClient
                .put()
                .uri(url)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(Mono.just(band), Band.class)
                .retrieve()
                .bodyToMono(Integer.class)
                .block();
    }

    @Override
    public Integer delete(Integer bandId) {
        logger.debug("delete()");
        return webClient
                .delete()
                .uri(url + "/" + bandId)
                .retrieve()
                .bodyToMono(Integer.class)
                .block();
    }

    @Override
    public Integer count() {
        logger.debug("count()");
        return webClient
                .get()
                .uri(url + "/count")
                .retrieve()
                .bodyToMono(Integer.class)
                .block();
    }
}
