package com.epam.brest.service.rest;

import com.epam.brest.model.Band;
import com.epam.brest.service.BandServiceRest;
import com.epam.brest.service.config.ServiceRestTestConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

@ExtendWith(SpringExtension.class)
@Import({ServiceRestTestConfig.class})
class BandServiceRestTest {

    private final Logger logger = LogManager.getLogger(BandServiceRestTest.class);

    public static final String BANDS_URL = "http://localhost:8088/bands";

    @Autowired
    private RestTemplate restTemplate;

    private MockRestServiceServer mockServer;

    private ObjectMapper objectMapper = new ObjectMapper();

    private BandServiceRest bandServiceRest;

    @BeforeEach
    public void before() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
        bandServiceRest = new BandServiceRest(BANDS_URL, restTemplate);
    }

    @Test
    void getBandById() throws Exception {
        // given
        Integer id = 1;
        Band band = new Band()
                    .setBandId(id)
                    .setBandName("Test band")
                    .setBandDetails("test band details");

        mockServer.expect(ExpectedCount.once(), requestTo(new URI(BANDS_URL + "/" + id)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(objectMapper.writeValueAsString(band))
                );

        // when
        Band resultBand = bandServiceRest.getBandById(id);

        // then
        mockServer.verify();
        assertNotNull(resultBand);
        assertEquals(resultBand.getBandId(), id);
        assertEquals(resultBand.getBandName(), band.getBandName());
        assertEquals(resultBand.getBandDetails(), band.getBandDetails());
    }

    @Test
    void shouldFindAllBands() throws Exception {
        logger.debug("shouldFindAllBands()");
        // given
        mockServer.expect(ExpectedCount.once(), requestTo(new URI(BANDS_URL)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(objectMapper.writeValueAsString(Arrays.asList(create(0), create(1))))
                );

        // when
        List<Band> bands = bandServiceRest.findAllBands();

        // then
        mockServer.verify();
        assertNotNull(bands);
        assertTrue(bands.size() > 0);
    }

    @Test
    void shouldCreateBand() throws Exception {
        logger.debug("shouldCreateBand()");
        // given
        Band band = new Band();
        mockServer.expect(ExpectedCount.once(), requestTo(new URI(BANDS_URL)))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(objectMapper.writeValueAsString("1"))
                );
        // when
        Integer id = bandServiceRest.create(band);

        // then
        mockServer.verify();
        assertNotNull(id);
    }

    @Test
    void shouldUpdateBand() throws Exception {
        logger.debug("shouldUpdateBand()");
        // given
        Integer id = 1;
        Band band = new Band();
        band.setBandId(id);
        band.setBandName("Test band");
        band.setBandDetails("test band details");

        mockServer.expect(ExpectedCount.once(), requestTo(new URI(BANDS_URL)))
                .andExpect(method(HttpMethod.PUT))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(objectMapper.writeValueAsString("1"))
                );

        mockServer.expect(ExpectedCount.once(), requestTo(new URI(BANDS_URL + "/" + id)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(objectMapper.writeValueAsString(band))
                );

        // when
        int result = bandServiceRest.update(band);
        Band resultBand = bandServiceRest.getBandById(id);

        // then
        mockServer.verify();
        assertTrue(1 == result);
        assertNotNull(resultBand);
        assertEquals(resultBand.getBandId(), id);
        assertEquals(resultBand.getBandName(), band.getBandName());
        assertEquals(resultBand.getBandDetails(), band.getBandDetails());

    }

    @Test
    void shouldDeleteBand() throws Exception {
        logger.debug("shouldDeleteBand()");
        // given
        Integer id = 1;
        mockServer.expect(ExpectedCount.once(), requestTo(new URI(BANDS_URL + "/" + id)))
                .andExpect(method(HttpMethod.DELETE))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(objectMapper.writeValueAsString("1"))
                );
        // when
        int result = bandServiceRest.delete(id);

        // then
        mockServer.verify();
        assertTrue(1 == result);
    }

    @Test
    void shouldCount() throws Exception {
        logger.debug("shouldCount()");
        mockServer.expect(ExpectedCount.once(), requestTo(new URI(BANDS_URL + "/count")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(objectMapper.writeValueAsString("1"))
                );
        // when
        int result = bandServiceRest.count();

        // then
        mockServer.verify();
        assertTrue(result> 0);
    }

    private Band create(int index) {
        Band band = new Band();
        band.setBandId(index);
        band.setBandName("band" + index);
        band.setBandDetails(band.getBandName() + "details" + index);
        return band;
    }
}