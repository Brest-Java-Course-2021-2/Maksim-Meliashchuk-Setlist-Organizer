package com.epam.brest.openapi.delegateimpl;

import com.epam.brest.model.TrackDto;
import com.epam.brest.openapi.api.TracksDtoApiController;
import com.epam.brest.service.faker.TrackDtoFakerService;
import com.epam.brest.service.TrackDtoService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.Arrays;

@ExtendWith(MockitoExtension.class)
public class TracksDtoDelegateImplTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(RepertoireDelegateImplTest.class);

    @InjectMocks
    private TracksDtoDelegateImpl tracksDtoDelegate;

    @Mock
    private TrackDtoService trackDtoService;

    @Mock
    private TrackDtoFakerService trackDtoFakerService;

    @Captor
    private ArgumentCaptor<LocalDate> captorDate;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(new TracksDtoApiController(tracksDtoDelegate))
                .build();
    }

    @AfterEach
    public void end() {
        Mockito.verifyNoMoreInteractions(trackDtoService);
        Mockito.verifyNoMoreInteractions(trackDtoFakerService);

    }
    //TODO exportToExcelAllTracksWithBandName
    @Test
    public void shouldFindAllTracksWithBandName() throws Exception {
        LOGGER.debug("findAllTracksWithBandName()");

        Mockito.when(trackDtoService.findAllTracksWithBandName())
                .thenReturn(Arrays.asList(create(0), create(1)));

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/tracks_dto")
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


        Mockito.verify(trackDtoService).findAllTracksWithBandName();
    }

    @Test
    public void shouldFillFakeTracksWithBandName() throws Exception {

        LOGGER.debug("shouldFillFakeTracksWithBandName()");

        Integer size = 2;

        Mockito.when(trackDtoFakerService.fillFakeTracksDto(size, "EN"))
                .thenReturn(Arrays.asList(create(0), create(1)));

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/tracks_dto/fill?size=" + size)
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


        Mockito.verify(trackDtoFakerService).fillFakeTracksDto(size, "EN");
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
