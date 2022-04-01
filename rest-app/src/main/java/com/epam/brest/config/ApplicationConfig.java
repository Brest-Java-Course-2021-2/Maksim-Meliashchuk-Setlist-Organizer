package com.epam.brest.config;

import com.epam.brest.SpringJdbcConfig;
import com.epam.brest.dao.BandDao;
import com.epam.brest.dao.jdbc.BandDaoJdbcImpl;
import com.epam.brest.service.BandService;
import com.epam.brest.service.impl.jdbc.BandServiceImpl;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.time.format.DateTimeFormatter;

@Configuration
@Profile("dev")
public class ApplicationConfig extends SpringJdbcConfig {

    @Value("${spring.jackson.date-format}")
    private String dateFormat;

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer() {
        return builder -> {
            builder.serializers(new LocalDateSerializer(DateTimeFormatter.ofPattern(dateFormat)));
        };
    }

    @Bean
    @Profile("dev")
    BandDao bandDao() {
        return new BandDaoJdbcImpl(namedParameterJdbcTemplate());
    }

    @Bean
    @Profile("dev")
    BandService bandService() {
        return new BandServiceImpl(bandDao());
    }

}
