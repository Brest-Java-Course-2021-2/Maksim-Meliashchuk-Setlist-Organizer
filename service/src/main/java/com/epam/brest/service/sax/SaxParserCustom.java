package com.epam.brest.service.sax;

import com.epam.brest.model.Band;
import com.epam.brest.model.Track;
import lombok.Getter;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;

public class SaxParserCustom {

    @Getter
    private final BandXmlHandler bandXmlHandler;
    @Getter
    private final TrackXmlHandler trackXmlHandler;
    private final SAXParser bandSaxParser;
    private final SAXParser trackSaxParser;

    public SaxParserCustom() throws ParserConfigurationException, SAXException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        bandSaxParser = factory.newSAXParser();
        bandXmlHandler = new BandXmlHandler();
        trackSaxParser = factory.newSAXParser();
        trackXmlHandler = new TrackXmlHandler();
    }

    List<Band> parseBands(String content) throws IOException, SAXException {
        InputSource inputSource = new InputSource(new StringReader(content));
        bandSaxParser.parse(inputSource, bandXmlHandler);
        return bandXmlHandler.getBandList();
    }

    List<Track> parseTracks(String content) throws IOException, SAXException {
        InputSource inputSource = new InputSource(new StringReader(content));
        trackSaxParser.parse(inputSource, trackXmlHandler);
        return trackXmlHandler.getTrackList();
    }
}
