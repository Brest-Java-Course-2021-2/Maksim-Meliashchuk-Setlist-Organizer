package com.epam.brest.web_app;

import com.epam.brest.model.Track;
import com.epam.brest.service.TrackService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;


import java.time.LocalDate;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:application-context-test.xml"})
@Transactional
public class TrackControllerIT {

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private TrackService trackService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    void shouldReturnRepertoirePage() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/repertoire")
                ).andDo(MockMvcResultHandlers.print()).andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(content().string(containsString("An easy way to organize your repertoire!")))
                .andExpect(view().name("repertoire"))
                .andExpect(model().attribute("tracks", hasItem(
                        allOf(
                                hasProperty("trackId", is(1)),
                                hasProperty("trackName", is("Track1")),
                                hasProperty("trackBandName", is("MY COVER BAND")),
                                hasProperty("trackTempo", is(120)),
                                hasProperty("trackDuration", is(135)),
                                hasProperty("trackDetails", is("super track1")),
                                hasProperty("trackLink", is("http://spotify.com/sdfc7436w&d"))
                        )
                )));
    }

    @Test
    void shouldAddTrack() throws Exception {
        // WHEN
        assertNotNull(trackService);
        Integer trackSizeBefore = trackService.count();
        assertNotNull(trackSizeBefore);
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
        assertEquals(trackSizeBefore, trackService.count() - 1);
    }

    @Test
    public void shouldOpenEditBTrackPageById() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/track/1")
                ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("track"))
                .andExpect(model().attribute("isNew", is(false)))
                .andExpect(model().attribute("track", hasProperty("trackId", is(1))))
                .andExpect(model().attribute("track", hasProperty("trackName", is("Track1"))))
                .andExpect(model().attribute("track", hasProperty("trackReleaseDate", is(LocalDate.parse("2000-03-12")))))
                .andExpect(model().attribute("track", hasProperty("trackBandId", is(1))))
                .andExpect(model().attribute("track", hasProperty("trackTempo", is(120))))
                .andExpect(model().attribute("track", hasProperty("trackDuration", is(135))))
                .andExpect(model().attribute("track", hasProperty("trackDetails", is("super track1"))))
                .andExpect(model().attribute("track", hasProperty("trackLink", is("http://spotify.com/sdfc7436w&d"))))
                .andExpect(model().attribute("bands", hasItem(
                        allOf(
                                hasProperty("bandId", is(1)),
                                hasProperty("bandName", is("MY COVER BAND"))
                                ))));
    }

    @Test
    public void shouldDeleteTrack() throws Exception {

        Integer countBefore = trackService.count();

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/track/3/delete")
                ).andExpect(status().isFound())
                .andExpect(view().name("redirect:/repertoire"))
                .andExpect(redirectedUrl("/repertoire"));

        // verify database size
        Integer countAfter = trackService.count();
        Assertions.assertEquals(countBefore - 1, countAfter);
    }

}
