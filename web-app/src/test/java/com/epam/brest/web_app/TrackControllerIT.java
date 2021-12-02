package com.epam.brest.web_app;

import com.epam.brest.service.TrackService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;


import static org.hamcrest.Matchers.*;
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
                                hasProperty("trackBandId", is(1)),
                                hasProperty("trackTempo", is(120)),
                                hasProperty("trackDuration", is(135)),
                                hasProperty("trackDetails", is("super track1")),
                                hasProperty("trackLink", is("http://spotify.com/sdfc7436w&d"))
                        )
                )));

    }
}
