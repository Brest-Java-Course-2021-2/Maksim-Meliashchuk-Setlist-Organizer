package com.epam.brest.service.xml;

import com.epam.brest.model.Track;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public interface TrackExportXmlService {

    List<Track> exportTracksXml(HttpServletResponse response) throws IOException;
    String exportTracksXmlAsString() throws IOException;

}
