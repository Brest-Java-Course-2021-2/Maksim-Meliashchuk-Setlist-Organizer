package com.epam.brest.service.rest;

import com.epam.brest.model.BandDto;
import com.epam.brest.service.BandDtoFakerServiceRest;
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
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

@ExtendWith(SpringExtension.class)
@Import({ServiceRestTestConfig.class})
class BandDtoFakerServiceRestTest {
    private final Logger logger = LogManager.getLogger(BandDtoFakerServiceRestTest.class);

    public static final String URL_SEARCH = "http://localhost:8088/bands_dto/fill?size=2&language=EN";

    public static final String URL = "http://localhost:8088/bands_dto/fill";

    @Autowired
    private RestTemplate restTemplate;

    private MockRestServiceServer mockServer;

    private final ObjectMapper mapper = new ObjectMapper();

    private BandDtoFakerServiceRest bandDtoFakerServiceRest;

    @BeforeEach
    public void before() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
        bandDtoFakerServiceRest = new BandDtoFakerServiceRest(URL, restTemplate);
    }

    @Test
    void shouldFillFakeBandsDtoTest() throws Exception {
        logger.debug("fillFakeBandsDto()");
        Integer size = 2;

        mockServer.expect(ExpectedCount.once(), requestTo(new URI(URL_SEARCH)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(Arrays.asList(create(0), create(1)))));

        List<BandDto> list = bandDtoFakerServiceRest.fillFakeBandsDto(size, "EN");

        mockServer.verify();
        assertNotNull(list);
        assertEquals(size, list.size());
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