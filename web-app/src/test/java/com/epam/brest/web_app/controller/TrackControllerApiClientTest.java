package com.epam.brest.web_app.controller;

import com.epam.brest.model.Track;
import com.epam.brest.model.TrackDto;
import com.epam.brest.service.BandService;
import com.epam.brest.service.TrackDtoService;
import com.epam.brest.service.TrackService;
import com.epam.brest.service.faker.TrackDtoFakerService;
import com.epam.brest.web_app.validator.TrackValidator;
import io.swagger.client.api.BandApi;
import io.swagger.client.api.TrackApi;
import io.swagger.client.api.TracksApi;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ComponentScan({"com.epam.brest.web_app.validator"})
@SpringBootTest(properties = { "app.httpClient = ApiClient" })
@AutoConfigureMockMvc
class TrackControllerApiClientTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TracksApi tracksApi;

    @MockBean
    private TrackApi trackApi;

    @MockBean
    private BandApi bandApi;

    @MockBean
    private TrackDtoService trackDtoService;

    @MockBean
    private TrackService trackService;

    @MockBean
    private BandService bandService;

    @MockBean
    private TrackDtoFakerService trackDtoFakerService;

    @MockBean
    private TrackValidator trackValidator;

    private static final Logger LOGGER = LoggerFactory.getLogger(TrackControllerApiClientTest.class);

    @Test
    void gotoAddTrackPage() throws Exception {
        LOGGER.debug("gotoAddTrackPage()");

        this.mockMvc.perform(get("/track")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(model().attribute("isNew", is(true)))
                .andExpect(content().string(containsString("New track")));
    }

    @Test
    void gotoEditTrackPage() throws Exception {
        LOGGER.debug("gotoEditTrackPage()");

        Integer id = 1;
        Track track = Track.builder()
                .trackId(id)
                .trackBandId(id)
                .trackName("Test track")
                .trackReleaseDate(LocalDate.parse("2005-12-02"))
                .build();

        when(trackApi.getTrackById(id)).thenReturn(track);

        this.mockMvc.perform(get("/track/{id}", id)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(model().attribute("track", track))
                .andExpect(model().attribute("isNew", is(false)))
                .andExpect(content().string(containsString(track.getTrackId().toString())))
                .andExpect(content().string(containsString(track.getTrackName())))
                .andExpect(content().string(containsString(track.getTrackReleaseDate().toString())));

    }

    @Test
    void addTrack() throws Exception {
        LOGGER.debug("addTrack()");

        Integer id = 1;
        Track track = Track.builder()
                .trackId(id)
                .trackBandId(id)
                .trackName("Test track")
                .trackReleaseDate(LocalDate.parse("2005-12-02"))
                .build();

        when(trackApi.createTrack(track)).thenReturn(track.getTrackId());

        this.mockMvc.perform(post("/track").contentType(MediaType.APPLICATION_FORM_URLENCODED)).andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/repertoire"))
                .andExpect(redirectedUrl("/repertoire"));

    }

    @Test
    void updateTrack() throws Exception {
        LOGGER.debug("updateTrack()");

        Integer id = 1;
        Track track = Track.builder()
                .trackId(id)
                .trackBandId(id)
                .trackName("Test track")
                .trackReleaseDate(LocalDate.parse("2005-12-02"))
                .build();

        when(trackApi.updateTrack(track)).thenReturn(track.getTrackId());

        this.mockMvc.perform(post("/track/{id}", track.getTrackId())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)).andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/repertoire"))
                .andExpect(redirectedUrl("/repertoire"));

    }

    @Test
    void deleteTrackById() throws Exception {
        LOGGER.debug("deleteTrackById()");

        Integer id = 1;
        Track track = Track.builder()
                .trackId(id)
                .trackBandId(id)
                .trackName("Test track")
                .trackReleaseDate(LocalDate.parse("2005-12-02"))
                .build();

        when(trackApi.updateTrack(track)).thenReturn(track.getTrackId());

        this.mockMvc.perform(get("/track/{id}/delete", track.getTrackId())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)).andDo(print())
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/repertoire"))
                .andExpect(redirectedUrl("/repertoire"));

    }

    @Test
    void findAllTracksWithBandName() throws Exception {
        LOGGER.debug("findAllTracksWithBandName()");

        TrackDto track1 = createTrackDto(1);
        TrackDto track2 = createTrackDto(2);
        List<TrackDto> trackDtoList = Arrays.asList(track1, track2);

        when(tracksApi.findAllTracksWithBandName()).thenReturn(trackDtoList);

        this.mockMvc.perform(get("/repertoire")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(model().attribute("tracks", trackDtoList))
                .andExpect(content().string(containsString(track1.getTrackName())))
                .andExpect(content().string(containsString(track1.getTrackId().toString())))
                .andExpect(content().string(containsString(track1.getTrackLink())))
                .andExpect(content().string(containsString(track1.getTrackDetails())))
                .andExpect(content().string(containsString(track1.getTrackBandName())))
                .andExpect(content().string(containsString(track1.getTrackDuration().toString())))
                .andExpect(content().string(containsString(track1.getTrackReleaseDate().toString())))
                .andExpect(content().string(containsString(track1.getTrackTempo().toString())))

                .andExpect(content().string(containsString(track2.getTrackBandName())))
                .andExpect(content().string(containsString(track2.getTrackId().toString())))
                .andExpect(content().string(containsString(track2.getTrackLink())))
                .andExpect(content().string(containsString(track2.getTrackDetails())))
                .andExpect(content().string(containsString(track2.getTrackBandName())))
                .andExpect(content().string(containsString(track2.getTrackDuration().toString())))
                .andExpect(content().string(containsString(track2.getTrackReleaseDate().toString())))
                .andExpect(content().string(containsString(track2.getTrackTempo().toString())));

    }

    @Test
    void fillFakeTracksDto() throws Exception {
        LOGGER.debug("fillFakeTracksDto()");

        Integer size = 2;
        String language = "EN";
        TrackDto track1 = createTrackDto(1);
        TrackDto track2 = createTrackDto(2);
        List<TrackDto> trackDtoList = Arrays.asList(track1, track2);

        when(tracksApi.fillTracksDtoFake(size, language)).thenReturn(trackDtoList);

        this.mockMvc.perform(get("/repertoire/fill?size={size}&language={language}", size, language)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(model().attribute("tracks", trackDtoList))
                .andExpect(content().string(containsString(track1.getTrackName())))
                .andExpect(content().string(containsString(track1.getTrackId().toString())))
                .andExpect(content().string(containsString(track1.getTrackLink())))
                .andExpect(content().string(containsString(track1.getTrackDetails())))
                .andExpect(content().string(containsString(track1.getTrackBandName())))
                .andExpect(content().string(containsString(track1.getTrackDuration().toString())))
                .andExpect(content().string(containsString(track1.getTrackReleaseDate().toString())))
                .andExpect(content().string(containsString(track1.getTrackTempo().toString())))

                .andExpect(content().string(containsString(track2.getTrackBandName())))
                .andExpect(content().string(containsString(track2.getTrackId().toString())))
                .andExpect(content().string(containsString(track2.getTrackLink())))
                .andExpect(content().string(containsString(track2.getTrackDetails())))
                .andExpect(content().string(containsString(track2.getTrackBandName())))
                .andExpect(content().string(containsString(track2.getTrackDuration().toString())))
                .andExpect(content().string(containsString(track2.getTrackReleaseDate().toString())))
                .andExpect(content().string(containsString(track2.getTrackTempo().toString())));

    }

    @Test
    void gotoBandTracksPage() throws Exception {
        LOGGER.debug("gotoBandTracksPage()");

        Integer id = 1;
        TrackDto track1 = createTrackDto(1);
        TrackDto track2 = createTrackDto(2);
        List<TrackDto> trackDtoList = Arrays.asList(track1, track2);

        when(tracksApi.findAllTracksWithBandNameByBandId(id)).thenReturn(trackDtoList);

        this.mockMvc.perform(get("/repertoire/filter/band/{id}", id)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(model().attribute("tracks", trackDtoList))
                .andExpect(content().string(containsString(track1.getTrackName())))
                .andExpect(content().string(containsString(track1.getTrackId().toString())))
                .andExpect(content().string(containsString(track1.getTrackLink())))
                .andExpect(content().string(containsString(track1.getTrackDetails())))
                .andExpect(content().string(containsString(track1.getTrackBandName())))
                .andExpect(content().string(containsString(track1.getTrackDuration().toString())))
                .andExpect(content().string(containsString(track1.getTrackReleaseDate().toString())))
                .andExpect(content().string(containsString(track1.getTrackTempo().toString())))

                .andExpect(content().string(containsString(track2.getTrackBandName())))
                .andExpect(content().string(containsString(track2.getTrackId().toString())))
                .andExpect(content().string(containsString(track2.getTrackLink())))
                .andExpect(content().string(containsString(track2.getTrackDetails())))
                .andExpect(content().string(containsString(track2.getTrackBandName())))
                .andExpect(content().string(containsString(track2.getTrackDuration().toString())))
                .andExpect(content().string(containsString(track2.getTrackReleaseDate().toString())))
                .andExpect(content().string(containsString(track2.getTrackTempo().toString())));

    }

    @Test
    void filterTrackByReleaseDate() throws Exception {
        LOGGER.debug("filterTrackByReleaseDate()");

        TrackDto track1 = createTrackDto(1);
        TrackDto track2 = createTrackDto(2);
        List<TrackDto> trackDtoList = Arrays.asList(track1, track2);
        String fromDate = "2000-10-10";
        String toDate = "2021-10-10";

        when(tracksApi.findAllTracksWithReleaseDateFilter(LocalDate.parse(fromDate), LocalDate.parse(toDate)))
                .thenReturn(trackDtoList);

        this.mockMvc.perform(get("/repertoire/filter?fromDate={fromDate}&toDate={toDate}", fromDate, toDate))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(model().attribute("tracks", trackDtoList))
                .andExpect(content().string(containsString(track1.getTrackName())))
                .andExpect(content().string(containsString(track1.getTrackId().toString())))
                .andExpect(content().string(containsString(track1.getTrackLink())))
                .andExpect(content().string(containsString(track1.getTrackDetails())))
                .andExpect(content().string(containsString(track1.getTrackBandName())))
                .andExpect(content().string(containsString(track1.getTrackDuration().toString())))
                .andExpect(content().string(containsString(track1.getTrackReleaseDate().toString())))
                .andExpect(content().string(containsString(track1.getTrackTempo().toString())))

                .andExpect(content().string(containsString(track2.getTrackBandName())))
                .andExpect(content().string(containsString(track2.getTrackId().toString())))
                .andExpect(content().string(containsString(track2.getTrackLink())))
                .andExpect(content().string(containsString(track2.getTrackDetails())))
                .andExpect(content().string(containsString(track2.getTrackBandName())))
                .andExpect(content().string(containsString(track2.getTrackDuration().toString())))
                .andExpect(content().string(containsString(track2.getTrackReleaseDate().toString())))
                .andExpect(content().string(containsString(track2.getTrackTempo().toString())));

    }

    @Test
    void shouldExportTracksDtoToExcel() throws Exception {
        LOGGER.debug("shouldExportTracksDtoToExcel()");
        List<TrackDto> trackDtoList = Arrays.asList(createTrackDto(1), createTrackDto(2));
        TrackController trackController = new TrackController(trackService, bandService, trackDtoService,
                trackDtoFakerService, trackValidator);
        ReflectionTestUtils.setField(trackController, "trackDtoList", trackDtoList);
        ModelAndView mav = trackController.exportToExcel();
        assertEquals(trackDtoList, mav.getModel().get("tracks"));
        MockHttpServletResponse response = mockMvc.perform(get("/repertoire/export/excel"))
                .andExpect(status().isOk())
                .andReturn().getResponse();
        assertNotNull(response);
        assertEquals(response.getContentType(), "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        assertEquals(response.getHeader("Content-disposition"), "attachment;fileName=Repertoire.xlsx");
    }

    @Test
    void shouldExportTrackTableToExcel() throws Exception {
        LOGGER.debug("shouldExportTrackTableToExcel()");
        File file = new File("src/test/resources/Track.xlsx");
        when(trackApi.exportToExcelAllTracks()).thenReturn(file);
        mockMvc.perform(get("/track/export/excel"))
                .andExpect(status().isOk()).andExpect(content()
                        .contentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
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
}