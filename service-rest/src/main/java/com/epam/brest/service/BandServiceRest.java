package com.epam.brest.service;

import com.epam.brest.model.Band;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class BandServiceRest implements BandService {

    private final Logger logger = LogManager.getLogger(BandServiceRest.class);

    private String url;

    private RestTemplate restTemplate;

    public BandServiceRest(String url, RestTemplate restTemplate) {
        this.url = url;
        this.restTemplate = restTemplate;
    }

    @Override
    public Band getBandById(Integer bandId) {
        logger.debug("findById({})", bandId);
        ResponseEntity<Band> responseEntity =
                restTemplate.getForEntity(url + "/" + bandId, Band.class);
        return responseEntity.getBody();
    }

    @Override
    public List<Band> findAllBands() {
        logger.debug("findAll()");
        ResponseEntity responseEntity = restTemplate.getForEntity(url, List.class);
        return (List<Band>) responseEntity.getBody();
    }

    @Override
    public Integer create(Band band) {
        logger.debug("create({})", band);
        ResponseEntity responseEntity = restTemplate.postForEntity(url, band, Integer.class);
        return (Integer) responseEntity.getBody();
    }

    @Override
    public Integer update(Band band) {
        logger.debug("update({})", band);
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<Band> entity = new HttpEntity<>(band, headers);
        ResponseEntity<Integer> result = restTemplate.exchange(url, HttpMethod.PUT, entity, Integer.class);
        return result.getBody();
    }

    @Override
    public Integer delete(Integer bandId) {
        logger.debug("delete({})", bandId);
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<Band> entity = new HttpEntity<>(headers);
        ResponseEntity<Integer> result =
                restTemplate.exchange(url + "/" + bandId, HttpMethod.DELETE, entity, Integer.class);
        return result.getBody();
    }

    @Override
    public Integer count() {
        logger.debug("count()");
        ResponseEntity<Integer> responseEntity = restTemplate.getForEntity(url + "/count" , Integer.class);
        return responseEntity.getBody();
    }
}
