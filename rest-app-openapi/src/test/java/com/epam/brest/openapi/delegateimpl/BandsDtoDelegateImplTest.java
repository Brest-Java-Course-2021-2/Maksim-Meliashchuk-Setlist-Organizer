package com.epam.brest.openapi.delegateimpl;

import com.epam.brest.model.BandDto;
import com.epam.brest.openapi.api.BandsDtoApiController;
import com.epam.brest.service.BandDtoService;
import com.epam.brest.service.excel.BandDtoExportExcelService;
import com.epam.brest.service.faker.BandDtoFakerService;
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

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class BandsDtoDelegateImplTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(BandsDtoDelegateImplTest.class);

    public static final int FAKE_DATA_SIZE = 2;

    @InjectMocks
    private BandsDtoDelegateImpl bandsDtoDelegate;

    @Mock
    private BandDtoService bandDtoService;

    @Mock
    private BandDtoFakerService bandDtoFakerService;

    @Mock
    private BandDtoExportExcelService bandDtoExportExcelService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(new BandsDtoApiController(bandsDtoDelegate))
                .build();
    }

    @AfterEach
    public void end() {
        Mockito.verifyNoMoreInteractions(bandDtoService);
        Mockito.verifyNoMoreInteractions(bandDtoFakerService);
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

    @Test
    public void shouldFillFakeBandsWithCountTrack() throws Exception {
        LOGGER.debug("shouldFillFakeBandsWithCountTrack()");

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
    public void shouldBandsDtoExportExcel() throws Exception {
        LOGGER.debug("shouldBandsDtoExportExcel()");
        List<BandDto> bandList = Arrays.asList(create(1), create(2));

        when(bandDtoExportExcelService.exportBandsDtoExcel(any(HttpServletResponse.class))).thenReturn(bandList);
        mockMvc.perform(get("/bands_dto/export/excel"))
                .andDo(print())
                .andExpect(content().contentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-disposition", "attachment; filename=BandsDto.xlsx"))
                .andReturn().getResponse();
        verify(bandDtoExportExcelService).exportBandsDtoExcel(any(HttpServletResponse.class));
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
