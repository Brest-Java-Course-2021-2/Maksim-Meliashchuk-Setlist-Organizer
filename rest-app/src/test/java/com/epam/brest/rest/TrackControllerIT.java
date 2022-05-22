package com.epam.brest.rest;

import com.epam.brest.exception.CustomExceptionHandler;
import com.epam.brest.model.Track;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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
import org.springframework.security.test.context.support.WithAnonymousUser;
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
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.epam.brest.model.constant.TrackConstant.TRACK_DETAILS_MAX_SIZE;
import static com.epam.brest.model.constant.TrackConstant.TRACK_NAME_MAX_SIZE;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestPropertySource(locations = "classpath:application-integrationtest.yaml")
@WithMockUser(username = "admin", roles = { "admin" })
public class TrackControllerIT {

    private final Logger logger = LogManager.getLogger(TrackControllerIT.class);

    public static final String REPERTOIRE_ENDPOINT = "/repertoire";

    public static final int FAKE_DATA_SIZE = 15;

    @Autowired
    private FilterChainProxy springSecurityFilterChain;

    @Autowired
    private TrackController trackController;

    @Autowired
    private CustomExceptionHandler customExceptionHandler;

    private final ObjectMapper objectMapper = JsonMapper.builder()
            .addModule(new JavaTimeModule())
            .build();

    private MockMvc mockMvc;

    private final MockMvcTrackService trackService = new MockMvcTrackService();

    @BeforeEach
    public void before() {
        mockMvc = MockMvcBuilders.standaloneSetup(trackController)
                .setMessageConverters(new MappingJackson2HttpMessageConverter())
                .setControllerAdvice(customExceptionHandler)
                .alwaysDo(MockMvcResultHandlers.print())
                .apply(SecurityMockMvcConfigurers.springSecurity(springSecurityFilterChain))
                .build();
    }

    @Test
    @Transactional
    public void shouldFindAllTracks() throws Exception {
        logger.debug("shouldFindAllTracks()");

        // given
        Track track = Track.builder()
                .trackId(1)
                .trackBandId(1)
                .trackName("Test track")
                .build();
        Integer id = trackService.create(track);

        // when
        List<Track> tracks = trackService.findAll();

        // then
        assertNotNull(tracks);
        assertTrue(tracks.size() > 0);
    }

    @Test
    @Transactional
    @WithAnonymousUser
    public void shouldNotFindAllTracks() throws Exception {
        logger.debug("shouldNotFindAllTracks()");
        MockHttpServletResponse response = mockMvc.perform(get(REPERTOIRE_ENDPOINT)
                        .accept(MediaType.APPLICATION_JSON)
                ).andExpect(status().is4xxClientError())
                .andReturn().getResponse();
        assertNotNull(response);

    }

    @Test
    public void shouldFillFakeTracks() throws Exception {
        logger.debug("shouldFillFakeBands()");

        // when
        List<Track> tracks = trackService.fillFakeTracks();

        // then
        assertNotNull(tracks);
        assertEquals(FAKE_DATA_SIZE, tracks.size());
    }

    @Test
    @Transactional
    public void shouldFindTrackById() throws Exception {
        logger.debug("shouldFindTrackById()");
        // given
        Track track = Track.builder()
                .trackId(1)
                .trackBandId(1)
                .trackName("Test track")
                .build();
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
        Track track = Track.builder()
                .trackName("Test Track")
                .trackId(1)
                .trackBandId(1)
                .build();
        Integer id = trackService.create(track);
        assertNotNull(id);
    }

    @Test
    @Transactional
    @WithMockUser(username = "user", roles = { "user" })
    public void shouldNotCreateTrack() throws Exception {
        logger.debug("shouldNotCreateTrack()");
        Track track = Track.builder()
                .trackName("Test Track")
                .trackId(1)
                .trackBandId(1)
                .build();
        String json = objectMapper.writeValueAsString(track);
        MockHttpServletResponse response =
                mockMvc.perform(post(REPERTOIRE_ENDPOINT).with(csrf())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                                .accept(MediaType.APPLICATION_JSON)
                        ).andExpect(status().is4xxClientError())
                        .andReturn().getResponse();
    }


