package com.epam.brest.web_app;

import com.epam.brest.model.Band;
import com.epam.brest.service.BandService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Disabled
class BandControllerIT {

    private final Logger logger = LogManager.getLogger(BandControllerIT.class);

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private BandService bandService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    void shouldReturnBandsPage() throws Exception {
        logger.debug("shouldReturnBandsPage()");
        mockMvc.perform(
                MockMvcRequestBuilders.get("/bands")
        ).andDo(MockMvcResultHandlers.print()).andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(content().string(containsString("An easy way to organize your repertoire!")))
                .andExpect(view().name("bands"))
                .andExpect(model().attribute("bands", hasItem(
                allOf(
                        hasProperty("bandId", is(1)),
                        hasProperty("bandName", is("MY COVER BAND")),
                        hasProperty("bandCountTrack", is(2))
                )
        )))
                .andExpect(model().attribute("bands", hasItem(
                        allOf(
                                hasProperty("bandId", is(2)),
                                hasProperty("bandName", is("MY BAND")),
                                hasProperty("bandCountTrack", is(1))
                        )
                )))
                .andExpect(model().attribute("bands", hasItem(
                        allOf(
                                hasProperty("bandId", is(3)),
                                hasProperty("bandName", is("MUSE")),
                                hasProperty("bandCountTrack", is(0))
                        )
                )));

    }

    @Test
    void shouldAddBand() throws Exception {
        logger.debug("shouldAddBand()");
        // WHEN
        assertNotNull(bandService);
        Integer bandsSizeBefore = bandService.count();
        assertNotNull(bandsSizeBefore);
        Band band = new Band("Vicious Crusade");

        // THEN
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/band")
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                .param("bandName", band.getBandName())
                ).andDo(MockMvcResultHandlers.print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/bands"))
                .andExpect(redirectedUrl("/bands"));

        // VERIFY
        assertEquals(bandsSizeBefore, bandService.count() - 1);
    }

    @Test
    public void shouldOpenEditBandPageById() throws Exception {
        logger.debug("shouldOpenEditBandPageById()");
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/band/1")
                ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("band"))
                .andExpect(model().attribute("isNew", is(false)))
                .andExpect(model().attribute("band", hasProperty("bandId", is(1))))
                .andExpect(model().attribute("band", hasProperty("bandName", is("MY COVER BAND"))));
    }

    @Test
    public void shouldUpdateBandAfterEdit() throws Exception {
        logger.debug("shouldUpdateBandAfterEdit()");
        String testName = "test band";
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/band/1")
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                .param("bandId", "1")
                                .param("bandName", testName)
                                .param("bandCountTrack", "2")
                ).andExpect(status().isFound())
                .andExpect(view().name("redirect:/bands"))
                .andExpect(redirectedUrl("/bands"));

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/bands")
                ).andDo(MockMvcResultHandlers.print()).andExpect(status().isOk())
                .andExpect(content().string(containsString(testName.toUpperCase())))
                .andExpect(model().attribute("bands", hasItem(
                        allOf(
                                hasProperty("bandId", is(1)),
                                hasProperty("bandName", is(testName.toUpperCase())),
                                hasProperty("bandCountTrack", is(2))))));


        Band band = bandService.getBandById(1);
        assertNotNull(band);
        assertEquals(testName.toUpperCase(), band.getBandName().toUpperCase());
    }

    @Test
    public void shouldDeleteBand() throws Exception {
        logger.debug("shouldDeleteBand()");
        Integer countBefore = bandService.count();

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/band/3/delete")
                ).andExpect(status().isFound())
                .andExpect(view().name("redirect:/bands"))
                .andExpect(redirectedUrl("/bands"));

        // verify database size
        Integer countAfter = bandService.count();
        Assertions.assertEquals(countBefore - 1, countAfter);
    }

    @Test
    void shouldFailAddBandOnEmptyName() throws Exception {
        logger.debug("shouldFailAddBandOnEmptyName()");
        // WHEN
        Band band = new Band("");

        // THEN
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/band")
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                .param("bandName", band.getBandName())
                ).andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(view().name("band"))
                .andExpect(
                        model().attributeHasFieldErrors(
                                "band", "bandName"
                        )
                );
    }

}