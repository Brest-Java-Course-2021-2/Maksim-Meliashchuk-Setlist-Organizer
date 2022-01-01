package com.epam.brest.dao;

import com.epam.brest.model.Track;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
public class TrackDaoJdbcImplTest {
    private final Logger logger = LogManager.getLogger(TrackDaoJdbcImplTest.class);

    @InjectMocks
    private TrackDaoJdbcImpl trackDaoJdbc;

    @Mock
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Captor
    private ArgumentCaptor<RowMapper<Track>> captorMapper;

    @Captor
    private ArgumentCaptor<SqlParameterSource> captorSource;

    @AfterEach
    public void check() {
        Mockito.verifyNoMoreInteractions(namedParameterJdbcTemplate);
    }

    @Test
    public void testFindAllTracks() {
        logger.debug("Execute mock test: testFindAllTracks()");
        String sql = "select";
        ReflectionTestUtils.setField(trackDaoJdbc, "sqlAllTracks", sql);
        Track track = new Track();
        List<Track> list = Collections.singletonList(track);

        Mockito.when(namedParameterJdbcTemplate.query(any(),
                ArgumentMatchers.<RowMapper<Track>>any())).thenReturn(list);

        List<Track> result = trackDaoJdbc.findAll();

        Mockito.verify(namedParameterJdbcTemplate).query(eq(sql), captorMapper.capture());

        RowMapper<Track> mapper = captorMapper.getValue();
        Assertions.assertNotNull(mapper);

        Assertions.assertNotNull(result);
        Assertions.assertFalse(result.isEmpty());
        Assertions.assertSame(track, result.get(0));
    }

    @Test
    public void testGetTrackById() {
        logger.debug("Execute mock test: testGetTrackById()");
        String sql = "get";
        ReflectionTestUtils.setField(trackDaoJdbc, "sqlTrackById", sql);
        int id = 0;
        Track track = new Track();

        Mockito.when(namedParameterJdbcTemplate.queryForObject(any(), ArgumentMatchers.<SqlParameterSource>any(),
                ArgumentMatchers.<RowMapper<Track>>any())).thenReturn(track);

        Track result = trackDaoJdbc.getTrackById(id);

        Mockito.verify(namedParameterJdbcTemplate)
                .queryForObject(eq(sql), captorSource.capture(), captorMapper.capture());

        SqlParameterSource source = captorSource.getValue();
        RowMapper<Track> mapper = captorMapper.getValue();

        Assertions.assertNotNull(source);
        Assertions.assertNotNull(mapper);

        Assertions.assertNotNull(result);
        Assertions.assertSame(track, result);
    }

    @Test
    public void testUpdateTrack() {
        logger.debug("Execute mock test: testUpdateTrack()");
        String sql = "update";
        ReflectionTestUtils.setField(trackDaoJdbc, "sqlUpdateTrackById", sql);
        int id = 0;
        Track track = new Track("new track").setTrackId(id);

        Mockito.when(namedParameterJdbcTemplate.update(any(), ArgumentMatchers.<SqlParameterSource>any()))
                .thenReturn(id);

        int resultId = trackDaoJdbc.update(track);

        Mockito.verify(namedParameterJdbcTemplate)
                .update(eq(sql), captorSource.capture());

        SqlParameterSource source = captorSource.getValue();

        Assertions.assertNotNull(source);
        Assertions.assertNotNull(resultId);
        Assertions.assertSame(track.getTrackId(), resultId);

    }

}