    @Test
    @Transactional
    public void shouldCreateNotValidTrack() throws Exception {
        logger.debug("shouldCreateNotValidTrack()");
        Track track = new Track(RandomStringUtils.randomAlphabetic(TRACK_NAME_MAX_SIZE + 1));
        track.setTrackDetails(RandomStringUtils.randomAlphabetic(TRACK_DETAILS_MAX_SIZE + 1));
        track.setTrackDuration(-1);
        track.setTrackTempo(-1);
        track.setTrackBandId(-1);
        track.setTrackLink("test");
        MockHttpServletResponse response =
                mockMvc.perform(post(REPERTOIRE_ENDPOINT).with(csrf())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(track))
                                .contentType(MediaType.APPLICATION_JSON)
                        ).andExpect(status().isBadRequest())
                        .andReturn().getResponse();
        assertNotNull(response);
        assertTrue(response.getContentAsString().contains("Track name size have to be <= 100 symbols!"));
        assertTrue(response.getContentAsString().contains("Track details size have to be <= 2000 symbols!"));
        assertTrue(response.getContentAsString().contains("Band id should be positive"));
        assertTrue(response.getContentAsString().contains("Track tempo cannot be less than zero!"));
        assertTrue(response.getContentAsString().contains("Track duration cannot be less than zero!"));
        assertTrue(response.getContentAsString()
                .contains("Track link is not valid. The link must contain http or https!"));
    }


    @Test
    @Transactional
    public void shouldCreateNotValidEmptyNameTrack() throws Exception {
        logger.debug("shouldCreateNotValidEmptyNameTrack()");
        Track track = new Track("");

        MockHttpServletResponse response =
                mockMvc.perform(post(REPERTOIRE_ENDPOINT).with(csrf())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(track))
                                .accept(MediaType.APPLICATION_JSON)
                        ).andExpect(status().isBadRequest())
                        .andReturn().getResponse();

        assertNotNull(response);
        assertTrue(response.getContentAsString().contains("Please provide track name!"));
    }

    @Test
    @Transactional
    public void shouldUpdateTrack() throws Exception {
        logger.debug("shouldUpdateTrack()");
        // given
        Track trackSrc = Track.builder()
                .trackName("Test Track")
                .trackId(1)
                .trackBandId(1)
                .trackTempo(100)
                .trackDuration(1000)
                .trackLink("https://youtube.com/test")
                .trackReleaseDate(LocalDate.parse("2022-12-01"))
                .build();

        Integer id = trackService.create(trackSrc);

        assertNotNull(id);

        List<Track> tracks = trackService.findAll();

        Optional<Track> trackOptionalSrc = trackService.findById(id);
        assertTrue(trackOptionalSrc.isPresent());

        trackOptionalSrc.get().setTrackName(trackSrc.getTrackName() + "#");
        trackOptionalSrc.get().setTrackDetails(trackSrc.getTrackDetails() + "#");
        trackOptionalSrc.get().setTrackTempo(trackSrc.getTrackTempo() + 1);
        trackOptionalSrc.get().setTrackLink(trackSrc.getTrackLink() + "#");
        trackOptionalSrc.get().setTrackDuration(trackSrc.getTrackDuration() + 1);
        trackOptionalSrc.get().setTrackReleaseDate(trackSrc.getTrackReleaseDate().plusMonths(1));
        trackOptionalSrc.get().setTrackBandId(trackSrc.getTrackBandId() + 1);

        // when
        int result = trackService.update(trackOptionalSrc.get());

        // then
        assertEquals(1, result);
        Optional<Track> updateOptional = trackService.findById(id);
        assertTrue(updateOptional.isPresent());

        assertEquals(updateOptional.get().getTrackId(), id);
        assertEquals(updateOptional.get().getTrackName(), trackOptionalSrc.get().getTrackName());
        assertEquals(updateOptional.get().getTrackDetails(), trackOptionalSrc.get().getTrackDetails());
        assertEquals(updateOptional.get().getTrackTempo(), trackOptionalSrc.get().getTrackTempo());
        assertEquals(updateOptional.get().getTrackLink(), trackOptionalSrc.get().getTrackLink());
        assertEquals(updateOptional.get().getTrackDuration(), trackOptionalSrc.get().getTrackDuration());
        assertEquals(updateOptional.get().getTrackReleaseDate(), trackOptionalSrc.get().getTrackReleaseDate());
        assertEquals(updateOptional.get().getTrackBandId(), trackOptionalSrc.get().getTrackBandId());

        // then
        assertEquals(1, result);

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
        Integer id = 1;

        trackService.create(Track.builder()
                        .trackName("test")
                        .trackId(5)
                        .trackBandId(1)
                .build());
        List<Track> tracks = trackService.findAll();
        assertTrue(tracks.size() > 0);

        // when
        int result = trackService.delete(id);

        // then
        assertEquals(1, result);

    }

    @Test
    public void shouldTracksExportExcel() throws Exception {
        logger.debug("shouldTracksExportExcel()");

        MockHttpServletResponse response =
                mockMvc.perform(MockMvcRequestBuilders.get(REPERTOIRE_ENDPOINT + "/export/excel").with(csrf()))
                        .andExpect(status().isOk())
                        .andReturn().getResponse();
        assertNotNull(response);
        assertEquals(response.getContentType(), "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        assertEquals(response.getHeader("Content-disposition"), "attachment; filename=Tracks.xlsx");
    }

    @Test
    @Transactional
    public void shouldImportTrackExcel() throws Exception {
        logger.debug("shouldImportTrackExcel()");

        File files = new File("src/test/resources/Track.xlsx");
        FileInputStream input = new FileInputStream(files);
        MockMultipartFile multipartFile = new MockMultipartFile("file",
                files.getName(), "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
                IOUtils.toByteArray(input));

        MockHttpServletResponse response = mockMvc.perform(multipart("/repertoire/import/excel")
                        .file(multipartFile).with(csrf()))
                .andExpect(status().isOk()).andReturn().getResponse();
        assertTrue(Integer.parseInt(response.getContentAsString()) > 0);
    }

    @Test
    public void shouldTracksExportXml() throws Exception {
        logger.debug("shouldBandsExportXml()");

        MockHttpServletResponse response =
                mockMvc.perform(MockMvcRequestBuilders.get(REPERTOIRE_ENDPOINT + "/export/xml").with(csrf())
                                .accept(MediaType.APPLICATION_XML))
                        .andExpect(status().isOk())
                        .andReturn().getResponse();
        assertNotNull(response);
        assertEquals(response.getContentType(), "application/xml");
        assertEquals(response.getHeader("Content-disposition"), "attachment; filename=Tracks.xml");
    }


    class MockMvcTrackService {

        public List<Track> findAll() throws Exception {
            logger.debug("findAll()");
            MockHttpServletResponse response = mockMvc.perform(get(REPERTOIRE_ENDPOINT).with(csrf())
                            .accept(MediaType.APPLICATION_JSON)
                    ).andExpect(status().isOk())
                    .andReturn().getResponse();
            assertNotNull(response);

            return objectMapper.readValue(response.getContentAsString(), new TypeReference<>() {
            });
        }

        public List<Track> fillFakeTracks() throws Exception {
            logger.debug("fillFakeTracks()");
            MockHttpServletResponse response = mockMvc.perform(get(REPERTOIRE_ENDPOINT + "/fill?size=" + FAKE_DATA_SIZE).with(csrf())
                            .accept(MediaType.APPLICATION_JSON)
                    ).andExpect(status().isOk())
                    .andReturn().getResponse();
            assertNotNull(response);

            return objectMapper.readValue(response.getContentAsString(), new TypeReference<>() {
            });
        }

        public Optional<Track> findById(Integer id) throws Exception {

            logger.debug("findById({})", id);
            MockHttpServletResponse response = mockMvc.perform(get(REPERTOIRE_ENDPOINT + "/" + id).with(csrf())
                            .accept(MediaType.APPLICATION_JSON)
                    ).andExpect(status().isOk())
                    .andReturn().getResponse();
            return Optional.of(objectMapper.readValue(response.getContentAsString(), Track.class));
        }
        public Integer create(Track track) throws Exception {

            logger.debug("create({})", track);
            String json = objectMapper.writeValueAsString(track);
            MockHttpServletResponse response =
                    mockMvc.perform(post(REPERTOIRE_ENDPOINT).with(csrf())
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
                    mockMvc.perform(put(REPERTOIRE_ENDPOINT).with(csrf())
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
                            MockMvcRequestBuilders.delete(REPERTOIRE_ENDPOINT + "/" +
                                            trackId).with(csrf())
                                    .accept(MediaType.APPLICATION_JSON)
                    ).andExpect(status().isOk())
                    .andReturn().getResponse();

            return objectMapper.readValue(response.getContentAsString(), Integer.class);
        }


    }

}
