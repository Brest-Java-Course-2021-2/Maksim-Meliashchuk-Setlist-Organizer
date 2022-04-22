package com.epam.brest.service.xml;

import com.epam.brest.model.Track;
import com.epam.brest.service.TrackService;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class TrackExportXmlServiceImpl implements TrackExportXmlService{

    private final TrackService trackService;
    private final XmlMapper mapper = XmlMapper.xmlBuilder()
            .enable(SerializationFeature.INDENT_OUTPUT)
            .enable(ToXmlGenerator.Feature.WRITE_XML_DECLARATION)
            .build();
    @Value("${spring.jackson.date-format}")
    private String dateFormat;

    @Override
    public List<Track> exportTracksXml(HttpServletResponse response) throws IOException {
        log.debug("exportTracksXml()");
        mapper.registerModule(new JavaTimeModule());
        mapper.setDateFormat(new SimpleDateFormat(dateFormat));
        List<Track> trackList = trackService.findAllTracks();
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(mapper.writer().withRootName("Tracks").writeValueAsString(trackList)
                .getBytes(StandardCharsets.UTF_8));
        outputStream.flush();
        outputStream.close();
        return trackList;
    }
    @Override
    public String exportTracksXmlAsString() throws IOException {
        log.debug("exportTracksXmlAsString()");
        List<Track> trackList = trackService.findAllTracks();
        mapper.registerModule(new JavaTimeModule());
        mapper.setDateFormat(new SimpleDateFormat(dateFormat));
        return mapper.writer().withRootName("Tracks").writeValueAsString(trackList);
    }
}
