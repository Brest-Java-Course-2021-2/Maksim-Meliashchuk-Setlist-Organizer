package com.epam.brest.web_app.controller;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(HomeController.class)
class HomeControllerTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(HomeControllerTest.class);

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private HomeController controller;

    @Test
    public void contextLoads() throws Exception {
        assertThat(controller).isNotNull();
    }

    @Test
    void defaultPageRedirect() throws Exception {
        LOGGER.debug("defaultPageRedirect()");

        this.mockMvc.perform(get("/")).andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:bands"))
                .andExpect(redirectedUrl("bands"));

    }
}