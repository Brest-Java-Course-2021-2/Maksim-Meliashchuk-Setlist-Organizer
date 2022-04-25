package com.epam.brest.rest;

import com.epam.brest.service.export_import_db.DataBaseZipRestoreService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.servlet.http.HttpServletResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@ExtendWith(MockitoExtension.class)
class ImportExportDbControllerTest {

    @InjectMocks
    private ImportExportDbController downloadZipController;

    @Mock
    DataBaseZipRestoreService dataBaseZipRestoreService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(downloadZipController)
                .build();
    }

    @Test
    void downloadZipFileTest() throws Exception {
        log.debug("downloadZipFileTest()");

        MockHttpServletResponse response =
                mockMvc.perform(MockMvcRequestBuilders.get("/downloadZipFile"))
                        .andExpect(status().isOk())
                        .andReturn().getResponse();
        assertNotNull(response);
        assertEquals(response.getContentType(), "application/zip");
        assertEquals(response.getHeader("Content-disposition"), "attachment; filename=download.zip");

        Mockito.verify(dataBaseZipRestoreService).exportData(any(HttpServletResponse.class));
    }
}