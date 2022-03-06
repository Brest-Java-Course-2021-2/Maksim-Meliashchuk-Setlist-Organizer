package com.epam.brest.web_app.controller;

import com.epam.brest.model.Band;
import com.epam.brest.model.BandDto;
import com.epam.brest.web_app.validator.BandValidator;
import io.swagger.client.api.BandApi;
import io.swagger.client.api.BandsApi;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(BandControllerApiClient.class)
@ComponentScan({"com.epam.brest.web_app.validator"})
class BandControllerApiClientTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(BandControllerApiClient.class);

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BandsApi bandsApi;

    @MockBean
    private BandApi bandApi;

    @MockBean
    private BandValidator bandValidator;

    @Test
    void bands() throws Exception {
        LOGGER.debug("bands()");

        BandDto band1 = createBandDto(1);
        BandDto band2 = createBandDto(2);
        List<BandDto> bandDtoList = Arrays.asList(band1, band2);

        when(bandsApi.bandsDto()).thenReturn(bandDtoList);

        this.mockMvc.perform(get("/bands")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(model().attribute("bands", bandDtoList))
                .andExpect(content().string(containsString(band1.getBandName())))
                .andExpect(content().string(containsString(band2.getBandName())));

    }

    @Test
    void fakeBands() throws Exception {
        LOGGER.debug("fakeBands()");

        BandDto band1 = createBandDto(1);
        BandDto band2 = createBandDto(2);
        List<BandDto> bandDtoList = Arrays.asList(band1, band2);
        Integer size = 2;
        String language = "EN";

        when(bandsApi.fillBandsDtoFake(size, language)).thenReturn(bandDtoList);

        this.mockMvc.perform(get("/bands/fill?size={size}&language={language}", size, language)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(model().attribute("bands", bandDtoList))
                .andExpect(content().string(containsString(band1.getBandName())))
                .andExpect(content().string(containsString(band2.getBandName())));

    }

    @Test
    void gotoAddBandPage() throws Exception {
        LOGGER.debug("gotoAddBandPage()");

        this.mockMvc.perform(get("/band")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(model().attribute("isNew", is(true)))
                .andExpect(content().string(containsString("New band")));

    }

    @Test
    void gotoEditBandPage() throws Exception {
        LOGGER.debug("gotoEditBandPage()");

        Integer id = 1;
        Band band = Band.builder()
                .bandId(id)
                .bandName("Test band")
                .bandDetails("Test band details")
                .build();

        when(bandApi.getBandById(id)).thenReturn(band);

        this.mockMvc.perform(get("/band/{id}", id)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(model().attribute("band", band))
                .andExpect(model().attribute("isNew", is(false)))
                .andExpect(content().string(containsString(band.getBandId().toString())))
                .andExpect(content().string(containsString(band.getBandName())))
                .andExpect(content().string(containsString(band.getBandDetails())));

    }

    @Test
    void addBand() throws Exception {
        LOGGER.debug("addBand()");

        Integer id = 1;
        Band band = Band.builder()
                .bandId(id)
                .bandName("Test band")
                .bandDetails("Test band details")
                .build();

        when(bandApi.createBand(band)).thenReturn(band.getBandId());

        this.mockMvc.perform(post("/band").contentType(MediaType.APPLICATION_FORM_URLENCODED)).andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/bands"))
                .andExpect(redirectedUrl("/bands"));
    }

    @Test
    void updateBand() throws Exception {
        LOGGER.debug("updateBand()");

        Integer id = 1;
        Band band = Band.builder()
                .bandId(id)
                .bandName("Test band")
                .bandDetails("Test band details")
                .build();

        when(bandApi.updateBand(band)).thenReturn(band.getBandId());

        this.mockMvc.perform(post("/band/{id}", band.getBandId())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)).andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/bands"))
                .andExpect(redirectedUrl("/bands"));

    }

    @Test
    void deleteBandById() throws Exception {
        LOGGER.debug("deleteBandById()");

        Integer id = 1;
        Band band = Band.builder()
                .bandId(id)
                .bandName("Test band")
                .bandDetails("Test band details")
                .build();

        when(bandApi.updateBand(band)).thenReturn(band.getBandId());

        this.mockMvc.perform(get("/band/{id}/delete", band.getBandId())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)).andDo(print())
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/bands"))
                .andExpect(redirectedUrl("/bands"));

    }

    private BandDto createBandDto(int index) {
        BandDto bandDto = new BandDto();
        bandDto.setBandId(index);
        bandDto.setBandName("band" + index);
        bandDto.setBandCountTrack(100 + index);
        bandDto.setBandRepertoireDuration(1000 + index);
        bandDto.setBandDetails(bandDto.getBandName() + "details" + index);
        return bandDto;
    }
}
