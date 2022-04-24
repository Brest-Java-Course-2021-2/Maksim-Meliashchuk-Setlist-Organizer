package com.epam.brest.service.sax;

import com.epam.brest.model.Band;
import com.epam.brest.model.Track;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
class SaxParserCustomTest {

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

    @Test
    void parseTracksXmlTest() throws ParserConfigurationException, SAXException, IOException{
        log.debug("parseTracksXmlTest()");
        FileInputStream fis = new FileInputStream("src/test/resources/Track.xml");
        String content = IOUtils.toString(fis, StandardCharsets.UTF_8);
        assertNotNull(content);
        SaxParserCustom trackSaxParser = new SaxParserCustom();
        trackSaxParser.parseTracks(content);
        List<Track> resultList = trackSaxParser.getTrackXmlHandler().getTrackList();
        assertNotNull(resultList);
        assertEquals(4, resultList.size());
        assertEquals(3, resultList.get(0).getTrackBandId());
        assertEquals(104, resultList.get(0).getTrackTempo());
        assertEquals(135000, resultList.get(0).getTrackDuration());
        assertEquals("Tuning:EADGBe", resultList.get(0).getTrackDetails());
        assertEquals("https://www.youtube.com/watch?v=rvX7lgrx47M&ab_channel=Muse-Topic",
                resultList.get(0).getTrackLink());
        assertEquals(LocalDate.parse("2000-01-12"), resultList.get(0).getTrackReleaseDate());
    }
}