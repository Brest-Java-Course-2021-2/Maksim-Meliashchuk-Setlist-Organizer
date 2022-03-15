package com.epam.brest.service.excel;

import com.epam.brest.service.config.BandServiceTestConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@Import({BandServiceTestConfig.class})
class BandExportExcelServiceImplTest {

    @Autowired
    private BandExportExcelService bandExportExcelService;

    @Test
    void exportBandsExcel() {
        //bandExportExcelService.exportBandsExcel();
    }
}