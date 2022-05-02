package com.epam.brest.web_app.controller;

import io.swagger.client.api.ImportExportDatabaseApi;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.File;
import java.io.FileInputStream;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Slf4j
@AutoConfigureMockMvc
@SpringBootTest(properties = { "app.httpClient = ApiClient" })
class ImportExportDbControllerApiClientTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ImportExportDatabaseApi importExportDatabaseApi;

    @Test
    void downloadZipFileTest() throws Exception {
        log.debug("downloadZipFileTest()");
        File file = new File("src/test/resources/database.zip");
        when(importExportDatabaseApi.downloadZipFile()).thenReturn(file);
        mockMvc.perform(get("/downloadZipFile"))
                .andExpect(status().isOk()).andExpect(content()
                        .contentType("application/zip"));
    }

    @Test
    void uploadZipFileTest() throws Exception {
        log.debug("uploadZipFileTest()");
        File file = new File("src/test/resources/database.zip");
        FileInputStream input = new FileInputStream(file);
        MockMultipartFile mockMultipartFile = new MockMultipartFile("uploadfile",
                file.getName(), "application/zip",
                IOUtils.toByteArray(input));
        mockMvc.perform(MockMvcRequestBuilders.multipart("/uploadZipFile")
                        .file(mockMultipartFile))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/bands"))
                .andExpect(redirectedUrl("/bands"));
    }
}