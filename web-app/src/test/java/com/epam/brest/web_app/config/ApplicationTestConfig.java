package com.epam.brest.web_app.config;

import com.epam.brest.ApiClient;
import com.epam.brest.service.*;
import com.epam.brest.service.faker.BandDtoFakerService;
import com.epam.brest.service.faker.TrackDtoFakerService;
import io.swagger.client.api.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.client.RestTemplate;

@TestConfiguration
@Profile("dev")
@PropertySource("classpath:application-dev.yaml")
public class ApplicationTestConfig {

    @Value("${rest.server.protocol}")
    private String protocol;
    @Value("${rest.server.host}")
    private String host;
    @Value("${rest.server.port}")
    private Integer port;

    private final RestTemplate restTemplate;

    private final ApiClient apiClient;

    public ApplicationTestConfig(RestTemplate restTemplate, ApiClient apiClient) {
        this.restTemplate = restTemplate;
        this.apiClient = apiClient;
    }

    @Bean
    BandDtoService bandDtoService() {
        String url = String.format("%s://%s:%d/bands_dto", protocol, host, port);
        return new BandDtoServiceRest(url, restTemplate);
    }

    @Bean
    BandDtoFakerService bandDtoFakerService() {
        String url = String.format("%s://%s:%d/bands_dto/fill", protocol, host, port);
        return new BandDtoFakerServiceRest(url, restTemplate);
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

    @Bean
    TrackDtoFakerService trackDtoFakerService() {
        String url = String.format("%s://%s:%d/repertoire/fill", protocol, host, port);
        return new TrackDtoFakerServiceRest(url, restTemplate);
    }

    @Bean
    public BandsApi bandsApi() {
        BandsApi bandsApi = new BandsApi();
        bandsApi.setApiClient(apiClient);
        return bandsApi;
    }

    @Bean
    public BandApi bandApi() {
        BandApi bandApi = new BandApi();
        bandApi.setApiClient(apiClient);
        return bandApi;
    }

    @Bean
    public TracksApi tracksApi() {
        TracksApi tracksApi = new TracksApi();
        tracksApi.setApiClient(apiClient);
        return tracksApi;
    }

    @Bean
    public TrackApi trackApi() {
        TrackApi trackApi = new TrackApi();
        trackApi.setApiClient(apiClient);
        return trackApi;
    }

    @Bean
    public ImportExportDatabaseApi importExportDatabaseApi() {
        ImportExportDatabaseApi importExportDatabaseApi = new ImportExportDatabaseApi();
        importExportDatabaseApi.setApiClient(apiClient);
        return importExportDatabaseApi;
    }

}
