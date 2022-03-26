package com.epam.brest.service.excel;

import com.epam.brest.service.config.TrackServiceTestConfig;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@Import({TrackServiceTestConfig.class})
@Transactional
@Rollback
@ActiveProfiles("dev")
class TrackImportExcelServiceImplIT {

    private final Logger logger = LogManager.getLogger(TrackExportExcelServiceImplIT.class);

    @Autowired
    private TrackImportExcelService trackImportExcelService;

    @Test
    void importTrackExcel() throws IOException {
        logger.debug("importTrackExcel()");
        File files = new File("src/test/resources/Track.xlsx");
        FileInputStream input = new FileInputStream(files);
        MultipartFile multipartFile = new MockMultipartFile("file",
                files.getName(), "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
                IOUtils.toByteArray(input));
        assertTrue(trackImportExcelService.importTrackExcel(multipartFile).size() > 0 );
    }
}