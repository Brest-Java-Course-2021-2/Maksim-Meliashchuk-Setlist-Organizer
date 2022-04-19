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
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.text.DateFormat;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

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
    ObjectMapper objectMapper() {
        DateTimeFormatterBuilder dateTimeFormatterBuilder = new DateTimeFormatterBuilder()
                .appendOptional(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
        builder.deserializers(new LocalDateDeserializer(dateTimeFormatterBuilder.toFormatter()));
        return builder.build();
    }

    @Bean
    TrackExportXmlService trackExportXmlService() {
        return new TrackExportXmlServiceImpl(trackService(), objectMapper());
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
