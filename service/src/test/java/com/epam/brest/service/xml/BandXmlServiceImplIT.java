package com.epam.brest.service.xml;

import com.epam.brest.service.config.BandServiceTestConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@Import({BandServiceTestConfig.class})
@Slf4j
@Sql({"/create-db.sql", "/init-db.sql"})
class BandXmlServiceImplIT {
    @Autowired
    private BandXmlService bandXmlService;

    @Test
    void exportBandsXmlTest() throws IOException {
        log.debug("exportBandsXmlTest()");
        int bandCount = 3;
        HttpServletResponse httpServletResponse = new MockHttpServletResponse();
        assertNotNull(bandXmlService.exportBandsXml(httpServletResponse));
        assertEquals(bandXmlService.exportBandsXml(httpServletResponse).size(), bandCount);
    }

    @Test
    void exportBandsXmlAsStringTest() throws IOException {
        log.debug("exportBandsXmlAsStringTest()");
        assertTrue(bandXmlService.exportBandsXmlAsString().length() > 0);
    }
}