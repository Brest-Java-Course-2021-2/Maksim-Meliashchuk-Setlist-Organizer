package com.epam.brest.service;

import com.epam.brest.model.TrackDto;
import com.epam.brest.service.faker.TrackDtoFakerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TrackDtoFakerServiceWebClientTest {

    private final Logger logger = LogManager.getLogger(TrackDtoFakerServiceWebClientTest.class);

    private TrackDtoFakerService trackDtoFakerService;

    public static MockWebServer mockWebServer;

    private final ObjectMapper objectMapper = JsonMapper.builder()
            .addModule(new JavaTimeModule())
            .build();

    @AfterAll
    static void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

    @BeforeEach
    void init() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
        String baseUrl = String.format("http://localhost:%s", mockWebServer.getPort());
        trackDtoFakerService = new TrackDtoFakerServiceWebClient("/tracks_dto/fill", WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build());
    }

    @Test
    void fillFakeTracksDto() throws Exception {
        logger.debug("fillFakeTracksDto()");
        Integer size = 2;

        mockWebServer.enqueue(new MockResponse().setResponseCode(200)
                .setBody(objectMapper.writeValueAsString(Arrays.asList(create(0), create(1))))
                .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE));

        List<TrackDto> list = trackDtoFakerService.fillFakeTracksDto(size, "EN");

        assertNotNull(list);
        assertEquals(2, list.size());

        RecordedRequest recordedRequest = mockWebServer.takeRequest();

        assertEquals("GET", recordedRequest.getMethod());
        assertEquals("/tracks_dto/fill?size=2&language=EN" , recordedRequest.getPath());
    }

    private TrackDto create(int index) {
        TrackDto trackDto = new TrackDto();
        LocalDate releaseDate = LocalDate.parse("2012-03-12");
        trackDto.setTrackId(index);
        trackDto.setTrackName("track" + index);
        trackDto.setTrackDuration(10000 + index);
        trackDto.setTrackBandName("band" + index);
        trackDto.setTrackReleaseDate(releaseDate.plusYears(index));
        trackDto.setTrackLink("link" + index);
        trackDto.setTrackDetails(trackDto.getTrackName() + "details" + index);
        return trackDto;
    }
}