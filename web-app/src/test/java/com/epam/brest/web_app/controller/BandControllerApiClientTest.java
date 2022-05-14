package com.epam.brest.web_app.controller;

import com.epam.brest.model.Band;
import com.epam.brest.model.BandDto;
import com.epam.brest.service.BandDtoService;
import com.epam.brest.service.BandService;
import com.epam.brest.service.faker.BandDtoFakerService;
import com.epam.brest.web_app.validator.BandValidator;
import io.swagger.client.api.BandApi;
import io.swagger.client.api.BandsApi;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.FileInputStream;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@ComponentScan({"com.epam.brest.web_app.validator"})
@SpringBootTest(properties = { "app.httpClient = ApiClient" })
@WithMockUser(username = "admin", roles = { "admin" })
class BandControllerApiClientTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(BandControllerApiClient.class);

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BandsApi bandsApi;

    @MockBean
    private BandApi bandApi;

    @MockBean
    private BandDtoService bandDtoService;

    @MockBean
    private BandService bandService;

    @MockBean
    private BandDtoFakerService bandDtoFakerService;

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

    @Test
    void shouldExportBandsDtoToExcel() throws Exception {
        LOGGER.debug("shouldExportBandsDtoToExcel()");
        List<BandDto> bandDtoList = Arrays.asList(createBandDto(1), createBandDto(2));
        BandController bandController = new BandController(bandDtoService, bandService, bandDtoFakerService,
                bandValidator);
        ReflectionTestUtils.setField(bandController, "bandDtoList", bandDtoList);
        ModelAndView mav = bandController.exportToExcel();
        assertEquals(bandDtoList, mav.getModel().get("bands"));
        MockHttpServletResponse response = mockMvc.perform(get("/bands/export/excel"))
                .andExpect(status().isOk())
                .andReturn().getResponse();
        assertNotNull(response);
        assertEquals(response.getContentType(), "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        assertEquals(response.getHeader("Content-disposition"), "attachment;fileName=Bands.xlsx");
    }

    @Test
    void shouldImportBandsFromExcel() throws Exception {
        LOGGER.debug("shouldImportBandsFromExcel()");
        File file = new File("src/test/resources/Band.xlsx");
        FileInputStream input = new FileInputStream(file);
        MockMultipartFile mockMultipartFile = new MockMultipartFile("uploadfile",
                file.getName(), "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
                IOUtils.toByteArray(input));
        mockMvc.perform(MockMvcRequestBuilders.multipart("/band/import/excel")
                        .file(mockMultipartFile))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/bands"))
                .andExpect(redirectedUrl("/bands"));
    }

    @Test
    void shouldExportBandTableToExcel() throws Exception {
        LOGGER.debug("shouldExportBandTableToExcel()");
        File file = new File("src/test/resources/Band.xlsx");
        when(bandApi.exportToExcelAllBands()).thenReturn(file);
        mockMvc.perform(get("/band/export/excel"))
                .andExpect(status().isOk()).andExpect(content()
                        .contentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
    }

    @Test
    void shouldExportBandTableToXml() throws Exception {
        LOGGER.debug("shouldExportBandTableToXml()");
        File file = new File("src/test/resources/Band.xml");
        when(bandApi.exportToXmlAllBands()).thenReturn(file);
        mockMvc.perform(get("/band/export/xml"))
                .andExpect(status().isOk()).andExpect(content()
                        .contentType("application/xml"));
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
