package com.epam.brest.rest;

import com.epam.brest.model.Track;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//TODO add all fields

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath:application-context-test.xml"})
public class TrackControllerIT {

    private final Logger logger = LogManager.getLogger(TrackControllerIT.class);

    public static final String REPERTOIRE_ENDPOINT = "/repertoire";

    @Autowired
    private TrackController trackController;

    ObjectMapper objectMapper = new ObjectMapper();

    private MockMvc mockMvc;

    MockMvcTrackService trackService = new MockMvcTrackService();

    @BeforeEach
    public void before() {
        mockMvc = MockMvcBuilders.standaloneSetup(trackController)
                .setMessageConverters(new MappingJackson2HttpMessageConverter())
                .alwaysDo(MockMvcResultHandlers.print())
                .build();
    }

    @Test
    @Transactional
    public void shouldFindTrackById() throws Exception {
        logger.debug("shouldFindTrackById()");
        // given
        Track track = new Track("Test track");
        Integer id = trackService.create(track);

        assertNotNull(id);

        // when
        Optional<Track> optionalTrack = trackService.findById(id);

        // then
        assertTrue(optionalTrack.isPresent());
        assertEquals(optionalTrack.get().getTrackId(), id);
        assertEquals(track.getTrackName(), optionalTrack.get().getTrackName());
    }

    @Test
    @Transactional
    public void shouldCreateTrack() throws Exception {
        logger.debug("shouldCreateTrack()");
        Track track = new Track("Test track");
        Integer id = trackService.create(track);
        assertNotNull(id);
    }

    @Test
    @Transactional
    public void shouldUpdateTrack() throws Exception {
        logger.debug("shouldUpdateTrack()");
        // given
        Track track = new Track("Test track");
        Integer id = trackService.create(track);
        assertNotNull(id);

        Optional<Track> trackOptional = trackService.findById(id);
        assertTrue(trackOptional.isPresent());

        trackOptional.get().setTrackName("Test track#");

        // when
        int result = trackService.update(trackOptional.get());

        // then
        assertTrue(1 == result);

        Optional<Track> updatedTrackOptional = trackService.findById(id);
        assertTrue(updatedTrackOptional.isPresent());
        assertEquals(updatedTrackOptional.get().getTrackName(), id);
        assertEquals(updatedTrackOptional.get().getTrackName(),trackOptional.get().getTrackName());

    }

    class MockMvcTrackService {

        public Optional<Track> findById(Integer id) throws Exception {

            logger.debug("findById({})", id);
            MockHttpServletResponse response = mockMvc.perform(get(REPERTOIRE_ENDPOINT + "/" + id)
                            .accept(MediaType.APPLICATION_JSON)
                    ).andExpect(status().isOk())
                    .andReturn().getResponse();
            return Optional.of(objectMapper.readValue(response.getContentAsString(), Track.class));
        }
        public Integer create(Track track) throws Exception {

            logger.debug("create({})", track);
            String json = objectMapper.writeValueAsString(track);
            MockHttpServletResponse response =
                    mockMvc.perform(post(REPERTOIRE_ENDPOINT)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(json)
                                    .accept(MediaType.APPLICATION_JSON)
                            ).andExpect(status().isOk())
                            .andReturn().getResponse();
            return objectMapper.readValue(response.getContentAsString(), Integer.class);
        }

        private int update(Track track) throws Exception {

            logger.debug("update({})", track);
            MockHttpServletResponse response =
                    mockMvc.perform(put(REPERTOIRE_ENDPOINT)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(objectMapper.writeValueAsString(track))
                                    .accept(MediaType.APPLICATION_JSON)
                            ).andExpect(status().isOk())
                            .andReturn().getResponse();
            return objectMapper.readValue(response.getContentAsString(), Integer.class);
        }

    }

}
