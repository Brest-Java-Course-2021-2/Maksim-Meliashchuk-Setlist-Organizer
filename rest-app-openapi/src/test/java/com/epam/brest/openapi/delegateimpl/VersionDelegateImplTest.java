package com.epam.brest.openapi.delegateimpl;

import com.epam.brest.openapi.api.VersionApiController;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
class VersionDelegateImplTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(RepertoireDelegateImplTest.class);

    @InjectMocks
    private VersionDelegateImpl versionDelegate;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(new VersionApiController(versionDelegate))
                .build();
    }

    @Test
    public void version() throws Exception {
        LOGGER.debug("VersionControllerTest version()");
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/version")
                ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("1.0.0")));
    }
}