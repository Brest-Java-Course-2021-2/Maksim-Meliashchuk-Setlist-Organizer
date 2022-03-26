package com.epam.brest.service.excel;

import com.epam.brest.service.config.BandServiceTestConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.servlet.http.HttpServletResponse;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@Import({BandServiceTestConfig.class})
class BandExportExcelServiceImplIT {

    private final Logger logger = LogManager.getLogger(BandExportExcelServiceImplIT.class);

    @Autowired
    private BandExportExcelService bandExportExcelService;

    @Test
    void exportBandsExcel() {
        logger.debug("exportBandsExcel()");
        HttpServletResponse httpServletResponse = new MockHttpServletResponse();
        assertNotNull(bandExportExcelService.exportBandsExcel(httpServletResponse));
    }
}