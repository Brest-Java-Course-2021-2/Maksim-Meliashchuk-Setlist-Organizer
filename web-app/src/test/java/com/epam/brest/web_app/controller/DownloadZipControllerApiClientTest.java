package com.epam.brest.web_app.controller;

import com.epam.brest.ApiException;
import com.epam.brest.service.BandDtoService;
import io.swagger.client.api.DownloadZipControllerApi;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@AutoConfigureMockMvc
@SpringBootTest(properties = { "app.httpClient = ApiClient" })
class DownloadZipControllerApiClientTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DownloadZipControllerApi downloadZipControllerApi;

    @Test
    void downloadZipFile() throws Exception {
        File file = new File("src/test/resources/database.zip");
        when(downloadZipControllerApi.downloadZipFile()).thenReturn(file);
        mockMvc.perform(get("/downloadZipFile"))
                .andExpect(status().isOk()).andExpect(content()
                        .contentType("application/zip"));
    }
}