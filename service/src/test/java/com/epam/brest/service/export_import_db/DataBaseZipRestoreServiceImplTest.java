package com.epam.brest.service.export_import_db;

import com.epam.brest.service.BandService;
import com.epam.brest.service.TrackService;
import com.epam.brest.service.xml.BandXmlService;
import com.epam.brest.service.xml.TrackXmlService;
import com.epam.brest.service.zip.DownloadZipService;
import com.epam.brest.service.zip.UploadZipService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
class DataBaseZipRestoreServiceImplTest {

    @Mock
    private BandService bandService;
    @Mock
    private TrackService trackService;
    @Mock
    private BandXmlService bandXmlService;
    @Mock
    private TrackXmlService trackXmlService;
    @Mock
    private DownloadZipService downloadZipService;
    @Mock
    private HttpServletResponse response;
    @Mock
    private UploadZipService uploadZipService;
    private DataBaseZipRestoreService dataBaseZipRestoreService;

    @BeforeEach
    void initUseCase() {
        dataBaseZipRestoreService = new DataBaseZipRestoreServiceImpl(bandXmlService, trackXmlService,
                downloadZipService, uploadZipService, bandService, trackService);
    }


    @Test
    void exportDataTest() throws IOException {
        log.debug("exportDataTest()");
        String bands = "bands";
        String tracks = "tracks";
        Integer size = 1;
        Map<String, String> listOfFiles = new HashMap<>();
        listOfFiles.put("Bands.xml", bands);
        listOfFiles.put("Tracks.xml", tracks);

        when(bandXmlService.exportBandsXmlAsString()).thenReturn(bands);
        when(trackXmlService.exportTracksXmlAsString()).thenReturn(tracks);
        when(downloadZipService.downloadZipFile(any(HttpServletResponse.class), anyMap())).thenReturn(size);

        dataBaseZipRestoreService.exportData(response);

        verify(bandXmlService).exportBandsXmlAsString();
        verify(trackXmlService).exportTracksXmlAsString();
        verify(downloadZipService).downloadZipFile(response, listOfFiles);

    }

    @Test
    void deleteAllDataTest() {
        log.debug("deleteAllDataTest()");

        dataBaseZipRestoreService.deleteAllData();

        verify(trackService).deleteAllTracks();
        verify(bandService).deleteAllBands();

    }

    @Test
    void importDataTest() throws IOException, SAXException {
        log.debug("importDataTest()");
        String bands = "bands";
        String tracks = "tracks";
        Map<String, String> listOfFiles = new HashMap<>();
        listOfFiles.put("Bands.xml", bands);
        listOfFiles.put("Tracks.xml", tracks);

        File files = new File("src/test/resources/database.zip");
        FileInputStream input = new FileInputStream(files);
        MultipartFile multipartFile = new MockMultipartFile("file",
                files.getName(), "application/zip",
                IOUtils.toByteArray(input));

        when(uploadZipService.uploadZipFile(multipartFile)).thenReturn(listOfFiles);

        dataBaseZipRestoreService.importData(multipartFile);

        verify(uploadZipService).uploadZipFile(multipartFile);
        verify(bandXmlService).importBandsAsXml(listOfFiles.get("Bands.xml"));
        verify(trackXmlService).importTracksAsXml(listOfFiles.get("Tracks.xml"));
    }
}