package com.epam.brest.service;

import com.epam.brest.model.BandDto;
import com.epam.brest.service.faker.BandDtoFakerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BandDtoFakerServiceWebClientTest {

    private final Logger logger = LogManager.getLogger(BandDtoServiceWebClientTest.class);

    private BandDtoFakerService bandDtoFakerService;

    public static MockWebServer mockWebServer;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeAll
    static void setUp() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
    }

    @AfterAll
    static void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

    @BeforeEach
    void init() {
        String baseUrl = String.format("http://localhost:%s", mockWebServer.getPort());
        bandDtoFakerService = new BandDtoFakerServiceWebClient("/bands_dto/fill", WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build());
    }
    @Test
    void fillFakeBandsDto() throws Exception {
        logger.debug("findAllWithCountTrack()");
        Integer size = 2;

        mockWebServer.enqueue(new MockResponse().setResponseCode(200)
                .setBody(objectMapper.writeValueAsString(Arrays.asList(create(0), create(1))))
                .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE));

        List<BandDto> bandDtoList = bandDtoFakerService.fillFakeBandsDto(size, "EN");

        assertNotNull(bandDtoList);
        assertTrue(bandDtoList.size() > 0);

        RecordedRequest recordedRequest = mockWebServer.takeRequest();

        assertEquals("GET", recordedRequest.getMethod());
        assertEquals("/bands_dto/fill?size=2&language=EN", recordedRequest.getPath());
    }

    private BandDto create(int index) {
        BandDto bandDto = new BandDto();
        bandDto.setBandId(index);
        bandDto.setBandName("band" + index);
        bandDto.setBandCountTrack(100 + index);
        bandDto.setBandRepertoireDuration(1000 + index);
        bandDto.setBandDetails(bandDto.getBandName() + "details" + index);
        return bandDto;
    }
}