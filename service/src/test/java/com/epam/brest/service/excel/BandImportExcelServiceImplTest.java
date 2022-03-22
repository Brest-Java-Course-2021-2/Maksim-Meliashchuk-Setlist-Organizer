package com.epam.brest.service.excel;

import com.epam.brest.service.config.BandServiceTestConfig;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@Import({BandServiceTestConfig.class})
class BandImportExcelServiceImplTest {

    @Autowired
    private BandImportExcelService bandImportExcelService;

    @Test
    void importBandsExcelTest() throws IOException {
        File files = new File("src/test/resources/Band.xlsx");
        FileInputStream input = new FileInputStream(files);
        MultipartFile multipartFile = new MockMultipartFile("file",
                files.getName(), "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
                IOUtils.toByteArray(input));
        assertNotNull(bandImportExcelService.importBandsExcel(multipartFile));
    }
}