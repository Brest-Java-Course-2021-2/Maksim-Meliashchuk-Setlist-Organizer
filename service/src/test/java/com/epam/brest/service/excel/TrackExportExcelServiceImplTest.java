package com.epam.brest.service.excel;

import com.epam.brest.service.config.TrackServiceTestConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@Import({TrackServiceTestConfig.class})
class TrackExportExcelServiceImplTest {

    @Autowired
    private TrackExportExcelService trackExportExcelService;

    @Test
    void exportTracksExcel() {
        trackExportExcelService.exportTracksExcel();
    }
}