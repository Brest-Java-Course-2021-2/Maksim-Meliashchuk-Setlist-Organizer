package com.epam.brest.service.zip;

import com.epam.brest.service.config.BandServiceTestConfig;
import com.epam.brest.service.config.TrackServiceTestConfig;
import com.epam.brest.service.xml.BandExportXmlService;
import com.epam.brest.service.xml.TrackExportXmlService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@ExtendWith(SpringExtension.class)
@Import({TrackServiceTestConfig.class, BandServiceTestConfig.class})
class DownloadZipServiceImplIT {

    private final DownloadZipService downloadZipService = new DownloadZipServiceImpl();
    @Autowired
    private BandExportXmlService bandExportXmlService;
    @Autowired
    private TrackExportXmlService trackExportXmlService;

    @BeforeEach
    void setup() throws NoSuchFieldException, IllegalAccessException {
        Field dateFormatField = trackExportXmlService.getClass()
                .getDeclaredField("dateFormat");
        dateFormatField.setAccessible(true);
        String dateFormat = "yyyy/MM/dd";
        dateFormatField.set(trackExportXmlService, dateFormat);
    }

    @SneakyThrows
    @Test
    void downloadZipFileTest() {
        log.debug("downloadZipFileTest()");
        HttpServletResponse httpServletResponse = new MockHttpServletResponse();
        Map<String, String> listOfFiles = new HashMap<>();
        listOfFiles.put("Bands.xml", bandExportXmlService.exportBandsXmlAsString());
        listOfFiles.put("Tracks.xml", trackExportXmlService.exportTracksXmlAsString());
        assertEquals(downloadZipService.downloadZipFile(httpServletResponse, listOfFiles), listOfFiles.keySet().size());
    }
}