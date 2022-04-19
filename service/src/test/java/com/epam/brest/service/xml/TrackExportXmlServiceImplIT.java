package com.epam.brest.service.xml;

import com.epam.brest.service.config.TrackServiceTestConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@Import({TrackServiceTestConfig.class})
@Slf4j
class TrackExportXmlServiceImplIT {

    @Autowired
    private TrackExportXmlService trackExportXmlService;

    @Test
    void exportBandsXmlTest() throws IOException {
        log.debug("exportBandsXmlTest()");
        int trackCount = 4;
        HttpServletResponse httpServletResponse = new MockHttpServletResponse();
        assertNotNull(trackExportXmlService.exportTracksXml(httpServletResponse));
        assertEquals(trackExportXmlService.exportTracksXml(httpServletResponse).size(), trackCount);
    }

}