package com.epam.brest.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.mockwebserver.MockWebServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

class TrackServiceWebClientTest {

    private final Logger logger = LogManager.getLogger(TrackServiceWebClientTest.class);

    private BandService serviceImpl;

    public static MockWebServer mockWebServer;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void getTrackById() {
    }

    @Test
    void create() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    @Test
    void count() {
    }

    @Test
    void findAllTracks() {
    }
}