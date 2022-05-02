package com.epam.brest.service.xml;

import com.epam.brest.model.Band;
import org.xml.sax.SAXException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public interface BandXmlService {

    List<Band> exportBandsXml(HttpServletResponse response) throws IOException;
    String exportBandsXmlAsString() throws IOException;

    void importBandsAsXml(String content) throws IOException, SAXException;
}
