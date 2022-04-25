package com.epam.brest.service.xml;

import com.epam.brest.model.Band;
import com.epam.brest.service.BandService;
import com.epam.brest.service.sax.SaxParserCustom;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BandXmlServiceImpl implements BandXmlService {

    private final BandService bandService;
    private final XmlMapper mapper = XmlMapper.xmlBuilder()
            .enable(SerializationFeature.INDENT_OUTPUT)
            .enable(ToXmlGenerator.Feature.WRITE_XML_DECLARATION)
            .build();
    private final SaxParserCustom saxParserCustom;
    @Override
    public List<Band> exportBandsXml(HttpServletResponse response) throws IOException {
        log.debug("exportBandsXml()");
        List<Band> bandList = bandService.findAllBands();
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(mapper.writer().withRootName("Bands").writeValueAsString(bandList)
                .getBytes(StandardCharsets.UTF_8));
        outputStream.flush();
        outputStream.close();
        return bandList;
    }

    @Override
    public String exportBandsXmlAsString() throws IOException {
        log.debug("exportBandsXmlAsString()");
        List<Band> bandList = bandService.findAllBands();
        return mapper.writer().withRootName("Bands").writeValueAsString(bandList);
    }

    @Override
    public void importBandsAsXml(String content) throws IOException, SAXException {
        log.debug("importBandsAsXml({})", content);
        List<Band> bandList = saxParserCustom.parseBands(content);
        bandList.forEach(bandService::create);
    }
}
