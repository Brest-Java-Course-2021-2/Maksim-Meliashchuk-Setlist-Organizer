package com.epam.brest.dao;

import com.epam.brest.dao.jdbc.BandDaoJdbcImpl;
import com.epam.brest.model.Band;
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

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BandDaoJdbcImplTest {
    private final Logger logger = LogManager.getLogger(BandDaoJdbcImplTest.class);

    @InjectMocks
    private BandDaoJdbcImpl bandDaoJdbc;

    @Mock
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Mock
    private JdbcTemplate jdbcTemplate;

    @Captor
    private ArgumentCaptor<RowMapper<Band>> captorMapper;

    @Captor
    private ArgumentCaptor<SqlParameterSource> captorSource;

    @Captor
    private ArgumentCaptor<KeyHolder> captorKeyHolder;

    @AfterEach
    public void check() {
        verifyNoMoreInteractions(namedParameterJdbcTemplate);
    }

    @Test
    public void testFindAllBands() {
        logger.debug("Execute mock test: testFindAllBands()");
        String sql = "select";
        ReflectionTestUtils.setField(bandDaoJdbc, "sqlAllBands", sql);
        Band band = new Band();
        List<Band> list = Collections.singletonList(band);

       when(namedParameterJdbcTemplate.query(any(),
                ArgumentMatchers.<RowMapper<Band>>any())).thenReturn(list);

        List<Band> result = bandDaoJdbc.findAll();

        verify(namedParameterJdbcTemplate).query(eq(sql), captorMapper.capture());

        RowMapper<Band> mapper = captorMapper.getValue();
        Assertions.assertNotNull(mapper);

        Assertions.assertNotNull(result);
        Assertions.assertFalse(result.isEmpty());
        Assertions.assertSame(band, result.get(0));
    }

    @Test
    public void testGetBandById() {
        logger.debug("Execute mock test: getBandById()");
        String sql = "get";
        ReflectionTestUtils.setField(bandDaoJdbc, "sqlBandById", sql);
        int id = 0;
        Band band = new Band();

        when(namedParameterJdbcTemplate.queryForObject(any(), ArgumentMatchers.<SqlParameterSource>any(),
                ArgumentMatchers.<RowMapper<Band>>any())).thenReturn(band);

        Band result = bandDaoJdbc.getBandById(id);

        verify(namedParameterJdbcTemplate)
                .queryForObject(eq(sql), captorSource.capture(), captorMapper.capture());

        SqlParameterSource source = captorSource.getValue();
        RowMapper<Band> mapper = captorMapper.getValue();

        Assertions.assertNotNull(source);
        Assertions.assertNotNull(mapper);

        Assertions.assertNotNull(result);
        Assertions.assertSame(band, result);
    }

    @Test
    public void testUpdateBand() {
        logger.debug("Execute mock test: testUpdateBand()");
        String sql = "update";
        ReflectionTestUtils.setField(bandDaoJdbc, "sqlUpdateBandById", sql);
        int id = 0;
        Band band = Band.builder()
                .bandId(id)
                .bandName("Gods Tower")
                .bandDetails("Band of metal")
                .build();
        List<Band> bandList = new ArrayList<>();

        when(namedParameterJdbcTemplate.update(any(), ArgumentMatchers.<SqlParameterSource>any()))
                .thenReturn(id);

        Integer resultId = bandDaoJdbc.update(band);

        verify(namedParameterJdbcTemplate)
                .update(eq(sql), captorSource.capture());

       SqlParameterSource source = captorSource.getValue();

        Assertions.assertNotNull(source);
        Assertions.assertNotNull(resultId);
        Assertions.assertSame(band.getBandId(), resultId);

    }

    @Test
    public void testCreateBand() {
        logger.debug("Execute mock test: testCreateBand()");
        String sql = "create";
        ReflectionTestUtils.setField(bandDaoJdbc, "sqlCreateBand", sql);
        String sqlCheck = "check";
        ReflectionTestUtils.setField(bandDaoJdbc, "sqlCheckBand", sqlCheck);
        int id = 1;
        int count = 1;
        Band band = Band.builder()
                .bandName("Gods Tower")
                .bandDetails("Band of metal")
                .build();
        List<Band> bandList = new ArrayList<>();

        when(namedParameterJdbcTemplate.query(any(), ArgumentMatchers.<SqlParameterSource>any(),
                ArgumentMatchers.<RowMapper<Band>>any())).thenReturn(bandList);

        when(namedParameterJdbcTemplate.update(any(), ArgumentMatchers.any(),
                ArgumentMatchers.any(), any())).thenAnswer(invocation ->  {
            Object[] args = invocation.getArguments();
            Map<String, Object> keyMap = new HashMap<>();
            keyMap.put("", id);
            band.setBandId(id);
            ((KeyHolder)args[2]).getKeyList().add(keyMap);
            return count;
        });

        Integer result = bandDaoJdbc.create(band);

        verify(namedParameterJdbcTemplate).query(eq(sqlCheck), captorSource.capture(), captorMapper.capture());

        verify(namedParameterJdbcTemplate).update(eq(sql), captorSource.capture(), captorKeyHolder.capture(), any());

        SqlParameterSource source = captorSource.getValue();
        KeyHolder keyHolder = captorKeyHolder.getValue();
        RowMapper<Band> mapper = captorMapper.getValue();

        Assertions.assertNotNull(source);
        Assertions.assertNotNull(mapper);
        Assertions.assertNotNull(result);
        Assertions.assertNotNull(keyHolder);
        Assertions.assertSame(band.getBandId(), result);
    }

    @Test
    public void testDeleteBand() {
        String sql = "delete";
        ReflectionTestUtils.setField(bandDaoJdbc, "sqlDeleteBandById", sql);
        int id = 0;
        when(namedParameterJdbcTemplate.update(any(), ArgumentMatchers.<SqlParameterSource>any()))
                .thenReturn(id);

        Integer result = bandDaoJdbc.delete(id);

        verify(namedParameterJdbcTemplate).update(eq(sql), captorSource.capture());

        SqlParameterSource source = captorSource.getValue();
        Assertions.assertNotNull(source);
        Assertions.assertNotNull(result);
        Assertions.assertSame(result, id);
    }

    @Test
    public void testDeleteAllBands() {
        String sql = "deleteAllBands";
        ReflectionTestUtils.setField(bandDaoJdbc, "sqlDeleteAllBands", sql);
        ReflectionTestUtils.setField(bandDaoJdbc, "sqlResetStartBandId", sql);
        int count = 3;

        when(namedParameterJdbcTemplate.getJdbcTemplate())
                .thenReturn(jdbcTemplate);

        when(jdbcTemplate.update(eq(sql))).thenReturn(count);

        Integer result = bandDaoJdbc.deleteAllBands();

        verify(jdbcTemplate).update(eq(sql));
        verify(jdbcTemplate).execute(eq(sql));

        Assertions.assertNotNull(result);
        Assertions.assertSame(result, count);
    }

    @Test
    public void testCountBand() {
        logger.debug("Execute mock test: testCountBand()");
        String sql = "count";
        ReflectionTestUtils.setField(bandDaoJdbc, "sqlSelectCountFromBand", sql);
        int count = 1;
        when(namedParameterJdbcTemplate.queryForObject(any(), ArgumentMatchers.<SqlParameterSource>any(),
                eq(Integer.class))).thenReturn(count);

        Integer result = bandDaoJdbc.count();

        verify(namedParameterJdbcTemplate).queryForObject(eq(sql), captorSource.capture(), eq(Integer.class));

        SqlParameterSource source = captorSource.getValue();
        Assertions.assertNotNull(source);
        Assertions.assertNotNull(result);
        Assertions.assertSame(result, count);
    }

}
