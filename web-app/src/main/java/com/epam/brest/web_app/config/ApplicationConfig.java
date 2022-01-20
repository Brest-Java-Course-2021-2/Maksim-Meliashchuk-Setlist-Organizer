package com.epam.brest.web_app.config;

import com.epam.brest.service.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.web.client.RestTemplate;

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

    private RestTemplate restTemplate;

    public ApplicationConfig(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Bean
    BandDtoService bandDtoService() {
        String url = String.format("%s://%s:%d/bands_dto", protocol, host, port);
        return new BandDtoServiceRest(url, restTemplate);
    }

    @Bean
    BandService bandService() {
        String url = String.format("%s://%s:%d/bands", protocol, host, port);
        return new BandServiceRest(url, restTemplate);
    }

    @Bean
    TrackService trackService() {
        String url = String.format("%s://%s:%d/repertoire", protocol, host, port);
        return new TrackServiceRest(url, restTemplate);
    }

    @Bean
    TrackDtoService trackDtoService() {
        String url = String.format("%s://%s:%d/repertoire/filter", protocol, host, port);
        return new TrackDtoServiceRest(url, restTemplate);
    }
}
