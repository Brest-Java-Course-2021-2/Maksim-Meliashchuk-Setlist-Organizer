package com.epam.brest.service.zip;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
class UploadZipServiceImplIT {

    private final UploadZipService uploadZipService = new UploadZipServiceImpl();

    @Test
    void uploadZipFileTest() throws IOException {
        log.debug("uploadZipFileTest()");
        File files = new File("src/test/resources/database.zip");
        FileInputStream input = new FileInputStream(files);
        MultipartFile multipartFile = new MockMultipartFile("file",
                files.getName(), "application/zip",
                IOUtils.toByteArray(input));
        assertEquals(2, uploadZipService.uploadZipFile(multipartFile).keySet().size());
        assertTrue(uploadZipService.uploadZipFile(multipartFile).containsKey("Bands.xml"));
        assertTrue(uploadZipService.uploadZipFile(multipartFile).containsKey("Tracks.xml"));
    }
}