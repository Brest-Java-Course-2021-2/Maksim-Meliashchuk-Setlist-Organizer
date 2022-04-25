package com.epam.brest.service.sax;

import com.epam.brest.model.Band;
import com.epam.brest.model.Track;
import lombok.Getter;
import org.springframework.stereotype.Service;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;

@Service
public class SaxParserCustom {

    @Getter
    private final BandXmlHandler bandXmlHandler;
    @Getter
    private final TrackXmlHandler trackXmlHandler;
    private final SAXParser saxParser;

    public SaxParserCustom() throws ParserConfigurationException, SAXException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        saxParser = factory.newSAXParser();
        bandXmlHandler = new BandXmlHandler();
        trackXmlHandler = new TrackXmlHandler();
    }

    public List<Band> parseBands(String content) throws IOException, SAXException {
        InputSource inputSource = new InputSource(new StringReader(content));
        saxParser.parse(inputSource, bandXmlHandler);
        return bandXmlHandler.getBandList();
    }

    public List<Track> parseTracks(String content) throws IOException, SAXException {
        InputSource inputSource = new InputSource(new StringReader(content));
        saxParser.parse(inputSource, trackXmlHandler);
        return trackXmlHandler.getTrackList();
    }
}
