package com.epam.brest.delegateimpl;

import com.epam.brest.model.Band;
import com.epam.brest.model.Track;
import com.epam.brest.api.BandsApiController;
import com.epam.brest.service.BandService;
import com.epam.brest.service.excel.BandExportExcelService;
import com.epam.brest.service.excel.BandImportExcelService;
import com.epam.brest.service.faker.BandFakerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
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
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

    @Mock
    private BandExportExcelService bandExportExcelService;

    @Mock
    private BandImportExcelService bandImportExcelService;

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
        int bandId = 1;
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

        int bandId = 1;
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

    @Test
    public void shouldBandsExportExcel() throws Exception {
        LOGGER.debug("shouldBandsExportExcel()");
        List<Band> bandList = Arrays.asList(createBand(1), createBand(2));

        when(bandExportExcelService.exportBandsExcel(any(HttpServletResponse.class))).thenReturn(bandList);
        mockMvc.perform(get("/bands/export/excel"))
                .andDo(print())
                .andExpect(content().contentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-disposition", "attachment; filename=Bands.xlsx"))
                .andReturn().getResponse();
        verify(bandExportExcelService).exportBandsExcel(any(HttpServletResponse.class));
    }

    @Test
    public void shouldBandImportFromExcel() throws Exception {
        LOGGER.debug("shouldBandImportFromExcel()");
        List<Band> bandList = Arrays.asList(createBand(1), createBand(2));

        when(bandImportExcelService.importBandsExcel(any(MockMultipartFile.class))).thenReturn(bandList);

        File files = new File("src/test/resources/Band.xlsx");
        FileInputStream input = new FileInputStream(files);
        MockMultipartFile multipartFile = new MockMultipartFile("file",
                files.getName(), "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
                IOUtils.toByteArray(input));

        MockHttpServletResponse response = mockMvc.perform(multipart("/bands/import/excel").file(multipartFile))
                .andExpect(status().isOk()).andReturn().getResponse();

        assertEquals(Integer.parseInt(response.getContentAsString()), bandList.size());

    }

    private Band createBand(int index) {
        Band band = new Band();
        band.setBandId(index);
        band.setBandName("band" + index);
        band.setBandDetails(band.getBandName() + "details" + index);
        return band;
    }
}