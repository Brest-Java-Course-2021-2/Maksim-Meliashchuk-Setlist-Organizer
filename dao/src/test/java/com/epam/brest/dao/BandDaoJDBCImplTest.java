package com.epam.brest.dao;

import com.epam.brest.model.Band;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.*;

@ExtendWith(MockitoExtension.class)
public class BandDaoJDBCImplTest {
    private final Logger logger = LogManager.getLogger(BandDaoJDBCImplTest.class);

    @InjectMocks
    private BandDaoJDBCImpl bandDaoJDBC;

    @Mock
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Test
    public void testFindAll() {
        logger.debug("Execute mock test: findAll()");
        Band band = new Band();
        List<Band> list = Collections.singletonList(band);
        Mockito.when(namedParameterJdbcTemplate.query(any(),
                ArgumentMatchers.<RowMapper<Band>>any())).thenReturn(list);
        List<Band> result = bandDaoJDBC.findAll();
        Assertions.assertNotNull(result);
        Assertions.assertFalse(result.isEmpty());
        Assertions.assertSame(band, result.get(0));
    }

    @Test
    public void testGetBandById() {
        logger.debug("Execute mock test: getBandById()");
        Band band = new Band();
        band.setBandId(1);
        Mockito.when(namedParameterJdbcTemplate.queryForObject(any(), any(MapSqlParameterSource.class),
                ArgumentMatchers.<RowMapper<Band>>any())).thenReturn(band);
        Band result = bandDaoJDBC.getBandById(1);
        Assertions.assertNotNull(result);
        Assertions.assertSame(band.getBandId(), result.getBandId());
    }

}
