package com.epam.brest.service;

import com.epam.brest.model.dto.TrackDto;
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

class TrackDtoServiceWebclientTest {

    private final Logger logger = LogManager.getLogger(TrackDtoServiceWebclientTest.class);

    private TrackDtoService serviceImpl;

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
        serviceImpl = new TrackDtoServiceWebClient("/tracks_dto", WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build());
    }

    @Test
    void findAllTracksWithBandName() throws Exception {
        logger.debug("findAllTracksWithBandName()");
        mockWebServer.enqueue(new MockResponse().setResponseCode(200)
                .setBody(objectMapper.writeValueAsString(Arrays.asList(create(0), create(1))))
                .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE));

        List<TrackDto> trackDtoList = serviceImpl.findAllTracksWithBandName();

        assertNotNull(trackDtoList);
        assertTrue(trackDtoList.size() > 0);

        RecordedRequest recordedRequest = mockWebServer.takeRequest();

        assertEquals("GET", recordedRequest.getMethod());
        assertEquals("/tracks_dto", recordedRequest.getPath());
    }

    @Test
    void findAllTracksWithBandNameByBandId() throws Exception {
        logger.debug("findAllTracksWithBandNameByBandId()");
        Integer id = 3;
        mockWebServer.enqueue(new MockResponse().setResponseCode(200)
                .setBody(objectMapper.writeValueAsString(Arrays.asList(create(0), create(1))))
                .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE));

        List<TrackDto> trackDtoList = serviceImpl.findAllTracksWithBandNameByBandId(id);

        assertNotNull(trackDtoList);
        assertTrue(trackDtoList.size() > 0);

        RecordedRequest recordedRequest = mockWebServer.takeRequest();

        assertEquals("GET", recordedRequest.getMethod());
        assertEquals("/tracks_dto/band/" + id, recordedRequest.getPath());

    }

    @Test
    void findAllTracksWithReleaseDateFilter() throws Exception {
        logger.debug("findAllTracksWithReleaseDateFilter()");
        LocalDate fromDate = LocalDate.parse("2010-01-01");
        LocalDate toDate = LocalDate.parse("2012-01-01");

        mockWebServer.enqueue(new MockResponse().setResponseCode(200)
                .setBody(objectMapper.writeValueAsString(Arrays.asList(create(0), create(1))))
                .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE));

        List<TrackDto> list = serviceImpl.findAllTracksWithReleaseDateFilter(fromDate, toDate);

        assertNotNull(list);
        assertEquals(2, list.size());

        RecordedRequest recordedRequest = mockWebServer.takeRequest();

        assertEquals("GET", recordedRequest.getMethod());
        assertEquals("/tracks_dto?fromDate=2010-01-01&toDate=2012-01-01" , recordedRequest.getPath());
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