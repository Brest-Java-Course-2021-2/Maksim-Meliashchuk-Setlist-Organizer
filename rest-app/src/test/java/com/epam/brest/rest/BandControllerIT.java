package com.epam.brest.rest;

import com.epam.brest.exception.CustomExceptionHandler;
import com.epam.brest.exception.ErrorResponse;
import com.epam.brest.model.Band;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.epam.brest.exception.CustomExceptionHandler.BAND_NOT_FOUND;
import static com.epam.brest.exception.CustomExceptionHandler.NOT_UNIQUE_ERROR;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@Transactional
public class BandControllerIT {

    private final Logger logger = LogManager.getLogger(BandControllerIT.class);

    public static final String BANDS_ENDPOINT = "/bands";

    @Autowired
    private BandController bandController;

    @Autowired
    private CustomExceptionHandler customExceptionHandler;

    private ObjectMapper objectMapper = new ObjectMapper();

    private MockMvc mockMvc;

    private MockMvcBandService bandService = new MockMvcBandService();

    @BeforeEach
    public void before() {
        mockMvc = MockMvcBuilders.standaloneSetup(bandController)
                .setMessageConverters(new MappingJackson2HttpMessageConverter())
                .setControllerAdvice(customExceptionHandler)
                .alwaysDo(MockMvcResultHandlers.print())
                .build();
    }

    @Test
    @Transactional
    public void shouldFindAllBands() throws Exception {
        logger.debug("shouldFindAllBands()");

        // given
        Band band = new Band("Test band");
        Integer id = bandService.create(band);

        // when
        List<Band> bands = bandService.findAll();

        // then
        assertNotNull(bands);
        assertTrue(bands.size() > 0);
    }

    @Test
    @Transactional
    public void shouldCreateBand() throws Exception {
        logger.debug("shouldCreateBand()");
        Band band = new Band("Test band");
        Integer id = bandService.create(band);
        assertNotNull(id);
    }

    @Test
    @Transactional
    public void shouldFindBandById() throws Exception {
        logger.debug("shouldFindBandById()");
        // given
        Band band = new Band("Test band");
        Integer id = bandService.create(band);

        assertNotNull(id);

        // when
        Optional<Band> optionalBand = bandService.findById(id);

        // then
        assertTrue(optionalBand.isPresent());
        assertEquals(optionalBand.get().getBandId(), id);
        assertEquals(band.getBandName().toUpperCase(), optionalBand.get().getBandName());
    }

    @Test
    @Transactional
    public void shouldUpdateBand() throws Exception {
        logger.debug("shouldUpdateBand()");
        // given
        Band band = new Band("Test band");
        Integer id = bandService.create(band);
        assertNotNull(id);

        Optional<Band> bandOptional = bandService.findById(id);
        assertTrue(bandOptional.isPresent());

        bandOptional.get().
                setBandName("Test band#");
        bandOptional.get().setBandDetails("Test band details#");


        // when
        int result = bandService.update(bandOptional.get());

        // then
        assertTrue(1 == result);

        Optional<Band> updatedBandOptional = bandService.findById(id);
        assertTrue(updatedBandOptional.isPresent());
        assertEquals(updatedBandOptional.get().getBandId(), id);
        assertEquals(updatedBandOptional.get().getBandName(),bandOptional.get().getBandName().toUpperCase());
        assertEquals(updatedBandOptional.get().getBandDetails(),bandOptional.get().getBandDetails());

    }



    @Test
    @Transactional
    public void shouldDeleteBand() throws Exception {
        logger.debug("shouldDeleteBand()");
        // given
        Band band = new Band("Test band");
        Integer id = bandService.create(band);

        List<Band> bands = bandService.findAll();
        assertNotNull(bands);

        // when
        int result = bandService.delete(id);

        // then
        assertTrue(1 == result);

        List<Band> currentBand = bandService.findAll();
        assertNotNull(currentBand);

        assertTrue(bands.size()-1 == currentBand.size());
    }

    @Test
    @Transactional
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
    @Transactional
    public void shouldFailOnCreateBandWithDuplicateName() throws Exception {
        logger.debug("shouldFailOnCreateBandWithDuplicateName()");
        Band band1 = new Band("Test band1");
        Integer id = bandService.create(band1);
        assertNotNull(id);

        Band band2 = new Band("Test band2");

        MockHttpServletResponse response =
                mockMvc.perform(post(BANDS_ENDPOINT)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(band1.getBandName()))
                                .accept(MediaType.APPLICATION_JSON)
                        ).andExpect(status().isUnprocessableEntity())
                        .andReturn().getResponse();

        assertNotNull(response);
        ErrorResponse errorResponse = objectMapper.readValue(response.getContentAsString(), ErrorResponse.class);
        assertNotNull(errorResponse);
        assertEquals(errorResponse.getMessage(), NOT_UNIQUE_ERROR);
    }


    class MockMvcBandService {

        public List<Band> findAll() throws Exception {
            logger.debug("findAll()");
            MockHttpServletResponse response = mockMvc.perform(get(BANDS_ENDPOINT)
                            .accept(MediaType.APPLICATION_JSON)
                    ).andExpect(status().isOk())
                    .andReturn().getResponse();
            assertNotNull(response);

            return objectMapper.readValue(response.getContentAsString(), new TypeReference<List<Band>>() {
            });
        }

        public Optional<Band> findById(Integer id) throws Exception {

            logger.debug("findById({})", id);
            MockHttpServletResponse response = mockMvc.perform(get(BANDS_ENDPOINT + "/" + id)
                            .accept(MediaType.APPLICATION_JSON)
                    ).andExpect(status().isOk())
                    .andReturn().getResponse();
            return Optional.of(objectMapper.readValue(response.getContentAsString(), Band.class));
        }

        public Integer create(Band band) throws Exception {

            logger.debug("create({})", band);
            String json = objectMapper.writeValueAsString(band);
            MockHttpServletResponse response =
                    mockMvc.perform(post(BANDS_ENDPOINT)
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
                    mockMvc.perform(put(BANDS_ENDPOINT)
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
                            MockMvcRequestBuilders.delete(new StringBuilder(BANDS_ENDPOINT).append("/")
                                            .append(bandId).toString())
                                    .accept(MediaType.APPLICATION_JSON)
                    ).andExpect(status().isOk())
                    .andReturn().getResponse();

            return objectMapper.readValue(response.getContentAsString(), Integer.class);
        }

    }



}


