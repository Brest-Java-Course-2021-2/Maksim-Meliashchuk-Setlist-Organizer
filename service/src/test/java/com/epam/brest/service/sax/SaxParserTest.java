package com.epam.brest.service.sax;

import com.epam.brest.model.Band;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
class SaxParserTest {

    @Test
    void parseBandXmlTest() throws ParserConfigurationException, SAXException, IOException {
        log.debug("parseBAndXmlTest()");
        FileInputStream fis = new FileInputStream("src/test/resources/Band.xml");
        String content = IOUtils.toString(fis, StandardCharsets.UTF_8);
        assertNotNull(content);
        SaxParserCustom bandSaxParser = new SaxParserCustom();
        bandSaxParser.parseBands(content);
        List<Band> resultList = bandSaxParser.getBandXmlHandler().getBandList();
        assertNotNull(resultList);
        assertEquals(3, resultList.size());
        assertEquals("MY COVER BAND", resultList.get(0).getBandName());
        assertEquals("Alternative&Metal", resultList.get(0).getBandDetails());
        assertEquals("MY BAND", resultList.get(1).getBandName());
        assertEquals("Metal", resultList.get(1).getBandDetails());
        assertEquals("MUSE", resultList.get(2).getBandName());
        assertEquals("Rock", resultList.get(2).getBandDetails());
    }
}