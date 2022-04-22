package com.epam.brest.service.xml;

import com.epam.brest.service.config.BandServiceTestConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@Import({BandServiceTestConfig.class})
@Slf4j
class BandExportXmlServiceImplIT {
    @Autowired
    private BandExportXmlService bandExportXmlService;

    @Test
    void exportBandsXmlTest() throws IOException {
        log.debug("exportBandsXmlTest()");
        int bandCount = 3;
        HttpServletResponse httpServletResponse = new MockHttpServletResponse();
        assertNotNull(bandExportXmlService.exportBandsXml(httpServletResponse));
        assertEquals(bandExportXmlService.exportBandsXml(httpServletResponse).size(), bandCount);
    }

    @Test
    void exportBandsXmlAsStringTest() throws IOException {
        log.debug("exportBandsXmlAsStringTest()");
        assertTrue(bandExportXmlService.exportBandsXmlAsString().length() > 0);
    }
}