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
@Profile("heroku")
@PropertySource("classpath:application-heroku.properties")
public class ApplicationHerokuConfig {

    @Value("${rest.server.protocol}")
    private String protocol;
    @Value("${rest.server.host}")
    private String host;

    private final RestTemplate restTemplate;

    private final WebClient webClient;

    public ApplicationHerokuConfig(RestTemplate restTemplate, WebClient webClient) {
        this.restTemplate = restTemplate;
        this.webClient = webClient;
    }

    @Bean
    @Conditional(RestTemplateCondition.class)
    BandDtoService bandDtoService() {
        String url = String.format("%s://%s/bands_dto", protocol, host);
        return new BandDtoServiceRest(url, restTemplate);
    }

    @Bean
    @Conditional(WebClientCondition.class)
    BandDtoService bandDtoServiceWebClient() {
        return new BandDtoServiceWebClient("/bands_dto", webClient);
    }

    @Bean
    @Conditional(RestTemplateCondition.class)
    BandService bandService() {
        String url = String.format("%s://%s/bands", protocol, host);
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
        String url = String.format("%s://%s/repertoire", protocol, host);
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
        String url = String.format("%s://%s/repertoire/filter", protocol, host);
        return new TrackDtoServiceRest(url, restTemplate);
    }

    @Bean
    @Conditional(WebClientCondition.class)
    TrackDtoService trackDtoServiceWebClient() {
        return new TrackDtoServiceWebClient("/repertoire/filter", webClient);
    }
}
