package com.epam.brest.web_app;

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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:application-context-test.xml"})
@Transactional
class BandControllerIT {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    void shouldReturnBandsPage() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/bands")
        ).andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("bands"))
                .andExpect(model().attribute("bands", hasItem(
                allOf(
                        hasProperty("bandId", is(1)),
                        hasProperty("bandName", is("MY COVER BAND")),
                        hasProperty("countTrack", is(2))
                )
        )))
                .andExpect(model().attribute("bands", hasItem(
                        allOf(
                                hasProperty("bandId", is(2)),
                                hasProperty("bandName", is("MY BAND")),
                                hasProperty("countTrack", is(1))
                        )
                )))
                .andExpect(model().attribute("bands", hasItem(
                        allOf(
                                hasProperty("bandId", is(3)),
                                hasProperty("bandName", is("MUSE")),
                                hasProperty("countTrack", is(0))
                        )
                )));

    }
}