package com.epam.brest.web_app.config;

import com.epam.brest.ApiClient;
import com.epam.brest.CustomJSON;
import com.epam.brest.JSON;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

@Configuration
public class ApiClientConfig {

    @Value("${spring.jackson.date-format}")
    private String dateFormat;

    @Bean
    public ApiClient apiClient() {
        DateTimeFormatterBuilder dateTimeFormatterBuilder = new DateTimeFormatterBuilder()
                .appendOptional(DateTimeFormatter.ofPattern(dateFormat));
        JSON json = new CustomJSON().setLocalDateDeserializeFormat(dateTimeFormatterBuilder.toFormatter());
        return new ApiClient().setJSON(json);
    }
}
