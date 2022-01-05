package com.epam.brest.web_app.config;

import com.epam.brest.service.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
@ComponentScan
@Profile("heroku")
@PropertySource("classpath:application-heroku.properties")
public class ApplicationDeployToHerokuConfig {

    @Value("${rest.server.protocol}")
    private String protocol;
    @Value("${rest.server.host}")
    private String host;


    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate(new SimpleClientHttpRequestFactory());
    }

    @Bean
    BandDtoService bandDtoService() {
        String url = String.format("%s://%s/bands_dto", protocol, host);
        return new BandDtoServiceRest(url, restTemplate());
    }

    @Bean
    BandService bandService() {
        String url = String.format("%s://%s/bands", protocol, host);
        return new BandServiceRest(url, restTemplate());
    }

    @Bean
    TrackService trackService() {
        String url = String.format("%s://%s/repertoire", protocol, host);
        return new TrackServiceRest(url, restTemplate());
    }

    @Bean
    TrackDtoService trackDtoService() {
        String url = String.format("%s://%s/repertoire/filter", protocol, host);
        return new TrackDtoServiceRest(url, restTemplate());
    }
}
