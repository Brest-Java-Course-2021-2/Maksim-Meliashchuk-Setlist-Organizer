package com.epam.brest.service.xml;

import com.epam.brest.model.Band;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public interface BandExportXmlService {

    List<Band> exportBandsXml(HttpServletResponse response) throws IOException;
    String exportBandsXmlAsString() throws IOException;
}
