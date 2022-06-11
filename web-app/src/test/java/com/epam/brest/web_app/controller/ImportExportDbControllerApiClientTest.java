package com.epam.brest.web_app.controller;

import com.epam.brest.ApiClient;
import com.epam.brest.web_app.security.AccessTokenValueExtractor;
import io.swagger.client.api.ImportExportDatabaseApi;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.File;
import java.io.FileInputStream;

import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Slf4j
@AutoConfigureMockMvc
@SpringBootTest(properties = { "app.httpClient = ApiClient" })
@WithMockUser(username = "admin", roles = { "admin" })
@TestPropertySource(properties = {"spring.security.oauth2.client.provider.keycloak.pre-connection-check: false"})
@TestPropertySource(properties = {"spring.cloud.config.enabled:false"})
@TestPropertySource(properties = {"eureka.client.enabled=false"})
class ImportExportDbControllerApiClientTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ImportExportDatabaseApi importExportDatabaseApi;

    @MockBean
    private OAuth2AuthorizedClientService auth2AuthorizedClientService;

    @Mock
    private AccessTokenValueExtractor accessTokenValueExtractor;

    @MockBean
    private ApiClient apiClient;

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
        try (MockedStatic<AccessTokenValueExtractor> mocked = mockStatic(AccessTokenValueExtractor.class)) {
            mocked.when(() -> AccessTokenValueExtractor.getAccessTokenValue(auth2AuthorizedClientService))
                    .thenReturn("token");

            when(importExportDatabaseApi.getApiClient()).thenReturn(apiClient);
            mockMvc.perform(MockMvcRequestBuilders.multipart("/uploadZipFile")
                            .file(mockMultipartFile).with(csrf()))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(view().name("redirect:/bands"))
                    .andExpect(redirectedUrl("/bands"));
        }
    }
}