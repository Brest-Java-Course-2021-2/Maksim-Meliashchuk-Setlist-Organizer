package com.epam.brest.openapi.delegateimpl;

import com.epam.brest.model.Track;
import com.epam.brest.model.TrackDto;
import com.epam.brest.openapi.api.RepertoireApiController;
import com.epam.brest.service.TrackDtoService;
import com.epam.brest.service.faker.TrackFakerService;
import com.epam.brest.service.TrackService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class RepertoireDelegateImplTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(RepertoireDelegateImplTest.class);

    private final ObjectMapper objectMapper = JsonMapper.builder()
            .addModule(new JavaTimeModule())
            .build();

    @InjectMocks
    private RepertoireDelegateImpl repertoireDelegate;

    @Mock
    private TrackDtoService trackDtoService;

    @Mock
    private TrackService trackService;

    @Mock
    private TrackFakerService trackFakerService;

    @Captor
    private ArgumentCaptor<LocalDate> captorDate;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(new RepertoireApiController(repertoireDelegate))
                .build();
    }

    @AfterEach
    public void end() {
        Mockito.verifyNoMoreInteractions(trackDtoService);
        Mockito.verifyNoMoreInteractions(trackService);
        Mockito.verifyNoMoreInteractions(trackFakerService);
    }
    //TODO exportToExcel
    @Test
    public void shouldFindAllTracksWithBandNameByBandId() throws Exception {

        LOGGER.debug("shouldFindAllTracksWithBandNameByBandId()");

        int id = 1;
        when(trackDtoService.findAllTracksWithBandNameByBandId(id))
                .thenReturn(List.of(createTrackDto(id)));

        mockMvc.perform(
                        MockMvcRequestBuilders.get(String.format("/repertoire/filter/band/%d", id))
                ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].trackId", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].trackName", Matchers.is("track1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].trackDetails", Matchers.is("track1details1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].trackDuration", Matchers.is(10001)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].trackBandName", Matchers.is("band1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].trackLink", Matchers.is("link1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].trackReleaseDate[0]",
                        Matchers.is(LocalDate.parse("2013-03-12").getYear())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].trackReleaseDate[1]",
                        Matchers.is(LocalDate.parse("2013-03-12").getMonthValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].trackReleaseDate[2]",
                        Matchers.is(LocalDate.parse("2013-03-12").getDayOfMonth())));


       verify(trackDtoService).findAllTracksWithBandNameByBandId(id);
        verifyNoMoreInteractions(trackDtoService);
    }

    @Test
    void shouldFindAllTracksWithReleaseDateFilter() throws Exception {

        LOGGER.debug("shouldFindAllTracksWithReleaseDateFilter()");

        when(trackDtoService.findAllTracksWithReleaseDateFilter(captorDate.capture(), captorDate.capture()))
                .thenReturn(Arrays.asList(createTrackDto(0), createTrackDto(1)));

            mockMvc.perform(
                            MockMvcRequestBuilders.get("/repertoire/filter")
                    ).andDo(MockMvcResultHandlers.print())
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].trackId", Matchers.is(0)))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].trackName", Matchers.is("track0")))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].trackDetails", Matchers.is("track0details0")))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].trackDuration", Matchers.is(10000)))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].trackBandName", Matchers.is("band0")))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].trackLink", Matchers.is("link0")))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].trackReleaseDate[0]",
                            Matchers.is(LocalDate.parse("2012-03-12").getYear())))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].trackReleaseDate[1]",
                            Matchers.is(LocalDate.parse("2012-03-12").getMonthValue())))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].trackReleaseDate[2]",
                            Matchers.is(LocalDate.parse("2012-03-12").getDayOfMonth())))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[1].trackId", Matchers.is(1)))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[1].trackName", Matchers.is("track1")))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[1].trackDetails", Matchers.is("track1details1")))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[1].trackDuration", Matchers.is(10001)))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[1].trackBandName", Matchers.is("band1")))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[1].trackLink", Matchers.is("link1")))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[1].trackReleaseDate[0]",
                            Matchers.is(LocalDate.parse("2013-03-12").getYear())))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[1].trackReleaseDate[1]",
                            Matchers.is(LocalDate.parse("2013-03-12").getMonthValue())))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[1].trackReleaseDate[2]",
                            Matchers.is(LocalDate.parse("2013-03-12").getDayOfMonth())));

        verify(trackDtoService).findAllTracksWithReleaseDateFilter(captorDate.capture(), captorDate.capture());

        verifyNoMoreInteractions(trackDtoService);
    }

    @Test
    void shouldCreateTrackTest() throws Exception {

        LOGGER.debug("shouldCreateTrackTest()");
        int trackId = 1;
        Track track = createTrack(trackId);
        when(trackService.create(any(Track.class))).thenReturn(trackId);
        String requestBody = objectMapper.writeValueAsString(track);

        mockMvc.perform(MockMvcRequestBuilders.post("/repertoire")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").value(1));

        verify(trackService).create(track);
        verifyNoMoreInteractions(trackService);
    }

    @Test
    void shouldUpdateTrackTest() throws Exception {

        LOGGER.debug("shouldUpdateTrackTest()");

        Integer trackId = 1;
        Track track = createTrack(trackId);
        when(trackService.update(any(Track.class))).thenReturn(trackId);
        String requestBody = objectMapper.writeValueAsString(track);

        mockMvc.perform(MockMvcRequestBuilders.put("/repertoire")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andDo(print())
                .andExpect(status().isOk());

        verify(trackService).update(track);
        verifyNoMoreInteractions(trackService);
    }

    @Test
    public void shouldDeleteTrackTest() throws Exception {

        LOGGER.debug("shouldDeleteTrackTest()");

        Integer trackId = 1;
        when(trackService.delete(anyInt())).thenReturn(trackId);
        mockMvc.perform(delete("/repertoire/{trackId}", trackId))
                .andDo(print())
                .andExpect(status().isOk());

        verify(trackService).delete(trackId);
        verifyNoMoreInteractions(trackService);
    }

    @Test
    public void shouldGetTrackByIdTest() throws Exception {

        LOGGER.debug("shouldGetTrackByIdTest()");

        Integer trackId = 1;
        Track track = createTrack(trackId);

        when(trackService.getTrackById(trackId)).thenReturn(track);
        String responseBody = mockMvc.perform(get("/repertoire/{trackId}", trackId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();

        Track responseTrack = objectMapper.readValue(responseBody, Track.class);

        assertEquals(track, responseTrack);
        verify(trackService).getTrackById(trackId);
        verifyNoMoreInteractions(trackService);
    }

    @Test
    public void shouldFindAllTracksTest() throws Exception {

        LOGGER.debug("shouldFindAllTracksTest()");

        List<Track> trackList = Arrays.asList(createTrack(0), createTrack(1));
        Mockito.when(trackService.findAllTracks())
                .thenReturn(trackList);

        String responseBody = mockMvc.perform(get("/repertoire"))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        List<Track> responseList = objectMapper.readValue(
                responseBody,
                objectMapper.getTypeFactory().constructCollectionType(List.class, Track.class));

        assertEquals(trackList, responseList);

        verify(trackService).findAllTracks();
        verifyNoMoreInteractions(trackService);
    }

    @Test
    public void shouldFillFakeTracksTest() throws Exception {

        LOGGER.debug("shouldFillFakeTracksTest()");

        Integer size = 2;

        List<Track> trackList = Arrays.asList(createTrack(0), createTrack(1));
        Mockito.when(trackFakerService.fillFakeTracks(size, "EN"))
                .thenReturn(trackList);

        String responseBody = mockMvc.perform(get("/repertoire/fill?size=" + size))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        List<Track> responseList = objectMapper.readValue(
                responseBody,
                objectMapper.getTypeFactory().constructCollectionType(List.class, Track.class));

        assertEquals(trackList, responseList);

        verify(trackFakerService).fillFakeTracks(size, "EN");
    }

    private TrackDto createTrackDto(int index) {
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

    private Track createTrack(int index) {
        Track track = new Track();
        LocalDate releaseDate = LocalDate.parse("2012-03-12");
        track.setTrackId(index);
        track.setTrackName("track" + index);
        track.setTrackDuration(10000 + index);
        track.setTrackBandId(index);
        track.setTrackReleaseDate(releaseDate.plusYears(index));
        track.setTrackLink("https://youtube.com" + index);
        track.setTrackDetails(track.getTrackName() + "details" + index);
        return track;
    }
}
