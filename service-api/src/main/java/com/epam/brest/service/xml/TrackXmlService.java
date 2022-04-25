package com.epam.brest.service.xml;

import com.epam.brest.model.Track;
import org.xml.sax.SAXException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public interface TrackXmlService {

    List<Track> exportTracksXml(HttpServletResponse response) throws IOException;
    String exportTracksXmlAsString() throws IOException;
    void importTracksAsXml(String content) throws IOException, SAXException;

}
