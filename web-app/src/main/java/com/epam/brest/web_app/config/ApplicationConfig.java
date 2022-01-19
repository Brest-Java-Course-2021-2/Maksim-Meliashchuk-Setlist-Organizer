package com.epam.brest.web_app.config;

import com.epam.brest.service.*;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.List;

@Configuration
@ComponentScan
@Profile("dev")
@PropertySource("classpath:application-dev.properties")
public class ApplicationConfig {

    @Value("${rest.server.protocol}")
    private String protocol;
    @Value("${rest.server.host}")
    private String host;
    @Value("${rest.server.port}")
    private Integer port;

    @Value("${spring.jackson.date-format}")
    private String dateFormat;

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate(new SimpleClientHttpRequestFactory());
        List<HttpMessageConverter<?>> converters = new ArrayList<>();
        DateTimeFormatterBuilder dateTimeFormatterBuilder = new DateTimeFormatterBuilder()
                .appendOptional(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                .appendOptional(DateTimeFormatter.ofPattern(dateFormat));
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
        builder.deserializers(new LocalDateDeserializer(dateTimeFormatterBuilder.toFormatter()));
        MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter(builder.build());
        converters.add(jsonConverter);
        restTemplate.setMessageConverters(converters);
        return restTemplate;
    }

    @Bean
    BandDtoService bandDtoService() {
        String url = String.format("%s://%s:%d/bands_dto", protocol, host, port);
        return new BandDtoServiceRest(url, restTemplate());
    }

    @Bean
    BandService bandService() {
        String url = String.format("%s://%s:%d/bands", protocol, host, port);
        return new BandServiceRest(url, restTemplate());
    }

    @Bean
    TrackService trackService() {
        String url = String.format("%s://%s:%d/repertoire", protocol, host, port);
        return new TrackServiceRest(url, restTemplate());
    }

    @Bean
    TrackDtoService trackDtoService() {
        String url = String.format("%s://%s:%d/repertoire/filter", protocol, host, port);
        return new TrackDtoServiceRest(url, restTemplate());
    }
}
