package com.epam.brest.service.xml;

import com.epam.brest.model.Track;
import com.epam.brest.service.TrackService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@EnableAutoConfiguration
public class TrackExportXmlServiceImpl implements TrackExportXmlService{

    private final TrackService trackService;

    private final ObjectMapper objectMapper;

    @Override
    public List<Track> exportTracksXml(HttpServletResponse response) throws IOException {
        log.debug("exportTracksXml()");
        List<Track> trackList = trackService.findAllTracks();
        XmlMapper mapper = new XmlMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.setDateFormat(objectMapper.getDateFormat());
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(mapper.writer().withRootName(Track.class.getSimpleName())
                .writeValueAsString(trackList).getBytes(StandardCharsets.UTF_8));
        outputStream.flush();
        outputStream.close();
        return trackList;
    }
}
