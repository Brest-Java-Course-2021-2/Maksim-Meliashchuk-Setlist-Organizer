package com.epam.brest.web_app;

import com.epam.brest.model.Band;
import com.epam.brest.model.Track;
import com.epam.brest.model.dto.TrackDto;
import com.epam.brest.service.TrackService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import java.net.URI;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.client.ExpectedCount.once;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
public class TrackControllerIT {

    private static final String TRACKS_DTO_URL = "http://localhost:8088/repertoire/filter";
    private static final String TRACKS_URL = "http://localhost:8088/repertoire";
    private static final String BANDS_URL = "http://localhost:8088/bands";

    private final Logger logger = LogManager.getLogger(BandControllerIT.class);

    @Autowired
    private WebApplicationContext wac;

    private TrackService trackService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    private MockRestServiceServer mockServer;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .alwaysDo(MockMvcResultHandlers.print())
                .build();
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }
    @Test
    void shouldReturnRepertoirePage() throws Exception {
        logger.debug("shouldReturnRepertoirePage()");

        TrackDto trackDto1 = createTrackDto(0);
        TrackDto trackDto2 = createTrackDto(1);

        List<TrackDto> trackDtoList = Arrays.asList(trackDto1, trackDto2);

        //WHEN
        mockServer.expect(once(), requestTo(new URI(TRACKS_DTO_URL)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(objectMapper.writeValueAsString(trackDtoList))

                );

        // THEN
        mockMvc.perform(MockMvcRequestBuilders.get("/repertoire")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(view().name("repertoire"))
                .andExpect(model().attribute("tracks", hasItem(
                        allOf(
                                hasProperty("trackId", is(0)),
                                hasProperty("trackName", is("track0")),
                                hasProperty("trackBandName", is("band0")),
                                hasProperty("trackTempo", is(100)),
                                hasProperty("trackDuration", is(10000)),
                                hasProperty("trackDetails", is("track0details0")),
                                hasProperty("trackReleaseDate", is(LocalDate.parse("2012-03-12"))),
                                hasProperty("trackLink", is("link0"))
                        )
                )))
                .andExpect(model().attribute("tracks", hasItem(
                        allOf(
                                hasProperty("trackId", is(1)),
                                hasProperty("trackName", is("track1")),
                                hasProperty("trackBandName", is("band1")),
                                hasProperty("trackTempo", is(101)),
                                hasProperty("trackDuration", is(10001)),
                                hasProperty("trackDetails", is("track1details1")),
                                hasProperty("trackReleaseDate", is(LocalDate.parse("2013-03-12"))),
                                hasProperty("trackLink", is("link1"))
                        )
                )));

        // VERIFY
        mockServer.verify();
    }

    @Test
    void shouldFindAllTracksWithReleaseDateFilter() throws Exception {
        logger.debug("shouldFindAllTracksWithReleaseDateFilter()");

        TrackDto trackDto1 = createTrackDto(0);
        TrackDto trackDto2 = createTrackDto(1);

        List<TrackDto> trackList = Arrays.asList(trackDto1, trackDto2);
        String fromDate = String.valueOf(LocalDate.parse("2000-10-10"));
        String toDate = String.valueOf(LocalDate.parse("2021-10-10"));

        mockServer.expect(once(), requestTo(new URI(TRACKS_DTO_URL + "?fromDate=" + fromDate +  "&toDate=" + toDate)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(objectMapper.writeValueAsString(trackList))
                );
        mockMvc.perform(get("/repertoire/filter?fromDate={fromDate}&toDate={toDate}", fromDate, toDate)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(view().name("repertoire"))
                .andExpect(model().attribute("tracks", hasItem(
                        allOf(
                                hasProperty("trackId", is(0)),
                                hasProperty("trackName", is("track0")),
                                hasProperty("trackBandName", is("band0")),
                                hasProperty("trackTempo", is(100)),
                                hasProperty("trackDuration", is(10000)),
                                hasProperty("trackDetails", is("track0details0")),
                                hasProperty("trackReleaseDate", is(LocalDate.parse("2012-03-12"))),
                                hasProperty("trackLink", is("link0"))
                        )
                )))
                .andExpect(model().attribute("tracks", hasItem(
                        allOf(
                                hasProperty("trackId", is(1)),
                                hasProperty("trackName", is("track1")),
                                hasProperty("trackBandName", is("band1")),
                                hasProperty("trackTempo", is(101)),
                                hasProperty("trackDuration", is(10001)),
                                hasProperty("trackDetails", is("track1details1")),
                                hasProperty("trackReleaseDate", is(LocalDate.parse("2013-03-12"))),
                                hasProperty("trackLink", is("link1"))
                        )
                )));
        mockServer.verify();

    }

    @Test
    void shouldAddTrack() throws Exception {
        logger.debug("shouldAddTrack()");

        // WHEN
        mockServer.expect(ExpectedCount.once(), requestTo(new URI(TRACKS_URL)))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body("1")
                );

        Track track = new Track("Test track");

        // THEN
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/track")
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                .param("trackName", track.getTrackName())
                ).andDo(MockMvcResultHandlers.print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/repertoire"))
                .andExpect(redirectedUrl("/repertoire"));

        // VERIFY
        mockServer.verify();
    }

    @Test
    public void shouldOpenEditTrackPageById() throws Exception {
        logger.debug("shouldOpenEditTrackPageById()");

        Track track = new Track("Test track").setTrackId(1).setTrackBandId(1);
        Band band1 = createBand(1);
        Band band2 = createBand(2);
        List<Band> bandList = Arrays.asList(band1, band2);

        // WHEN
        mockServer.expect(ExpectedCount.once(), requestTo(new URI(TRACKS_URL + "/" + track.getTrackId())))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(objectMapper.writeValueAsString(track))
                );

        mockServer.expect(once(), requestTo(new URI(BANDS_URL)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(objectMapper.writeValueAsString(bandList))

                );

        // THEN
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/track/1")
                ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("track"))
                .andExpect(model().attribute("isNew", is(false)))
                .andExpect(model().attribute("track", hasProperty("trackId", is(1))))
                .andExpect(model().attribute("track", hasProperty("trackBandId", is(1))))
                .andExpect(model().attribute("track", hasProperty("trackName", is("Test track"))));

        // VERIFY
        mockServer.verify();
    }

    @Test
    public void shouldDeleteTrack() throws Exception {
        logger.debug("shouldDeleteTrack()");

        int id = 1;

        // WHEN
        mockServer.expect(ExpectedCount.once(), requestTo(new URI(TRACKS_URL + "/" + id)))
                .andExpect(method(HttpMethod.DELETE))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body("1")
                );

        // THEN
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/track/1/delete")
                ).andExpect(status().isFound())
                .andExpect(view().name("redirect:/repertoire"))
                .andExpect(redirectedUrl("/repertoire"));

        // VERIFY
        mockServer.verify();
    }

    private TrackDto createTrackDto(int index) {
        TrackDto trackDto = new TrackDto();
        LocalDate releaseDate = LocalDate.parse("2012-03-12");
        trackDto.setTrackId(index);
        trackDto.setTrackName("track" + index);
        trackDto.setTrackDuration(10000 + index);
        trackDto.setTrackTempo(100 + index);
        trackDto.setTrackBandName("band" + index);
        trackDto.setTrackReleaseDate(releaseDate.plusYears(index));
        trackDto.setTrackLink("link" + index);
        trackDto.setTrackDetails(trackDto.getTrackName() + "details" + index);
        return trackDto;
    }

    private Band createBand(int index) {
        Band band = new Band();
        band.setBandId(index);
        band.setBandName("band" + index);
        band.setBandDetails(band.getBandName() + "details" + index);
        return band;
    }

}
