package com.epam.brest.service.config;

import com.epam.brest.SpringDataSourceTestConfig;
import com.epam.brest.dao.TrackDao;
import com.epam.brest.dao.TrackDtoDao;
import com.epam.brest.dao.jdbc.TrackDaoJdbcImpl;
import com.epam.brest.dao.jdbc.TrackDtoDaoJdbcImpl;
import com.epam.brest.service.TrackDtoService;
import com.epam.brest.service.TrackService;
import com.epam.brest.service.excel.*;
import com.epam.brest.service.impl.jdbc.TrackDtoServiceImpl;
import com.epam.brest.service.impl.jdbc.TrackServiceImpl;
import com.epam.brest.service.xml.TrackExportXmlService;
import com.epam.brest.service.xml.TrackExportXmlServiceImpl;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@TestConfiguration
@ComponentScan("com.epam.brest.dao.annotation")
public class TrackServiceTestConfig extends SpringDataSourceTestConfig {

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
    TrackExportXmlService trackExportXmlService() {
        return new TrackExportXmlServiceImpl(trackService());
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
