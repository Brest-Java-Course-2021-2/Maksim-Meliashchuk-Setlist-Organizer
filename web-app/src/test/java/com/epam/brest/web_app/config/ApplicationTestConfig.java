package com.epam.brest.web_app.config;

import com.epam.brest.ApiClient;
import com.epam.brest.service.*;
import com.epam.brest.web_app.condition.ApiClientCondition;
import io.swagger.client.api.BandApi;
import io.swagger.client.api.BandsApi;
import io.swagger.client.api.TrackApi;
import io.swagger.client.api.TracksApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.client.RestTemplate;

@TestConfiguration
@Profile("dev")
@PropertySource("classpath:application-dev.properties")
public class ApplicationTestConfig {

    @Value("${rest.server.protocol}")
    private String protocol;
    @Value("${rest.server.host}")
    private String host;
    @Value("${rest.server.port}")
    private Integer port;

    private RestTemplate restTemplate;

    private ApiClient apiClient;

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

}
