package com.epam.brest.service.xml;

import com.epam.brest.model.Track;
import org.springframework.security.access.prepost.PreAuthorize;
import org.xml.sax.SAXException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public interface TrackXmlService {
    @PreAuthorize("hasAnyRole('user', 'admin')")
    List<Track> exportTracksXml(HttpServletResponse response) throws IOException;

    @PreAuthorize("hasAnyRole('user', 'admin')")
    String exportTracksXmlAsString() throws IOException;
    @PreAuthorize("hasAnyRole('admin')")
    void importTracksAsXml(String content) throws IOException, SAXException;

}
