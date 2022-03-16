package com.epam.brest.service.excel;

import com.epam.brest.service.config.TrackServiceTestConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.servlet.http.HttpServletResponse;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@Import({TrackServiceTestConfig.class})
class TrackDtoExportExcelServiceImplTest {

    @Autowired
    private TrackDtoExportExcelService trackDtoExportExcelService;

    @Test
    void exportTracksDtoExcel() {
        HttpServletResponse httpServletResponse = new MockHttpServletResponse();
        assertNotNull(trackDtoExportExcelService.exportTracksDtoExcel(httpServletResponse));
    }
}