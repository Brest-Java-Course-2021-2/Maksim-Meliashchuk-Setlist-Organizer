package com.epam.brest.service.xml;

import com.epam.brest.model.Band;
import org.springframework.security.access.prepost.PreAuthorize;
import org.xml.sax.SAXException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public interface BandXmlService {

    @PreAuthorize("hasAnyRole('user', 'admin')")
    List<Band> exportBandsXml(HttpServletResponse response) throws IOException;

    @PreAuthorize("hasAnyRole('user', 'admin')")
    String exportBandsXmlAsString() throws IOException;

    @PreAuthorize("hasAnyRole('admin')")
    void importBandsAsXml(String content) throws IOException, SAXException;
}
