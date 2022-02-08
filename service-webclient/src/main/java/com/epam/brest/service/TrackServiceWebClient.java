package com.epam.brest.service;

import com.epam.brest.model.Track;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

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
        return null;
    }

    @Override
    public Integer create(Track track) {
        return null;
    }

    @Override
    public Integer update(Track track) {
        return null;
    }

    @Override
    public Integer delete(Integer trackId) {
        return null;
    }

    @Override
    public Integer count() {
        return null;
    }

    @Override
    public List<Track> findAllTracks() {
        return null;
    }
}
