package com.epam.brest.service;

import com.epam.brest.model.Track;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class TrackServiceWebClient implements TrackService {

    private final Logger logger = LogManager.getLogger(TrackServiceWebClient.class);

    private final String url;

    private final WebClient webClient;

    public TrackServiceWebClient(String url, WebClient webClient) {
        this.url = url;
        this.webClient = webClient;
    }

    @Override
    public Track getTrackById(Integer trackId) {
        logger.debug("getTrackById({})", trackId);
        return webClient
                .get()
                .uri(url + "/" + trackId)
                .retrieve()
                .bodyToMono(Track.class)
                .block();
    }

    @Override
    public Integer create(Track track) {
        logger.debug("create()");
        return webClient
                .post()
                .uri(url)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(Mono.just(track), Track.class)
                .retrieve()
                .bodyToMono(Integer.class)
                .block();
    }

    @Override
    public Integer update(Track track) {
        logger.debug("update()");
        return webClient
                .put()
                .uri(url)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(Mono.just(track), Track.class)
                .retrieve()
                .bodyToMono(Integer.class)
                .block();
    }

    @Override
    public Integer delete(Integer trackId) {
        logger.debug("delete()");
        return webClient
                .delete()
                .uri(url + "/" + trackId)
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

    @Override
    public List<Track> findAllTracks() {
        logger.debug("findAllTracks()");
        ParameterizedTypeReference<List<Track>> typeReference = new ParameterizedTypeReference<>() {};
        return webClient
                .get()
                .uri(url)
                .retrieve()
                .bodyToMono(typeReference)
                .block();
    }

    @Override
    public void deleteAllTracks() {

    }
}
