package com.epam.brest.web_app.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

@Configuration
@Profile("dev")
@PropertySource("classpath:application-dev.properties")
public class WebClientConfig {

    @Value("${spring.jackson.date-format}")
    private String dateFormat;

    @Bean
    public ObjectMapper objectMapper() {
        DateTimeFormatterBuilder dateTimeFormatterBuilder = new DateTimeFormatterBuilder()
                .appendOptional(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                .appendOptional(DateTimeFormatter.ofPattern(dateFormat));
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
        builder.deserializers(new LocalDateDeserializer(dateTimeFormatterBuilder.toFormatter()));
        return builder.build();
    }

    @Bean
    public WebClient webClient(@Value("${base.url}") String baseUrl, ObjectMapper objectMapper) {
        return WebClient.builder()
                .baseUrl(baseUrl)
                .codecs(clientDefaultCodecsConfig -> {
                    clientDefaultCodecsConfig
                            .defaultCodecs().jackson2JsonEncoder(new Jackson2JsonEncoder(objectMapper,
                                    MediaType.APPLICATION_JSON));
                    clientDefaultCodecsConfig
                            .defaultCodecs().jackson2JsonDecoder(new Jackson2JsonDecoder(objectMapper,
                                    MediaType.APPLICATION_JSON));
                })
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
}