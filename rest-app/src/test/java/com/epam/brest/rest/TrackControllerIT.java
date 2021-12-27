package com.epam.brest.rest;

import com.epam.brest.model.Track;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@Transactional
public class TrackControllerIT {

    private final Logger logger = LogManager.getLogger(TrackControllerIT.class);

    public static final String REPERTOIRE_ENDPOINT = "/repertoire";

    @Autowired
    private TrackController trackController;

    ObjectMapper objectMapper = JsonMapper.builder()
            .addModule(new JavaTimeModule())
            .build();

    private MockMvc mockMvc;

    private MockMvcTrackService trackService = new MockMvcTrackService();

    @BeforeEach
    public void before() {
        mockMvc = MockMvcBuilders.standaloneSetup(trackController)
                .setMessageConverters(new MappingJackson2HttpMessageConverter())
                .alwaysDo(MockMvcResultHandlers.print())
                .build();
    }

    @Test
    @Transactional
    public void shouldFindAllTracks() throws Exception {
        logger.debug("shouldFindAllTracks()");

        // given
        Track track = new Track("Test track");
        Integer id = trackService.create(track);

        // when
        List<Track> tracks = trackService.findAll();

        // then
        assertNotNull(tracks);
        assertTrue(tracks.size() > 0);
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
        List<Track> tracks = trackService.findAll();
        Track trackSrc = tracks.get(0);
        Integer id = trackService.create(trackSrc);
        assertNotNull(id);

        Optional<Track> trackOptionalSrc = trackService.findById(id);
        assertTrue(trackOptionalSrc.isPresent());

        trackOptionalSrc.get().setTrackName("Test track#");
        trackOptionalSrc.get().setTrackName(trackSrc.getTrackName() + "#");
        trackOptionalSrc.get().setTrackDetails(trackSrc.getTrackDetails() + "#");
        trackOptionalSrc.get().setTrackTempo(trackSrc.getTrackTempo() + 1);
        trackOptionalSrc.get().setTrackLink(trackSrc.getTrackLink() + "#");
        trackOptionalSrc.get().setTrackDuration(trackSrc.getTrackDuration() + 1);
        trackOptionalSrc.get().setTrackReleaseDate(trackSrc.getTrackReleaseDate().plusMonths(1));
        trackOptionalSrc.get().setTrackBandId(trackSrc.getTrackBandId() - 1);

        // when
        int result = trackService.update(trackOptionalSrc.get());
        trackService.update(trackSrc);
        Optional<Track> trackOptionalDst = trackService.findById(trackSrc.getTrackId());
        assertEquals(trackSrc.getTrackName(), trackOptionalDst.get().getTrackName());
        assertEquals(trackSrc.getTrackDetails(), trackOptionalDst.get().getTrackDetails());
        assertEquals(trackSrc.getTrackTempo(), trackOptionalDst.get().getTrackTempo());
        assertEquals(trackSrc.getTrackLink(), trackOptionalDst.get().getTrackLink());
        assertEquals(trackSrc.getTrackDuration(), trackOptionalDst.get().getTrackDuration());
        assertEquals(trackSrc.getTrackReleaseDate(), trackOptionalDst.get().getTrackReleaseDate());
        assertEquals(trackSrc.getTrackBandId(), trackOptionalDst.get().getTrackBandId());

        // then
        assertTrue(1 == result);

        Optional<Track> updatedTrackOptional = trackService.findById(id);
        assertTrue(updatedTrackOptional.isPresent());
        assertEquals(updatedTrackOptional.get().getTrackId(), id);
        assertEquals(updatedTrackOptional.get().getTrackName(),trackOptionalSrc.get().getTrackName());
    }

    @Test
    @Transactional
    public void shouldDeleteTrack() throws Exception {
        logger.debug("shouldDeleteTrack()");
        // given
        Track track = new Track("Test track");
        Integer id = trackService.create(track);

        List<Track> tracks = trackService.findAll();
        assertNotNull(tracks);

        // when
        int result = trackService.delete(id);

        // then
        assertTrue(1 == result);

        List<Track> currentTrack = trackService.findAll();
        assertNotNull(currentTrack);

        assertTrue(tracks.size()-1 == currentTrack.size());
    }


    class MockMvcTrackService {

        public List<Track> findAll() throws Exception {
            logger.debug("findAll()");
            MockHttpServletResponse response = mockMvc.perform(get(REPERTOIRE_ENDPOINT)
                            .accept(MediaType.APPLICATION_JSON)
                    ).andExpect(status().isOk())
                    .andReturn().getResponse();
            assertNotNull(response);

            return objectMapper.readValue(response.getContentAsString(), new TypeReference<List<Track>>() {
            });
        }

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

        private int delete(Integer trackId) throws Exception {

            logger.debug("delete(id:{})", trackId);
            MockHttpServletResponse response = mockMvc.perform(
                            MockMvcRequestBuilders.delete(new StringBuilder(REPERTOIRE_ENDPOINT).append("/")
                                            .append(trackId).toString())
                                    .accept(MediaType.APPLICATION_JSON)
                    ).andExpect(status().isOk())
                    .andReturn().getResponse();

            return objectMapper.readValue(response.getContentAsString(), Integer.class);
        }

    }

}
