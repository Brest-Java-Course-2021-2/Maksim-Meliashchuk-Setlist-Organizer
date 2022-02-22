package com.epam.brest.web_app;

import com.epam.brest.model.Band;
import com.epam.brest.model.BandDto;
import com.epam.brest.service.BandService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.client.ExpectedCount.once;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("dev")
class BandControllerIT {

    private static final String BANDS_DTO_URL = "http://localhost:8088/bands_dto";
    private static final String BANDS_URL = "http://localhost:8088/bands";


    private final Logger logger = LogManager.getLogger(BandControllerIT.class);

    @Autowired
    private WebApplicationContext wac;

    private BandService bandService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    private MockRestServiceServer mockServer;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .alwaysDo(MockMvcResultHandlers.print())
                .build();
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    void shouldReturnBandsPage() throws Exception {
        logger.debug("shouldReturnBandsPage()");

        BandDto band1 = createBandDto(1);
        BandDto band2 = createBandDto(2);

        List<BandDto> bandDtoList = Arrays.asList(band1, band2);

        //WHEN
        mockServer.expect(once(), requestTo(new URI(BANDS_DTO_URL)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(objectMapper.writeValueAsString(bandDtoList))

                );

        // THEN
        mockMvc.perform(MockMvcRequestBuilders.get("/bands")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(view().name("bands"))
                .andExpect(model().attribute("bands", hasItem(
                        allOf(
                                hasProperty("bandId", is(1)),
                                hasProperty("bandName", is("band1")),
                                hasProperty("bandRepertoireDuration", is(1001)),
                                hasProperty("bandDetails", is("band1details1")),
                                hasProperty("bandCountTrack", is(101))
                        )
                )))
                .andExpect(model().attribute("bands", hasItem(
                        allOf(
                                hasProperty("bandId", is(2)),
                                hasProperty("bandName", is("band2")),
                                hasProperty("bandRepertoireDuration", is(1002)),
                                hasProperty("bandDetails", is("band2details2")),
                                hasProperty("bandCountTrack", is(102))
                        )
                )));

        // VERIFY
        mockServer.verify();

    }

    @Test
    void shouldAddBand() throws Exception {
        logger.debug("shouldAddBand()");
        // WHEN
        mockServer.expect(ExpectedCount.once(), requestTo(new URI(BANDS_URL)))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body("1")
                );

        Band band = new Band("band");

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
        mockServer.verify();
    }

    @Test
    public void shouldOpenEditBandPageById() throws Exception {
        logger.debug("shouldOpenEditBandPageById()");

        Band band = Band.builder()
                .bandId(1)
                .bandName("band")
                .build();

        // WHEN
        mockServer.expect(ExpectedCount.once(), requestTo(new URI(BANDS_URL + "/" + band.getBandId())))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(objectMapper.writeValueAsString(band))
                );

        // THEN
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/band/1")
                ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("band"))
                .andExpect(model().attribute("isNew", is(false)))
                .andExpect(model().attribute("band", hasProperty("bandId", is(1))))
                .andExpect(model().attribute("band", hasProperty("bandName", is("band"))));

        // VERIFY
        mockServer.verify();
    }

    @Test
    public void shouldUpdateBandAfterEdit() throws Exception {
        logger.debug("shouldUpdateBandAfterEdit()");

        // WHEN
        mockServer.expect(ExpectedCount.once(), requestTo(new URI(BANDS_URL)))
                .andExpect(method(HttpMethod.PUT))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body("1")
                );
        String testName = "band_test";

        // THEN
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/band/1")
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                .param("bandId", "1")
                                .param("bandName", testName)
                ).andExpect(status().isFound())
                .andExpect(view().name("redirect:/bands"))
                .andExpect(redirectedUrl("/bands"));

        // VERIFY
        mockServer.verify();
    }

    @Test
    public void shouldDeleteBand() throws Exception {
        logger.debug("shouldDeleteBand()");
        int id = 1;

        // WHEN
        mockServer.expect(ExpectedCount.once(), requestTo(new URI(BANDS_URL + "/" + id)))
                .andExpect(method(HttpMethod.DELETE))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body("1")
                );

        // THEN
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/band/1/delete")
                ).andExpect(status().isFound())
                .andExpect(view().name("redirect:/bands"))
                .andExpect(redirectedUrl("/bands"));

        // VERIFY
        mockServer.verify();
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