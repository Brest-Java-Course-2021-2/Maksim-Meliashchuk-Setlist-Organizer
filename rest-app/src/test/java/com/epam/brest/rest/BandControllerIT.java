package com.epam.brest.rest;

import com.epam.brest.exception.CustomExceptionHandler;
import com.epam.brest.model.Band;
import com.epam.brest.model.ErrorResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.Optional;

import static com.epam.brest.exception.CustomExceptionHandler.BAND_NOT_FOUND;
import static com.epam.brest.model.constant.BandConstant.BAND_DETAILS_MAX_SIZE;
import static com.epam.brest.model.constant.BandConstant.BAND_NAME_MAX_SIZE;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestPropertySource(locations = "classpath:application-integrationtest.yaml")
@Transactional
@WithMockUser(username = "admin", roles = { "admin" })
public class BandControllerIT {

    private final Logger logger = LogManager.getLogger(BandControllerIT.class);

    public static final String BANDS_ENDPOINT = "/bands";

    public static final int FAKE_DATA_SIZE = 15;

    @Autowired
    private FilterChainProxy springSecurityFilterChain;

    @Autowired
    private BandController bandController;

    @Autowired
    private CustomExceptionHandler customExceptionHandler;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private MockMvc mockMvc;

    private final MockMvcBandService bandService = new MockMvcBandService();

    @BeforeEach
    public void before() {
        mockMvc = MockMvcBuilders.standaloneSetup(bandController)
                .setMessageConverters(new MappingJackson2HttpMessageConverter())
                .setControllerAdvice(customExceptionHandler)
                .alwaysDo(MockMvcResultHandlers.print())
                .apply(SecurityMockMvcConfigurers.springSecurity(springSecurityFilterChain))
                .build();
    }

    @Test
    public void shouldFindAllBands() throws Exception {
        logger.debug("shouldFindAllBands()");

        // given
        Band band = Band.builder()
                .bandId(5)
                .bandName("Test band")
                .build();

        Integer id = bandService.create(band);

        // when
        List<Band> bands = bandService.findAll();
        System.out.println(bands);

        // then
        assertNotNull(bands);
        assertTrue(bands.size() > 0);
    }

    @Test
    public void shouldFillFakeBands() throws Exception {
        logger.debug("shouldFillFakeBands()");

        // when
        List<Band> bands = bandService.fillFakeBands();

        // then
        assertNotNull(bands);
        assertEquals(FAKE_DATA_SIZE, bands.size());
    }

    @Test
    public void shouldCreateBand() throws Exception {
        logger.debug("shouldCreateBand()");
        Band band = Band.builder()
                .bandId(4)
                .bandName("Test band")
                .build();
        Integer id = bandService.create(band);
        assertNotNull(id);
    }

    @Test
    public void shouldCreateNotValidBand() throws Exception {
        logger.debug("shouldCreateNotValidBand()");
        Band band = Band.builder()
                .bandId(1)
                .bandDetails(RandomStringUtils.randomAlphabetic(BAND_DETAILS_MAX_SIZE + 1))
                .bandName(RandomStringUtils.randomAlphabetic(BAND_NAME_MAX_SIZE + 1))
                .build();

        MockHttpServletResponse response =
                mockMvc.perform(post(BANDS_ENDPOINT).with(csrf())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(band))
                                .accept(MediaType.APPLICATION_JSON)
                        ).andExpect(status().isBadRequest())
                        .andReturn().getResponse();

