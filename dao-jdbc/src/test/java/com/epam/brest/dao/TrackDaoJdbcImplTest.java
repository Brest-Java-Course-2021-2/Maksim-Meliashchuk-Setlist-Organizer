package com.epam.brest.dao;

import com.epam.brest.dao.jdbc.TrackDaoJdbcImpl;
import com.epam.brest.model.Track;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
public class TrackDaoJdbcImplTest {
    private final Logger logger = LogManager.getLogger(TrackDaoJdbcImplTest.class);

    @InjectMocks
    private TrackDaoJdbcImpl trackDaoJdbc;

    @Mock
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Mock
    private JdbcTemplate jdbcTemplate;

    @Captor
    private ArgumentCaptor<RowMapper<Track>> captorMapper;

    @Captor
    private ArgumentCaptor<SqlParameterSource> captorSource;

    @Captor
    private ArgumentCaptor<KeyHolder> captorKeyHolder;

    @AfterEach
    public void check() {
        verifyNoMoreInteractions(namedParameterJdbcTemplate);
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
        Track track = Track.builder()
                .trackId(id)
                .trackName("new track")
                .build();

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
    public void testCreateTrack() {
        logger.debug("Execute mock test: testCreateTrack()");
        String sql = "create";
        ReflectionTestUtils.setField(trackDaoJdbc, "sqlCreateTrack", sql);
        int id = 0;
        int count = 1;
        Track track = Track.builder()
                .trackId(id)
                .trackName("new track")
                .build();

        Mockito.when(namedParameterJdbcTemplate.update(any(), ArgumentMatchers.any(),
                ArgumentMatchers.any(), any())).thenAnswer(invocation ->  {
            Object[] args = invocation.getArguments();
            Map<String, Object> keyMap = new HashMap<>();
            keyMap.put("", id);
            ((KeyHolder)args[2]).getKeyList().add(keyMap);
            return count;
        });

        Integer result = trackDaoJdbc.create(track);

        Mockito.verify(namedParameterJdbcTemplate).update(eq(sql), captorSource.capture(), captorKeyHolder.capture(), any());

        SqlParameterSource source = captorSource.getValue();
        KeyHolder keyHolder = captorKeyHolder.getValue();

        Assertions.assertNotNull(result);
        Assertions.assertNotNull(source);
        Assertions.assertNotNull(keyHolder);
        Assertions.assertSame(track.getTrackId(), result);
    }

    @Test
    public void testUpdateTrack() {
        logger.debug("Execute mock test: testUpdateTrack()");
        String sql = "update";
        ReflectionTestUtils.setField(trackDaoJdbc, "sqlUpdateTrackById", sql);
        int id = 0;
        Track track = Track.builder()
                .trackId(id)
                .trackName("new track")
                .build();

        Mockito.when(namedParameterJdbcTemplate.update(any(), ArgumentMatchers.<SqlParameterSource>any()))
                .thenReturn(id);

        Integer resultId = trackDaoJdbc.update(track);

        Mockito.verify(namedParameterJdbcTemplate)
                .update(eq(sql), captorSource.capture());

        SqlParameterSource source = captorSource.getValue();

        Assertions.assertNotNull(source);
        Assertions.assertNotNull(resultId);
        Assertions.assertSame(track.getTrackId(), resultId);

    }

    @Test
    public void testDeleteTrack() {
        String sql = "delete";
        ReflectionTestUtils.setField(trackDaoJdbc, "sqlDeleteTrackById", sql);
        int id = 0;
        Mockito.when(namedParameterJdbcTemplate.update(any(),ArgumentMatchers.<SqlParameterSource>any()))
                .thenReturn(id);

        Integer result = trackDaoJdbc.delete(id);

        Mockito.verify(namedParameterJdbcTemplate).update(eq(sql), captorSource.capture());

        SqlParameterSource source = captorSource.getValue();
        Assertions.assertNotNull(source);
        Assertions.assertNotNull(result);
        Assertions.assertSame(result, id);
    }

    @Test
    public void testDeleteAllTracks() {
        String sql = "deleteAllBands";
        ReflectionTestUtils.setField(trackDaoJdbc, "sqlDeleteAllTracks", sql);
        ReflectionTestUtils.setField(trackDaoJdbc, "sqlResetStartTrackId", sql);
        int count = 3;

        Mockito.when(namedParameterJdbcTemplate.getJdbcTemplate())
                .thenReturn(jdbcTemplate);

        Mockito.when(jdbcTemplate.update(eq(sql))).thenReturn(count);

        Integer result = trackDaoJdbc.deleteAllTracks();

        Mockito.verify(jdbcTemplate).update(eq(sql));
        Mockito.verify(jdbcTemplate).execute(eq(sql));

        Assertions.assertNotNull(result);
        Assertions.assertSame(result, count);
    }

    @Test
    public void testCountTrack() {
        logger.debug("Execute mock test: testCountTrack()");
        String sql = "count";
        ReflectionTestUtils.setField(trackDaoJdbc, "sqlSelectCountFromTrack", sql);
        int count = 1;
        Mockito.when(namedParameterJdbcTemplate.queryForObject(any(), ArgumentMatchers.<SqlParameterSource>any(),
                eq(Integer.class))).thenReturn(count);

        Integer result = trackDaoJdbc.count();

        Mockito.verify(namedParameterJdbcTemplate).queryForObject(eq(sql), captorSource.capture(), eq(Integer.class));

        SqlParameterSource source = captorSource.getValue();
        Assertions.assertNotNull(source);
        Assertions.assertNotNull(result);
        Assertions.assertSame(result, count);

    }

}
