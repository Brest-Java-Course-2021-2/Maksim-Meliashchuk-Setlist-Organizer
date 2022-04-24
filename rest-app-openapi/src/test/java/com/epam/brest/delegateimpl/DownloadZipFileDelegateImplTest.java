package com.epam.brest.delegateimpl;

import com.epam.brest.api.DownloadZipFileApiController;
import com.epam.brest.service.xml.BandExportXmlService;
import com.epam.brest.service.xml.TrackExportXmlService;
import com.epam.brest.service.zip.DownloadZipService;
import lombok.SneakyThrows;
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
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@ExtendWith(MockitoExtension.class)
class DownloadZipFileDelegateImplTest {

    @InjectMocks
    private DownloadZipFileDelegateImpl downloadZipFileDelegate;

    @Mock
    private BandExportXmlService bandExportXmlService;

    @Mock
    private TrackExportXmlService trackExportXmlService;

    @Mock
    private DownloadZipService downloadZipService;

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

        Mockito.when(bandExportXmlService.exportBandsXmlAsString())
                .thenReturn("Bands.xml");
        Mockito.when(trackExportXmlService.exportTracksXmlAsString())
                .thenReturn("Tracks.xml");
        Mockito.when(downloadZipService.downloadZipFile(any(HttpServletResponse.class), any(HashMap.class)))
                .thenReturn(2);

        MockHttpServletResponse response =
                mockMvc.perform(MockMvcRequestBuilders.get("/downloadZipFile"))
                        .andExpect(status().isOk())
                        .andReturn().getResponse();
        assertNotNull(response);
        assertEquals(response.getContentType(), "application/zip");
        assertEquals(response.getHeader("Content-disposition"), "attachment; filename=download.zip");

        Mockito.verify(bandExportXmlService).exportBandsXmlAsString();
        Mockito.verify(trackExportXmlService).exportTracksXmlAsString();
        Mockito.verify(downloadZipService).downloadZipFile(any(HttpServletResponse.class), any(HashMap.class));
    }
}