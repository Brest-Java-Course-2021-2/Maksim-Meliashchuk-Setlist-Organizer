package com.epam.brest.web_app.config;

import com.epam.brest.ApiClient;
import com.epam.brest.service.*;
import com.epam.brest.service.faker.BandDtoFakerService;
import com.epam.brest.service.faker.TrackDtoFakerService;
import com.epam.brest.web_app.condition.ApiClientCondition;
import com.epam.brest.web_app.condition.RestTemplateCondition;
import com.epam.brest.web_app.condition.WebClientCondition;
import io.swagger.client.api.*;
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

    private final ApiClient apiClient;

    public ApplicationConfig(RestTemplate restTemplate, WebClient webClient, ApiClient apiClient) {
        this.webClient = webClient;
        this.restTemplate = restTemplate;
        this.apiClient = apiClient;
    }

    @Bean
    @Conditional(RestTemplateCondition.class)
    BandDtoService bandDtoServiceRestTemplate() {
        String url = String.format("%s://%s:%d/bands_dto", protocol, host, port);
        return new BandDtoServiceRest(url, restTemplate);
    }

    @Bean
    @Conditional(RestTemplateCondition.class)
    BandDtoFakerService bandDtoFakerServiceRestTemplate() {
        String url = String.format("%s://%s:%d/bands_dto/fill", protocol, host, port);
        return new BandDtoFakerServiceRest(url, restTemplate);
    }

    @Bean
    @Conditional(WebClientCondition.class)
    BandDtoService bandDtoServiceWebClient() {
        return new BandDtoServiceWebClient("/bands_dto", webClient);
    }

    @Bean
    @Conditional(WebClientCondition.class)
    BandDtoFakerService bandDtoFakerServiceWebClient() {
        return new BandDtoFakerServiceWebClient("/bands_dto/fill", webClient);
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
    @Conditional(RestTemplateCondition.class)
    TrackDtoFakerService trackDtoFakerService() {
        String url = String.format("%s://%s:%d/tracks_dto/fill", protocol, host, port);
        return new TrackDtoFakerServiceRest(url, restTemplate);
    }

    @Bean
    @Conditional(WebClientCondition.class)
    TrackDtoService trackDtoServiceWebClient() {
        return new TrackDtoServiceWebClient("/repertoire/filter", webClient);
    }

    @Bean
    @Conditional(WebClientCondition.class)
    TrackDtoFakerService trackDtoFakerServiceWebClient() {
        return new TrackDtoFakerServiceWebClient("/tracks_dto/fill", webClient);
    }

    @Bean
    @Conditional(ApiClientCondition.class)
    public BandsApi bandsApi() {
        BandsApi bandsApi = new BandsApi();
        bandsApi.setApiClient(apiClient);
        return bandsApi;
    }

    @Bean
    @Conditional(ApiClientCondition.class)
    public BandApi bandApi() {
        BandApi bandApi = new BandApi();
        bandApi.setApiClient(apiClient);
        return bandApi;
    }

    @Bean
    @Conditional(ApiClientCondition.class)
    public TracksApi tracksApi() {
        TracksApi tracksApi = new TracksApi();
        tracksApi.setApiClient(apiClient);
        return tracksApi;
    }

    @Bean
    @Conditional(ApiClientCondition.class)
    public TrackApi trackApi() {
        TrackApi trackApi = new TrackApi();
        trackApi.setApiClient(apiClient);
        return trackApi;
    }

    @Bean
    @Conditional(ApiClientCondition.class)
    public ImportExportDatabaseApi importExportDatabaseApi() {
        ImportExportDatabaseApi importExportDatabaseApi = new ImportExportDatabaseApi();
        importExportDatabaseApi.setApiClient(apiClient);
        return importExportDatabaseApi;
    }

}
