package com.epam.brest.openapi.delegateimpl;

import com.epam.brest.model.BandDto;
import com.epam.brest.openapi.api.BandsDtoApiController;
import com.epam.brest.service.BandDtoService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

@ExtendWith(MockitoExtension.class)
public class BandsDtoDelegateImplTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(BandsDtoDelegateImplTest.class);

    @InjectMocks
    private BandsDtoDelegateImpl bandsDtoDelegate;

    @Mock
    private BandDtoService bandDtoService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(new BandsDtoApiController(bandsDtoDelegate))
                .build();
    }

    @AfterEach
    public void end() {
        Mockito.verifyNoMoreInteractions(bandDtoService);
    }

    @Test
    public void shouldFindAllWithCountTrack() throws Exception {
        LOGGER.debug("shouldFindAllWithCountTrack()");

        Mockito.when(bandDtoService.findAllWithCountTrack()).thenReturn(Arrays.asList(create(0), create(1)));

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/bands_dto")
                ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].bandId", Matchers.is(0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].bandName", Matchers.is("band0")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].bandCountTrack", Matchers.is(100)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].bandDetails", Matchers.is("band0details0")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].bandRepertoireDuration", Matchers.is(1000)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].bandId", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].bandName", Matchers.is("band1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].bandCountTrack", Matchers.is(101)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].bandDetails", Matchers.is("band1details1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].bandRepertoireDuration", Matchers.is(1001)));

        Mockito.verify(bandDtoService).findAllWithCountTrack();
    }

    private BandDto create(int index) {
        BandDto bandDto = new BandDto();
        bandDto.setBandId(index);
        bandDto.setBandName("band" + index);
        bandDto.setBandCountTrack(100 + index);
        bandDto.setBandRepertoireDuration(1000 + index);
        bandDto.setBandDetails(bandDto.getBandName() + "details" + index);
        return bandDto;
    }

}
