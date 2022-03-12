package com.epam.brest.service.rest;

import com.epam.brest.model.TrackDto;
import com.epam.brest.service.TrackDtoServiceRest;
import com.epam.brest.service.config.ServiceRestTestConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
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
class TrackDtoServiceRestTest {

    private final Logger logger = LogManager.getLogger(TrackDtoServiceRestTest.class);

    public static final String URL = "http://localhost:8088/repertoire";

    public static final String URL_BAND_TRACKS = "http://localhost:8088/repertoire/band/3";

    public static final String URL_SEARCH = "http://localhost:8088/repertoire?fromDate=2010-01-01&toDate=2012-01-01";

    public static final LocalDate FROM_DATE = LocalDate.parse("2010-01-01");
    public static final LocalDate TO_DATE = LocalDate.parse("2012-01-01");

    @Autowired
    private RestTemplate restTemplate;

    private MockRestServiceServer mockServer;

    @Captor
    private ArgumentCaptor<LocalDate> captorDate;

    private final ObjectMapper objectMapper = JsonMapper.builder()
            .addModule(new JavaTimeModule())
            .build();

    private TrackDtoServiceRest trackDtoServiceRest;

    @BeforeEach
    public void before() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
        trackDtoServiceRest = new TrackDtoServiceRest(URL, restTemplate);
    }


    @Test
    void shouldFindAllTracksWithBandName() throws Exception {
        logger.debug("shouldFindAllTracksWithBandName()");
        // given
        mockServer.expect(ExpectedCount.once(), requestTo(new URI(URL)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(objectMapper.writeValueAsString(Arrays.asList(create(0), create(1)))));

        // when
        List<TrackDto> list = trackDtoServiceRest.findAllTracksWithBandName();

        // then
        mockServer.verify();
        assertNotNull(list);
        assertTrue(list.size() > 0);
    }

    @Test
    void shouldFindAllTracksWithBandNameByBandId() throws Exception {
        logger.debug("shouldFindAllTracksWithBandNameByBandId()");
        int id = 3;
        // given
        mockServer.expect(ExpectedCount.once(), requestTo(new URI(URL_BAND_TRACKS)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(objectMapper.writeValueAsString(Arrays.asList(create(0), create(1)))));

        // when
        List<TrackDto> list = trackDtoServiceRest.findAllTracksWithBandNameByBandId(id);

        // then
        mockServer.verify();
        assertNotNull(list);
        assertTrue(list.size() > 0);
    }

    @Test
    void shouldFindAllTracksWithReleaseDateFilter()  throws Exception {
        logger.debug("shouldFindAllTracksWithReleaseDateFilter()");
        // given
        mockServer.expect(ExpectedCount.once(), requestTo(new URI(URL_SEARCH)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(objectMapper.writeValueAsString(List.of(create(1)))));

        // when
        List<TrackDto> list = trackDtoServiceRest.findAllTracksWithReleaseDateFilter(FROM_DATE, TO_DATE);

        // then
        mockServer.verify();
        assertNotNull(list);
        assertEquals(1, list.size());
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