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
class BandDtoExportExcelServiceImplIT {

    private final Logger logger = LogManager.getLogger(BandDtoExportExcelServiceImplIT.class);

    @Autowired
    private BandDtoExportExcelService bandDtoExportExcelService;

    @Test
    void exportBandsDtoExcel() {
        logger.debug("exportBandsDtoExcel()");
        HttpServletResponse httpServletResponse = new MockHttpServletResponse();
        assertNotNull(bandDtoExportExcelService.exportBandsDtoExcel(httpServletResponse));
    }

}