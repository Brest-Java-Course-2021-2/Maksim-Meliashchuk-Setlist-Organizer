package com.epam.brest.service.config;

import com.epam.brest.SpringJdbcConfig;
import com.epam.brest.dao.*;
import com.epam.brest.service.BandDtoService;
import com.epam.brest.service.BandService;
import com.epam.brest.service.TrackDtoService;
import com.epam.brest.service.TrackService;
import com.epam.brest.service.impl.BandDtoServiceImpl;
import com.epam.brest.service.impl.BandServiceImpl;
import com.epam.brest.service.impl.TrackDtoServiceImpl;
import com.epam.brest.service.impl.TrackServiceImpl;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class ServiceTestConfig extends SpringJdbcConfig {

    @Bean
    BandDtoDao bandDtoDao() {
        return new BandDtoDaoJdbcImpl(namedParameterJdbcTemplate());
    }

    @Bean
    BandDtoService bandDtoService() {
        return new BandDtoServiceImpl(bandDtoDao());
    }

    @Bean
    BandDao bandDao() {
        return new BandDaoJdbcImpl(namedParameterJdbcTemplate());
    }

    @Bean
    BandService bandService() {
        return new BandServiceImpl(bandDao());
    }

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
