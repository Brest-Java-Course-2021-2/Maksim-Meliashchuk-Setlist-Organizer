package com.epam.brest.openapi.delegateimpl;

import com.epam.brest.model.Band;
import com.epam.brest.model.Track;
import com.epam.brest.openapi.api.BandsApiController;
import com.epam.brest.service.faker.BandFakerService;
import com.epam.brest.service.BandService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class BandsDelegateImplTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(BandsDelegateImplTest.class);

    private final ObjectMapper objectMapper = new ObjectMapper();

    public static final int FAKE_DATA_SIZE = 2;

    @InjectMocks
    private BandsDelegateImpl bandsDelegate;

    @Mock
    private BandService bandService;

    @Mock
    private BandFakerService bandFakerService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(new BandsApiController(bandsDelegate))
                .build();
    }

    @AfterEach
    public void end() {
        verifyNoMoreInteractions(bandService);
    }

    @Test
    void shouldGetBandByIdTest() throws Exception {

        LOGGER.debug("shouldGetBandByIdTest()");

        Integer bandId = 1;
        Band band = createBand(bandId);

        when(bandService.getBandById(bandId)).thenReturn(band);
        String responseBody = mockMvc.perform(get("/bands/{bandId}", bandId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();

        Band responseBand = objectMapper.readValue(responseBody, Band.class);

        assertEquals(band, responseBand);
        verify(bandService).getBandById(bandId);
    }

    @Test
    void shouldFindAllBandsTest() throws Exception {

        LOGGER.debug("shouldFindAllBandsTest()");

        List<Band> bandList = Arrays.asList(createBand(1), createBand(2));
        when(bandService.findAllBands()).thenReturn(bandList);

        String responseBody = mockMvc.perform(get("/bands"))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        List<Track> responseList = objectMapper.readValue(
                responseBody,
                objectMapper.getTypeFactory().constructCollectionType(List.class, Band.class));

        assertEquals(bandList, responseList);

        verify(bandService).findAllBands();
    }

    @Test
    void shouldFillFakeBandsTest() throws Exception {

        LOGGER.debug("shouldFillFakeBandsTest()");

        List<Band> bandList = Arrays.asList(createBand(1), createBand(2));
        when(bandFakerService.fillFakeBands(FAKE_DATA_SIZE, "EN")).thenReturn(bandList);

        String responseBody = mockMvc.perform(get("/bands/fill?size=" + FAKE_DATA_SIZE))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        List<Track> responseList = objectMapper.readValue(
                responseBody,
                objectMapper.getTypeFactory().constructCollectionType(List.class, Band.class));

        assertEquals(bandList, responseList);

        verify(bandFakerService).fillFakeBands(FAKE_DATA_SIZE, "EN");
    }

    @Test
    void shouldDeleteBandTest() throws Exception {

        LOGGER.debug("shouldDeleteBandTest()");

        Integer bandId = 1;
        when(bandService.delete(anyInt())).thenReturn(bandId);
        mockMvc.perform(delete("/bands/{trackId}", bandId))
                .andDo(print())
                .andExpect(status().isOk());

        verify(bandService).delete(bandId);
        verifyNoMoreInteractions(bandService);
    }

    @Test
    void shouldCreateBandTest() throws Exception {

        LOGGER.debug("shouldCreateBandTest()");
        Integer bandId = 1;
        Band band = createBand(bandId);
        when(bandService.create(any(Band.class))).thenReturn(bandId);
        String requestBody = objectMapper.writeValueAsString(band);

        mockMvc.perform(MockMvcRequestBuilders.post("/bands")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").value(1));

        verify(bandService).create(band);
    }

    @Test
    void shouldUpdateBandTest() throws Exception {

        LOGGER.debug("shouldUpdateBandTest()");

        Integer bandId = 1;
        Band band = createBand(bandId);
        when(bandService.update(any(Band.class))).thenReturn(bandId);
        String requestBody = objectMapper.writeValueAsString(band);

        mockMvc.perform(MockMvcRequestBuilders.put("/bands")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andDo(print())
                .andExpect(status().isOk());

        verify(bandService).update(band);
    }

    private Band createBand(int index) {
        Band band = new Band();
        band.setBandId(index);
        band.setBandName("band" + index);
        band.setBandDetails(band.getBandName() + "details" + index);
        return band;
    }
}