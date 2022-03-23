package com.epam.brest.service.config;

import com.epam.brest.SpringJdbcConfig;
import com.epam.brest.dao.TrackDao;
import com.epam.brest.dao.TrackDaoJdbcImpl;
import com.epam.brest.dao.TrackDtoDao;
import com.epam.brest.dao.TrackDtoDaoJdbcImpl;
import com.epam.brest.service.excel.*;
import com.epam.brest.service.TrackDtoService;
import com.epam.brest.service.TrackService;
import com.epam.brest.service.impl.TrackDtoServiceImpl;
import com.epam.brest.service.impl.TrackServiceImpl;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@TestConfiguration
@ComponentScan("com.epam.brest.dao.annotation")
public class TrackServiceTestConfig extends SpringJdbcConfig {

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

    @Bean
    TrackExportExcelService trackExportExcelService() {
        return new TrackExportExcelServiceImpl(trackService());
    }

    @Bean
    TrackDtoExportExcelService trackDtoExportExcelService() {
        return new TrackDtoExportExcelServiceImpl(trackDtoService());
    }

    @Bean
    TrackImportExcelService trackImportExcel() {
        return new TrackImportExcelServiceServiceImpl(trackService());
    }
}
