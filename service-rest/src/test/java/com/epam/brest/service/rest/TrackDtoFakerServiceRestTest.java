package com.epam.brest.service.rest;

import com.epam.brest.model.TrackDto;
import com.epam.brest.service.TrackDtoFakerServiceRest;
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
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

@ExtendWith(SpringExtension.class)
@Import({ServiceRestTestConfig.class})
class TrackDtoFakerServiceRestTest {

    private final Logger logger = LogManager.getLogger(TrackDtoFakerServiceRestTest.class);

    public static final String URL_SEARCH = "http://localhost:8088/tracks_dto/fill?size=1&language=EN";

    public static final String URL = "http://localhost:8088/tracks_dto/fill";

    private MockRestServiceServer mockServer;

    @Autowired
    private RestTemplate restTemplate;

    private final ObjectMapper objectMapper = JsonMapper.builder()
            .addModule(new JavaTimeModule())
            .build();

    private TrackDtoFakerServiceRest trackDtoFakerServiceRest;


    @BeforeEach
    public void before() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
        trackDtoFakerServiceRest = new TrackDtoFakerServiceRest(URL, restTemplate);
    }

    @Test
    void shouldFillFakeTracksDtoTest()  throws Exception {
        logger.debug("shouldFillFakeTracksDtoTest()");
        Integer size = 1;
        // given
        mockServer.expect(ExpectedCount.once(), requestTo(new URI(URL_SEARCH)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(objectMapper.writeValueAsString(List.of(create(1)))));

        // when
        List<TrackDto> list = trackDtoFakerServiceRest.fillFakeTracksDto(size, "EN");

        // then
        mockServer.verify();
        assertNotNull(list);
        assertEquals(size, list.size());
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