        assertNotNull(response);
        assertTrue(response.getContentAsString()
                .contains(String.format("Band name size have to be <= %d symbols!", BAND_NAME_MAX_SIZE)));
        assertTrue(response.getContentAsString()
                .contains(String.format("Band details size have to be <= %d symbols!", BAND_DETAILS_MAX_SIZE)));

    }

    @Test
    public void shouldCreateNotValidEmptyNameBand() throws Exception {
        logger.debug("shouldCreateNotValidEmptyNameBand()");
        Band band = Band.builder()
                .bandId(4)
                .bandName("")
                .build();

        MockHttpServletResponse response =
                mockMvc.perform(post(BANDS_ENDPOINT).with(csrf())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(band))
                                .accept(MediaType.APPLICATION_JSON)
                        ).andExpect(status().isBadRequest())
                        .andReturn().getResponse();

        assertNotNull(response);
        assertTrue(response.getContentAsString().contains("Please provide band name!"));
    }

    @Test
    public void shouldFindBandById() throws Exception {
        logger.debug("shouldFindBandById()");
        // given
        Band band = Band.builder()
                .bandId(5)
                .bandName("Test band")
                .build();

        Integer id = bandService.create(band);

        assertNotNull(id);

        // when
        Optional<Band> optionalBand = bandService.findById(id);

        // then
        assertTrue(optionalBand.isPresent());
        assertEquals(optionalBand.get().getBandId(), id);
        assertEquals(band.getBandName().toUpperCase(), optionalBand.get().getBandName().toUpperCase());
    }

    @Test
    public void shouldUpdateBand() throws Exception {
        logger.debug("shouldUpdateBand()");
        // given
        int id = 3;
        Optional<Band> bandOptional = bandService.findById(id);
        assertTrue(bandOptional.isPresent());

        bandOptional.get().
                setBandName("Test band#");
        bandOptional.get().setBandDetails("Test band details#");

        // when
        int result = bandService.update(bandOptional.get());
        Optional<Band> updatedBandOptional = bandService.findById(id);

        // then

        assertTrue(updatedBandOptional.isPresent());
        assertEquals(updatedBandOptional.get().getBandId(), id);
        assertEquals(updatedBandOptional.get().getBandName().toUpperCase(), bandOptional.get().getBandName().toUpperCase());
        assertEquals(updatedBandOptional.get().getBandDetails(), bandOptional.get().getBandDetails());

    }

    @Test
    public void shouldDeleteBand() throws Exception {
        logger.debug("shouldDeleteBand()");
        // given
        Integer id = 2;

        List<Band> bands = bandService.findAll();
        assertNotNull(bands);

        // when
        int result = bandService.delete(id);

        // then
        assertEquals(1, result);

        List<Band> currentBand = bandService.findAll();
        assertNotNull(currentBand);

    }

    @Test
    public void shouldReturnBandNotFoundError() throws Exception {
        logger.debug("shouldReturnBandNotFoundError()");
        MockHttpServletResponse response =
                mockMvc.perform(MockMvcRequestBuilders.get(BANDS_ENDPOINT + "/999999")
                                .accept(MediaType.APPLICATION_JSON)
                        ).andExpect(status().isNotFound())
                        .andReturn().getResponse();
        assertNotNull(response);
        ErrorResponse errorResponse = objectMapper.readValue(response.getContentAsString(), ErrorResponse.class);
        assertNotNull(errorResponse);
        assertEquals(errorResponse.getMessage(), BAND_NOT_FOUND);
    }

    @Test
    public void shouldBandsExportExcel() throws Exception {
        logger.debug("shouldBandsExportExcel()");

        MockHttpServletResponse response =
                mockMvc.perform(MockMvcRequestBuilders.get(BANDS_ENDPOINT + "/export/excel"))
                        .andExpect(status().isOk())
                        .andReturn().getResponse();
        assertNotNull(response);
        assertEquals(response.getContentType(), "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        assertEquals(response.getHeader("Content-disposition"), "attachment; filename=Bands.xlsx");
    }

    @Test
    public void shouldImportBandExcel() throws Exception {
        logger.debug("shouldImportBandExcel()");

        File files = new File("src/test/resources/Band.xlsx");
        FileInputStream input = new FileInputStream(files);
        MockMultipartFile multipartFile = new MockMultipartFile("file",
                files.getName(), "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
                IOUtils.toByteArray(input));

        MockHttpServletResponse response = mockMvc.perform(multipart("/bands/import/excel").file(multipartFile).with(csrf()))
                .andExpect(status().isOk()).andReturn().getResponse();

        assertTrue(Integer.parseInt(response.getContentAsString()) > 0);
    }

    class MockMvcBandService {
        public List<Band> findAll() throws Exception {
            logger.debug("findAll()");
            MockHttpServletResponse response = mockMvc.perform(get(BANDS_ENDPOINT).with(csrf())
                            .accept(MediaType.APPLICATION_JSON)
                    ).andExpect(status().isOk())
                    .andReturn().getResponse();
            assertNotNull(response);

            return objectMapper.readValue(response.getContentAsString(), new TypeReference<>() {
            });
        }

        public List<Band> fillFakeBands() throws Exception {
            logger.debug("fillFakeBands()");
            MockHttpServletResponse response = mockMvc.perform(get(BANDS_ENDPOINT + "/fill?size=" + FAKE_DATA_SIZE).with(csrf())
                            .accept(MediaType.APPLICATION_JSON)
                    ).andExpect(status().isOk())
                    .andReturn().getResponse();
            assertNotNull(response);

            return objectMapper.readValue(response.getContentAsString(), new TypeReference<>() {
            });
        }

        public Optional<Band> findById(Integer id) throws Exception {

            logger.debug("findById({})", id);
            MockHttpServletResponse response = mockMvc.perform(get(BANDS_ENDPOINT + "/" + id).with(csrf())
                            .accept(MediaType.APPLICATION_JSON)
                    ).andExpect(status().isOk())
                    .andReturn().getResponse();
            return Optional.of(objectMapper.readValue(response.getContentAsString(), Band.class));
        }

        public Integer create(Band band) throws Exception {

            logger.debug("create({})", band);
            String json = objectMapper.writeValueAsString(band);
            MockHttpServletResponse response =
                    mockMvc.perform(post(BANDS_ENDPOINT).with(csrf())
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(json)
                                    .accept(MediaType.APPLICATION_JSON)
                            ).andExpect(status().isOk())
                            .andReturn().getResponse();
            return objectMapper.readValue(response.getContentAsString(), Integer.class);
        }

        private int update(Band band) throws Exception {

            logger.debug("update({})", band);
            MockHttpServletResponse response =
                    mockMvc.perform(put(BANDS_ENDPOINT).with(csrf())
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(objectMapper.writeValueAsString(band))
                                    .accept(MediaType.APPLICATION_JSON)
                            ).andExpect(status().isOk())
                            .andReturn().getResponse();
            return objectMapper.readValue(response.getContentAsString(), Integer.class);
        }

        private int delete(Integer bandId) throws Exception {

            logger.debug("delete(id:{})", bandId);
            MockHttpServletResponse response = mockMvc.perform(
                            MockMvcRequestBuilders.delete(BANDS_ENDPOINT + "/" +
                                            bandId).with(csrf())
                                    .accept(MediaType.APPLICATION_JSON)
                    ).andExpect(status().isOk())
                    .andReturn().getResponse();

            return objectMapper.readValue(response.getContentAsString(), Integer.class);
        }
    }


}

