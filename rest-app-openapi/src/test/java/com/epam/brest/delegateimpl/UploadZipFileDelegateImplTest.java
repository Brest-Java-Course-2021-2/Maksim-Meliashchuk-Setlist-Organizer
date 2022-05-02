package com.epam.brest.delegateimpl;

import com.epam.brest.api.UploadZipFileApiController;
import com.epam.brest.service.export_import_db.DataBaseZipRestoreService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.File;
import java.io.FileInputStream;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@ExtendWith(MockitoExtension.class)
class UploadZipFileDelegateImplTest {

    @InjectMocks
    private UploadZipFileDelegateImpl uploadZipFileDelegate;
    @Mock
    private DataBaseZipRestoreService dataBaseZipRestoreService;
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(new UploadZipFileApiController(uploadZipFileDelegate))
                .build();
    }

    @SneakyThrows
    @Test
    void uploadingZipFileTest() {
        log.debug("uploadingZipFileTest");

        File files = new File("src/test/resources/database.zip");
        FileInputStream input = new FileInputStream(files);
        MockMultipartFile multipartFile = new MockMultipartFile("file",
                files.getName(), "application/zip",
                IOUtils.toByteArray(input));

        MockHttpServletResponse response = mockMvc.perform(multipart("/uploadZipFile").file(multipartFile))
                .andExpect(status().isOk()).andReturn().getResponse();

        assertNotNull(response);

        Mockito.verify(dataBaseZipRestoreService).importData(any(MockMultipartFile.class));

    }
}