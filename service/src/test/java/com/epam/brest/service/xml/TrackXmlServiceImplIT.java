package com.epam.brest.service.xml;

import com.epam.brest.service.config.TrackServiceTestConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@Import({TrackServiceTestConfig.class})
@Slf4j
@Sql({"/create-db.sql", "/init-db.sql"})
class TrackXmlServiceImplIT {

    @Autowired
    private TrackXmlService trackXmlService;

    @BeforeEach
    void setup() throws NoSuchFieldException, IllegalAccessException {
        Field dateFormatField = trackXmlService.getClass()
                .getDeclaredField("dateFormat");
        dateFormatField.setAccessible(true);
        String dateFormat= "yyyy/MM/dd";
        dateFormatField.set(trackXmlService, dateFormat);
    }

    @Test
    void exportBandsXmlTest() throws IOException{
        log.debug("exportBandsXmlTest()");
        int trackCount = 4;
        HttpServletResponse httpServletResponse = new MockHttpServletResponse();
        assertNotNull(trackXmlService.exportTracksXml(httpServletResponse));
        assertEquals(trackXmlService.exportTracksXml(httpServletResponse).size(), trackCount);
    }

    @Test
    void exportTracksXmlAsStringTest() throws IOException {
        log.debug("exportTracksXmlAsStringTest()");
        assertTrue(trackXmlService.exportTracksXmlAsString().length() > 0);
    }

}