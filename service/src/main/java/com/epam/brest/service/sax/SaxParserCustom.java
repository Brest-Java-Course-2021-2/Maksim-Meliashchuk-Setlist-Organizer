package com.epam.brest.service.sax;

import com.epam.brest.model.Band;
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
    private final SAXParser bandSaxParser;
    private SAXParser trackSaxParser;

    public SaxParserCustom() throws ParserConfigurationException, SAXException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        bandSaxParser = factory.newSAXParser();
        bandXmlHandler = new BandXmlHandler();
    }

    List<Band> parseBands(String content) throws IOException, SAXException {
        InputSource inputSource = new InputSource(new StringReader(content));
        bandSaxParser.parse(inputSource, bandXmlHandler);
        return bandXmlHandler.getBandList();
    }
}
