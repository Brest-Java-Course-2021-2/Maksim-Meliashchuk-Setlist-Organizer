package com.epam.brest.openapi.delegateimpl;

import com.epam.brest.model.TrackDto;
import com.epam.brest.openapi.api.RepertoireApiController;
import com.epam.brest.service.TrackDtoService;
import com.epam.brest.service.TrackService;
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
public class RepertoireDelegateImplTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(RepertoireDelegateImplTest.class);

    @InjectMocks
    private RepertoireDelegateImpl repertoireDelegate;

    @Mock
    private TrackDtoService trackDtoService;

    @Mock
    private TrackService trackService;

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
    }

    @Test
    public void shouldFindAllTracksWithBandNameByBandId() throws Exception {
        LOGGER.debug("shouldFindAllTracksWithBandNameByBandId()");
        int id = 1;
        Mockito.when(trackDtoService.findAllTracksWithBandNameByBandId(id))
                .thenReturn(Arrays.asList(create(id)));

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


        Mockito.verify(trackDtoService).findAllTracksWithBandNameByBandId(id);
    }

    @Test
    void shouldFindAllTracksWithReleaseDateFilter() throws Exception{
        LOGGER.debug("shouldFindAllTracksWithReleaseDateFilter()");

        Mockito.when(trackDtoService.findAllTracksWithReleaseDateFilter(captorDate.capture(), captorDate.capture()))
                .thenReturn(Arrays.asList(create(0), create(1)));

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

        Mockito.verify(trackDtoService).findAllTracksWithReleaseDateFilter(captorDate.capture(), captorDate.capture());

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
