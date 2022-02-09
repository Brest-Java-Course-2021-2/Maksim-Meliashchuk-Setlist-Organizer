package com.epam.brest.service;

import com.epam.brest.model.Track;
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
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TrackServiceWebClientTest {

    private final Logger logger = LogManager.getLogger(TrackServiceWebClientTest.class);

    private TrackService serviceImpl;

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
        serviceImpl = new TrackServiceWebClient("/repertoire", WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build());
    }

    @Test
    void getTrackById() throws Exception {
        logger.debug("getBandById()");
        Integer id = 1;
        Track track = create(id);
        mockWebServer.enqueue(new MockResponse().setResponseCode(200)
                .setBody(objectMapper.writeValueAsString(track))
                .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE));

        Track resultTrack = serviceImpl.getTrackById(id);

        assertNotNull(resultTrack);
        assertEquals(resultTrack.getTrackId(), id);
        assertEquals(resultTrack.getTrackName(), track.getTrackName());
        assertEquals(resultTrack.getTrackDetails(), track.getTrackDetails());
        assertEquals(resultTrack.getTrackLink(), track.getTrackLink());
        assertEquals(resultTrack.getTrackTempo(), track.getTrackTempo());
        assertEquals(resultTrack.getTrackReleaseDate(), track.getTrackReleaseDate());
        assertEquals(resultTrack.getTrackDuration(), track.getTrackDuration());
        assertEquals(resultTrack.getTrackBandId(), track.getTrackBandId());

        StepVerifier.create(Mono.just(resultTrack))
                .expectNextMatches(t ->
                        t.getTrackName().equals(track.getTrackName()) &&
                                t.getTrackDetails().equals(track.getTrackDetails()) &&
                                t.getTrackLink().equals(track.getTrackLink()) &&
                                t.getTrackTempo().equals(track.getTrackTempo()) &&
                                t.getTrackReleaseDate().equals(track.getTrackReleaseDate()) &&
                                t.getTrackDuration().equals(track.getTrackDuration()) &&
                                t.getTrackBandId().equals(track.getTrackBandId()) &&
                                t.getTrackId().equals(track.getTrackId()))

                .verifyComplete();

        RecordedRequest recordedRequest = mockWebServer.takeRequest();

        assertEquals("GET", recordedRequest.getMethod());
        assertEquals("/repertoire/1", recordedRequest.getPath());
    }

    @Test
    void create() throws Exception {
        logger.debug("create()");

        Integer id = 1;
        Track track = create(id);

        mockWebServer.enqueue(new MockResponse().setResponseCode(200)
                .setBody(objectMapper.writeValueAsString(id))
                .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE));

        Integer resultId = serviceImpl.create(track);

        assertNotNull(resultId);

        StepVerifier.create(Mono.just(resultId))
                .expectNextMatches(i -> i.equals(id))
                .verifyComplete();

        RecordedRequest recordedRequest = mockWebServer.takeRequest();

        assertEquals("POST", recordedRequest.getMethod());
        assertEquals("/repertoire", recordedRequest.getPath());
    }

    @Test
    void update() throws Exception {
        logger.debug("update()");
        Integer id = 1;
        Track track = create(id);

        mockWebServer.enqueue(new MockResponse().setResponseCode(200)
                .setBody(objectMapper.writeValueAsString(id))
                .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE));

        Integer resultId = serviceImpl.update(track);

        mockWebServer.enqueue(new MockResponse().setResponseCode(200)
                .setBody(objectMapper.writeValueAsString(track))
                .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE));

        Track resultTrack = serviceImpl.getTrackById(id);

        assertNotNull(resultId);
        assertSame(id, resultId);
        assertNotNull(resultTrack);
        assertEquals(resultTrack.getTrackId(), id);
        assertEquals(resultTrack.getTrackName(), track.getTrackName());
        assertEquals(resultTrack.getTrackDetails(), track.getTrackDetails());
        assertEquals(resultTrack.getTrackLink(), track.getTrackLink());
        assertEquals(resultTrack.getTrackTempo(), track.getTrackTempo());
        assertEquals(resultTrack.getTrackReleaseDate(), track.getTrackReleaseDate());
        assertEquals(resultTrack.getTrackDuration(), track.getTrackDuration());
        assertEquals(resultTrack.getTrackBandId(), track.getTrackBandId());

        StepVerifier.create(Mono.just(resultId))
                .expectNextMatches(i -> i.equals(id))
                .verifyComplete();

        StepVerifier.create(Mono.just(resultTrack))
                .expectNextMatches(t ->
                        t.getTrackName().equals(track.getTrackName()) &&
                                t.getTrackDetails().equals(track.getTrackDetails()) &&
                                t.getTrackLink().equals(track.getTrackLink()) &&
                                t.getTrackTempo().equals(track.getTrackTempo()) &&
                                t.getTrackReleaseDate().equals(track.getTrackReleaseDate()) &&
                                t.getTrackDuration().equals(track.getTrackDuration()) &&
                                t.getTrackBandId().equals(track.getTrackBandId()) &&
                                t.getTrackId().equals(track.getTrackId()))

                .verifyComplete();


        RecordedRequest recordedRequest = mockWebServer.takeRequest();

        assertEquals("PUT", recordedRequest.getMethod());
        assertEquals("/repertoire", recordedRequest.getPath());
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
        assertEquals("/repertoire/" + id, recordedRequest.getPath());
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
        assertEquals("/repertoire/count", recordedRequest.getPath());
    }

    @Test
    void findAllTracks() throws Exception {
        logger.debug("findAllTracks()");

        mockWebServer.enqueue(new MockResponse().setResponseCode(200)
                .setBody(objectMapper.writeValueAsString(Arrays.asList(create(0), create(1))))
                .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE));

        List<Track> tracks = serviceImpl.findAllTracks();

        assertNotNull(tracks);
        assertTrue(tracks.size() > 0);

        RecordedRequest recordedRequest = mockWebServer.takeRequest();
        assertEquals("GET", recordedRequest.getMethod());
        assertEquals("/repertoire", recordedRequest.getPath());
    }

    private Track create(int index) {
        Track track = new Track();
        LocalDate releaseDate = LocalDate.parse("2012-03-12");
        track.setTrackId(index);
        track.setTrackName("track" + index);
        track.setTrackDuration(10000 + index);
        track.setTrackBandId(index + 1);
        track.setTrackTempo(index + 100);
        track.setTrackReleaseDate(releaseDate.plusYears(index));
        track.setTrackLink("link" + index);
        track.setTrackDetails(track.getTrackName() + "details" + index);
        return track;
    }
}