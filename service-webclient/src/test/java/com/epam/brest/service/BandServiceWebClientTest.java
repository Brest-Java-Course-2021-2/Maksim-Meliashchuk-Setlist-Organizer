package com.epam.brest.service;

import com.epam.brest.model.Band;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BandServiceWebClientTest {

    private final Logger logger = LogManager.getLogger(BandServiceWebClientTest.class);

    private BandService serviceImpl;

    public static MockWebServer mockWebServer;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @AfterAll
    static void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

    @BeforeEach
    void init() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
        String baseUrl = String.format("http://localhost:%s", mockWebServer.getPort());
        serviceImpl = new BandServiceWebClient("/bands", WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build());
    }

    @Test
    void getBandById() throws Exception {

        logger.debug("getBandById()");
        Integer id = 1;
        Band band = create(id);

        mockWebServer.enqueue(new MockResponse().setResponseCode(200)
                .setBody(objectMapper.writeValueAsString(band))
                .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE));

        Band resultBand = serviceImpl.getBandById(id);

        assertNotNull(resultBand);
        assertEquals(resultBand.getBandId(), id);
        assertEquals(resultBand.getBandName(), band.getBandName());
        assertEquals(resultBand.getBandDetails(), band.getBandDetails());

        StepVerifier.create(Mono.just(resultBand))
                .expectNextMatches(b ->
                        b.getBandName().equals(band.getBandName()) &&
                                b.getBandDetails().equals(band.getBandDetails()) &&
                                b.getBandId().equals(band.getBandId()))
                .verifyComplete();

        RecordedRequest recordedRequest = mockWebServer.takeRequest();

        assertEquals("GET", recordedRequest.getMethod());
        assertEquals("/bands/1", recordedRequest.getPath());
    }

    @Test
    void findAllBands() throws Exception {
        logger.debug("findAllBands()");

        mockWebServer.enqueue(new MockResponse().setResponseCode(200)
                .setBody(objectMapper.writeValueAsString(Arrays.asList(create(0), create(1))))
                .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE));

        List<Band> bands = serviceImpl.findAllBands();

        assertNotNull(bands);
        assertTrue(bands.size() > 0);

        RecordedRequest recordedRequest = mockWebServer.takeRequest();
        assertEquals("GET", recordedRequest.getMethod());
        assertEquals("/bands", recordedRequest.getPath());
    }

    @Test
    void create() throws Exception {
        logger.debug("create()");

        Integer id = 1;
        Band band = create(id);

        mockWebServer.enqueue(new MockResponse().setResponseCode(200)
                .setBody(objectMapper.writeValueAsString(id))
                .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE));

        Integer resultId = serviceImpl.create(band);

        assertNotNull(resultId);

        StepVerifier.create(Mono.just(resultId))
                .expectNextMatches(i -> i.equals(id))
                .verifyComplete();

        RecordedRequest recordedRequest = mockWebServer.takeRequest();

        assertEquals("POST", recordedRequest.getMethod());
        assertEquals("/bands", recordedRequest.getPath());

    }

    @Test
    void update() throws Exception {
        logger.debug("update()");
        Integer id = 1;
        Band band = create(id);

        mockWebServer.enqueue(new MockResponse().setResponseCode(200)
                .setBody(objectMapper.writeValueAsString(id))
                .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE));

        Integer resultId = serviceImpl.update(band);

        mockWebServer.enqueue(new MockResponse().setResponseCode(200)
                .setBody(objectMapper.writeValueAsString(band))
                .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE));
        Band resultBand = serviceImpl.getBandById(id);

        assertNotNull(resultId);
        assertSame(id, resultId);
        assertNotNull(resultBand);
        assertEquals(resultBand.getBandId(), id);
        assertEquals(resultBand.getBandName(), band.getBandName());
        assertEquals(resultBand.getBandDetails(), band.getBandDetails());

        StepVerifier.create(Mono.just(resultId))
                .expectNextMatches(i -> i.equals(id))
                .verifyComplete();

        StepVerifier.create(Mono.just(resultBand))
                .expectNextMatches(b ->
                        b.getBandName().equals(band.getBandName()) &&
                                b.getBandDetails().equals(band.getBandDetails()) &&
                                b.getBandId().equals(band.getBandId()))
                .verifyComplete();

        RecordedRequest recordedRequest = mockWebServer.takeRequest();

        assertEquals("PUT", recordedRequest.getMethod());
        assertEquals("/bands", recordedRequest.getPath());
    }

    @Test
    void delete() throws Exception {
        logger.debug("delete()");
        Integer count = 1;
        Integer id = 1;

        mockWebServer.enqueue(new MockResponse().setResponseCode(200)
                .setBody(objectMapper.writeValueAsString(count))
                .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE));

        Integer result = serviceImpl.delete(id);
        assertSame(count, result);

        RecordedRequest recordedRequest = mockWebServer.takeRequest();

        assertEquals("DELETE", recordedRequest.getMethod());
        assertEquals("/bands/1", recordedRequest.getPath());
    }

    @Test
    void count() throws Exception {
        logger.debug("count()");
        Integer count = 1;
        mockWebServer.enqueue(new MockResponse().setResponseCode(200)
                .setBody(objectMapper.writeValueAsString(count))
                .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE));

        Integer result = serviceImpl.count();
        assertSame(count, result);

        RecordedRequest recordedRequest = mockWebServer.takeRequest();

        assertEquals("GET", recordedRequest.getMethod());
        assertEquals("/bands/count", recordedRequest.getPath());

    }

    private Band create(int index) {
        Band band = new Band();
        band.setBandId(index);
        band.setBandName("band" + index);
        band.setBandDetails(band.getBandName() + "details" + index);
        return band;
    }
}
