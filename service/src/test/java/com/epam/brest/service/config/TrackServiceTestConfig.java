package com.epam.brest.service.config;

import com.epam.brest.SpringJdbcConfig;
import com.epam.brest.dao.TrackDao;
import com.epam.brest.dao.TrackDaoJdbcImpl;
import com.epam.brest.dao.TrackDtoDao;
import com.epam.brest.dao.TrackDtoDaoJdbcImpl;
import com.epam.brest.service.TrackDtoService;
import com.epam.brest.service.TrackService;
import com.epam.brest.service.impl.TrackDtoServiceImpl;
import com.epam.brest.service.impl.TrackServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

@TestConfiguration
public class TrackServiceTestConfig extends SpringJdbcConfig {

/*
    @Autowired
    public DataSource dataSource;

    public TrackServiceTestConfig(DataSource dataSource) {
        super(dataSource);
    }
*/

    @Bean
    TrackDtoDao trackDtoDao() {
        return new TrackDtoDaoJdbcImpl(namedParameterJdbcTemplate());
    }

    @Bean
    TrackDtoService trackDtoService() {
        return new TrackDtoServiceImpl(trackDtoDao());
    }

    @Bean
    TrackDao trackDao() {
        return new TrackDaoJdbcImpl(namedParameterJdbcTemplate());
    }

    @Bean
    TrackService trackService() {
        return new TrackServiceImpl(trackDao());
    }
}
