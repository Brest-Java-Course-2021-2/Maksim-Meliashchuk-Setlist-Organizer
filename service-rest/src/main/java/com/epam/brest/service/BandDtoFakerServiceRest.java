package com.epam.brest.service;

import com.epam.brest.model.BandDto;
import com.epam.brest.service.faker.BandDtoFakerService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Service
public class BandDtoFakerServiceRest implements BandDtoFakerService {

    private final Logger logger = LogManager.getLogger(BandDtoFakerServiceRest.class);

    private final String url;

    private final RestTemplate restTemplate;

    public BandDtoFakerServiceRest(String url, RestTemplate restTemplate) {
        this.url = url;
        this.restTemplate = restTemplate;
    }

    @Override
    public List<BandDto> fillFakeBandsDto(Integer size, String language) {
        logger.debug("fillFakeBandsDto({},{})", size, language);
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("size", size)
                .queryParam("language", language);
        ParameterizedTypeReference<List<BandDto>> typeReference = new ParameterizedTypeReference<>(){};
        ResponseEntity<List<BandDto>> responseEntity = restTemplate
                .exchange(uriComponentsBuilder.toUriString(), HttpMethod.GET,null, typeReference);
        return responseEntity.getBody();
    }
}
