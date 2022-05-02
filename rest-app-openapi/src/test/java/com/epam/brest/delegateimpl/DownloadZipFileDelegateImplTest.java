package com.epam.brest.delegateimpl;

import com.epam.brest.api.DownloadZipFileApiController;
import com.epam.brest.service.export_import_db.DataBaseZipRestoreService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.servlet.http.HttpServletResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@ExtendWith(MockitoExtension.class)
class DownloadZipFileDelegateImplTest {

    @InjectMocks
    private DownloadZipFileDelegateImpl downloadZipFileDelegate;
    @Mock
    private DataBaseZipRestoreService dataBaseZipRestoreService;
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(new DownloadZipFileApiController(downloadZipFileDelegate))
                .build();
    }

    @SneakyThrows
    @Test
    void downloadZipFileTest() {
        log.debug("downloadZipFileTest()");

        MockHttpServletResponse response =
                mockMvc.perform(MockMvcRequestBuilders.get("/downloadZipFile"))
                        .andExpect(status().isOk())
                        .andReturn().getResponse();

        assertNotNull(response);
        assertEquals(response.getContentType(), "application/zip");
        assertEquals(response.getHeader("Content-disposition"), "attachment; filename=download.zip");

        verify(dataBaseZipRestoreService).exportData(any(HttpServletResponse.class));
    }
}