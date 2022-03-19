package com.epam.brest.rest;

import com.epam.brest.model.BandDto;
import com.epam.brest.service.excel.BandDtoExportExcelService;
import com.epam.brest.service.faker.BandDtoFakerService;
import com.epam.brest.service.BandDtoService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class BandDtoControllerTest {

    public static final int FAKE_DATA_SIZE = 2;

    private final Logger logger = LogManager.getLogger(BandDtoControllerTest.class);

    @InjectMocks
    private BandDtoController bandDtoController;

    @Mock
    private BandDtoService bandDtoService;

    @Mock
    private BandDtoFakerService bandDtoFakerService;

    @Mock
    private BandDtoExportExcelService bandDtoExportExcelService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(bandDtoController)
                .build();
    }

    @AfterEach
    public void end() {
        Mockito.verifyNoMoreInteractions(bandDtoService);
    }

    @Test
    public void shouldFindAllWithCountTrack() throws Exception {
        logger.debug("shouldFindAllWithCountTrack()");
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

    @Test
    public void shouldFillFakeBands() throws Exception {
        logger.debug("shouldFillFakeBands()");

        Mockito.when(bandDtoFakerService.fillFakeBandsDto(FAKE_DATA_SIZE, "EN"))
                .thenReturn(Arrays.asList(create(0), create(1)));

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/bands_dto/fill?size=" + FAKE_DATA_SIZE)
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

        Mockito.verify(bandDtoFakerService).fillFakeBandsDto(FAKE_DATA_SIZE, "EN");
    }

    @Test
    public void shouldExportBandsDtoToExcel() throws Exception {
        logger.debug("shouldExportBandsDtoToExcel()");
        Mockito.when(bandDtoExportExcelService.exportBandsDtoExcel(any(HttpServletResponse.class)))
                .thenReturn(Arrays.asList(create(0), create(1)));
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/bands_dto/export/excel")
                ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .andExpect(MockMvcResultMatchers.header()
                        .string("Content-disposition", "attachment; filename=BandsDto.xlsx"));

        Mockito.verify(bandDtoExportExcelService).exportBandsDtoExcel(any(HttpServletResponse.class));
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
