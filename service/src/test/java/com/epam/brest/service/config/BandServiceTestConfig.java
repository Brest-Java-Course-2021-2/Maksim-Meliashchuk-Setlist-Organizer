package com.epam.brest.service.config;

import com.epam.brest.SpringJdbcConfig;
import com.epam.brest.dao.BandDao;
import com.epam.brest.dao.BandDaoJdbcImpl;
import com.epam.brest.dao.BandDtoDao;
import com.epam.brest.dao.BandDtoDaoJdbcImpl;
import com.epam.brest.service.BandDtoService;
import com.epam.brest.service.BandService;
import com.epam.brest.service.excel.*;
import com.epam.brest.service.impl.BandDtoServiceImpl;
import com.epam.brest.service.impl.BandServiceImpl;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.multipart.MultipartFile;

@TestConfiguration
@ComponentScan("com.epam.brest.dao.annotation")
public class BandServiceTestConfig extends SpringJdbcConfig {

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
    BandExportExcelService bandExportExcelService() {
        return new BandExportExcelServiceImpl(bandService());
    }

    @Bean
    BandDtoExportExcelService bandDtoExportExcelService() {
        return new BandDtoExportExcelServiceImpl(bandDtoService());
    }

    @Bean
    BandImportExcelService bandImportExcelService() {
        return new BandImportExcelServiceImpl(bandService());
    }

}
