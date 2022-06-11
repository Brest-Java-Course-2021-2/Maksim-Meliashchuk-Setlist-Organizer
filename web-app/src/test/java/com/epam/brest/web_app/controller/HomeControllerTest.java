package com.epam.brest.web_app.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@WithMockUser(username = "admin", roles = { "admin" })
@SpringBootTest
@TestPropertySource(properties = {"spring.security.oauth2.client.provider.keycloak.pre-connection-check: false"})
@TestPropertySource(properties = {"spring.cloud.config.enabled:false"})
@TestPropertySource(properties = {"eureka.client.enabled=false"})
class HomeControllerTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(HomeControllerTest.class);

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FilterChainProxy springSecurityFilterChain;

    @Autowired
    private HomeController controller;

    @Test
    public void contextLoads() {
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

    @Test
    void logoutPage() throws Exception {
        LOGGER.debug("logoutPage()");

        this.mockMvc.perform(get("/logout")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    @Test
    void loginPageAnonymous() throws Exception {
        LOGGER.debug("loginPage()");

        this.mockMvc.perform(get("/login").with(anonymous())).andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("Sign into your account")))
                .andExpect(view().name("login"));
    }

    @Test
    void loginPageLogged() throws Exception {
        LOGGER.debug("loginPageLogged()");

        this.mockMvc.perform(get("/login")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("Hello")))
                .andExpect(view().name("login"));
    }
}