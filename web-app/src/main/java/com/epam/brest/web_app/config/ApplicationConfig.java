package com.epam.brest.web_app.config;

import com.epam.brest.service.*;
import com.epam.brest.web_app.condition.RestTemplateCondition;
import com.epam.brest.web_app.condition.WebClientCondition;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

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

    private final RestTemplate restTemplate;

    private final WebClient webClient;

    public ApplicationConfig(RestTemplate restTemplate, WebClient webClient) {
        this.webClient = webClient;
        this.restTemplate = restTemplate;
    }

    @Bean
    @Conditional(RestTemplateCondition.class)
    BandDtoService bandDtoServiceRestTemplate() {
        String url = String.format("%s://%s:%d/bands_dto", protocol, host, port);
        return new BandDtoServiceRest(url, restTemplate);
    }

    @Bean
    @Conditional(WebClientCondition.class)
    BandDtoService bandDtoServiceWebClient() {
        return new BandDtoServiceWebClient("/bands_dto", webClient);
    }

    @Bean
    @Conditional(RestTemplateCondition.class)
    BandService bandServiceRestTemplate() {
        String url = String.format("%s://%s:%d/bands", protocol, host, port);
        return new BandServiceRest(url, restTemplate);
    }

    @Bean
    @Conditional(WebClientCondition.class)
    BandService bandServiceWebClient() {
        return new BandServiceWebClient("/bands", webClient);
    }

    @Bean
    @Conditional(RestTemplateCondition.class)
    TrackService trackService() {
        String url = String.format("%s://%s:%d/repertoire", protocol, host, port);
        return new TrackServiceRest(url, restTemplate);
    }

    @Bean
    @Conditional(WebClientCondition.class)
    TrackService trackServiceWebClient() {
        return new TrackServiceWebClient("/repertoire", webClient);
    }

    @Bean
    @Conditional(RestTemplateCondition.class)
    TrackDtoService trackDtoService() {
        String url = String.format("%s://%s:%d/repertoire/filter", protocol, host, port);
        return new TrackDtoServiceRest(url, restTemplate);
    }

    @Bean
    @Conditional(WebClientCondition.class)
    TrackDtoService trackDtoServiceWebClient() {
        return new TrackDtoServiceWebClient("/repertoire/filter", webClient);
    }

}
