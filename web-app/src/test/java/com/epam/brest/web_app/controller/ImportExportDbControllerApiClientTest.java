package com.epam.brest.web_app.controller;

import io.swagger.client.api.ImportExportDatabaseApi;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.io.File;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@AutoConfigureMockMvc
@SpringBootTest(properties = { "app.httpClient = ApiClient" })
class ImportExportDbControllerApiClientTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ImportExportDatabaseApi importExportDatabaseApi;

    @Test
    void downloadZipFile() throws Exception {
        File file = new File("src/test/resources/database.zip");
        when(importExportDatabaseApi.downloadZipFile()).thenReturn(file);
        mockMvc.perform(get("/downloadZipFile"))
                .andExpect(status().isOk()).andExpect(content()
                        .contentType("application/zip"));
    }
}