package com.epam.brest.service;

import com.epam.brest.model.Track;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class TrackServiceRest implements TrackService {

    private final Logger logger = LogManager.getLogger(TrackServiceRest.class);

    private String url;

    private RestTemplate restTemplate;

    public TrackServiceRest(String url, RestTemplate restTemplate) {
        this.url = url;
        this.restTemplate = restTemplate;
    }


    @Override
    public Track getTrackById(Integer trackId) {
        logger.debug("getTrackById({})", trackId);
        ResponseEntity<Track> responseEntity =
                restTemplate.getForEntity(url + "/" + trackId, Track.class);
        return responseEntity.getBody();
    }

    @Override
    public Integer create(Track track) {
        logger.debug("create({})", track);
        ResponseEntity responseEntity = restTemplate.postForEntity(url, track, Integer.class);
        return (Integer) responseEntity.getBody();
    }

    @Override
    public Integer update(Track track) {
        logger.debug("update({})", track);
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<Track> entity = new HttpEntity<>(track, headers);
        ResponseEntity<Integer> result = restTemplate.exchange(url, HttpMethod.PUT, entity, Integer.class);
        return result.getBody();
    }

    @Override
    public Integer delete(Integer trackId) {
        logger.debug("delete({})", trackId);
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<Track> entity = new HttpEntity<>(headers);
        ResponseEntity<Integer> result =
                restTemplate.exchange(url + "/" + trackId, HttpMethod.DELETE, entity, Integer.class);
        return result.getBody();
    }

    @Override
    public Integer count() {
        logger.debug("count()");
        ResponseEntity<Integer> responseEntity = restTemplate.getForEntity(url + "/count" , Integer.class);
        return responseEntity.getBody();
    }

    @Override
    public List<Track> findAllTracks() {
        logger.debug("findAllTracks()");
        ResponseEntity responseEntity = restTemplate.getForEntity(url, List.class);
        return (List<Track>) responseEntity.getBody();
    }
}
