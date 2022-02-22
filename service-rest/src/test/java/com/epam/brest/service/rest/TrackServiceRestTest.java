package com.epam.brest.service.rest;

import com.epam.brest.model.Track;
import com.epam.brest.model.TrackDto;
import com.epam.brest.service.TrackServiceRest;
import com.epam.brest.service.config.ServiceRestTestConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

@ExtendWith(SpringExtension.class)
@Import({ServiceRestTestConfig.class})
class TrackServiceRestTest {

    private final Logger logger = LogManager.getLogger(TrackServiceRestTest.class);

    public static final String TRACKS_URL = "http://localhost:8088/repertoire";

    @Autowired
    private RestTemplate restTemplate;

    private MockRestServiceServer mockServer;

    private ObjectMapper objectMapper = JsonMapper.builder()
            .addModule(new JavaTimeModule())
            .build();

    private TrackServiceRest trackServiceRest;

    @BeforeEach
    public void before() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
        trackServiceRest = new TrackServiceRest(TRACKS_URL, restTemplate);
    }


    @Test
    void shouldGetTrackById()  throws Exception {
        logger.debug("shouldGetTrackById()");
        // given
        Integer id = 1;

        Track track =Track.builder()
                .trackId(id)
                .trackName("Test track")
                .trackDetails("")
                .trackTempo(120)
                .trackLink("link")
                .trackDuration(1500)
                .trackBandId(1)
                .trackReleaseDate(LocalDate.parse("2020-12-12"))
                .build();


        mockServer.expect(ExpectedCount.once(), requestTo(new URI(TRACKS_URL + "/" + id)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(objectMapper.writeValueAsString(track))
                );

        // when
        Track resultTrack = trackServiceRest.getTrackById(id);

        // then
        mockServer.verify();
        assertNotNull(resultTrack);
        assertEquals(resultTrack.getTrackId(), id);
        assertEquals(resultTrack.getTrackBandId(), track.getTrackBandId());
        assertEquals(resultTrack.getTrackTempo(), track.getTrackTempo());
        assertEquals(resultTrack.getTrackDuration(), track.getTrackDuration());
        assertEquals(resultTrack.getTrackName(), track.getTrackName());
        assertEquals(resultTrack.getTrackDetails(), track.getTrackDetails());
        assertEquals(resultTrack.getTrackLink(), track.getTrackLink());
        assertEquals(resultTrack.getTrackReleaseDate(), track.getTrackReleaseDate());
    }

    @Test
    void shouldCreate()  throws Exception {
        logger.debug("shouldCreate()");
        // given
        Track track = new Track();
        mockServer.expect(ExpectedCount.once(), requestTo(new URI(TRACKS_URL)))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(objectMapper.writeValueAsString("1"))
                );
        // when
        Integer id = trackServiceRest.create(track);

        // then
        mockServer.verify();
        assertNotNull(id);
    }

    @Test
    void shouldUpdate()  throws Exception {
        logger.debug("shouldUpdate()");
        // given
        Integer id = 1;

        Track track =Track.builder()
                .trackId(id)
                .trackName("Test track")
                .trackDetails("")
                .trackTempo(120)
                .trackLink("link")
                .trackDuration(1500)
                .trackBandId(1)
                .trackReleaseDate(LocalDate.parse("2020-12-12"))
                .build();

        mockServer.expect(ExpectedCount.once(), requestTo(new URI(TRACKS_URL)))
                .andExpect(method(HttpMethod.PUT))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(objectMapper.writeValueAsString("1"))
                );

        mockServer.expect(ExpectedCount.once(), requestTo(new URI(TRACKS_URL + "/" + id)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(objectMapper.writeValueAsString(track))
                );

        // when
        int result = trackServiceRest.update(track);
        Track resultTrack = trackServiceRest.getTrackById(id);

        // then
        mockServer.verify();
        assertEquals(1, result);
        assertNotNull(resultTrack);
        assertEquals(resultTrack.getTrackId(), id);
        assertEquals(resultTrack.getTrackBandId(), track.getTrackBandId());
        assertEquals(resultTrack.getTrackTempo(), track.getTrackTempo());
        assertEquals(resultTrack.getTrackDuration(), track.getTrackDuration());
        assertEquals(resultTrack.getTrackName(), track.getTrackName());
        assertEquals(resultTrack.getTrackDetails(), track.getTrackDetails());
        assertEquals(resultTrack.getTrackLink(), track.getTrackLink());
        assertEquals(resultTrack.getTrackReleaseDate(), track.getTrackReleaseDate());
    }

    @Test
    void shouldDelete()  throws Exception {
        logger.debug("shouldDelete()");
        // given
        Integer id = 1;
        mockServer.expect(ExpectedCount.once(), requestTo(new URI(TRACKS_URL + "/" + id)))
                .andExpect(method(HttpMethod.DELETE))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(objectMapper.writeValueAsString("1"))
                );
        // when
        int result = trackServiceRest.delete(id);

        // then
        mockServer.verify();
        assertTrue(1 == result);
    }

    @Test
    void shouldCount()  throws Exception {
        logger.debug("shouldCount()");
        mockServer.expect(ExpectedCount.once(), requestTo(new URI(TRACKS_URL + "/count")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(objectMapper.writeValueAsString("1"))
                );
        // when
        int result = trackServiceRest.count();

        // then
        mockServer.verify();
        assertTrue(result> 0);
    }

    @Test
    void shouldFindAllTracks()  throws Exception {
        logger.debug("shouldFindAllTracks()");
        // given
        mockServer.expect(ExpectedCount.once(), requestTo(new URI(TRACKS_URL)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(objectMapper.writeValueAsString(Arrays.asList(create(0), create(1))))
                );

        // when
        List<Track> tracks = trackServiceRest.findAllTracks();

        // then
        mockServer.verify();
        assertNotNull(tracks);
        assertTrue(tracks.size() > 0);
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