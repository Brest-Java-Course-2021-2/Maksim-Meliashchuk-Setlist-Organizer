package com.epam.brest.delegateimpl;

import com.epam.brest.api.DownloadZipFileApiDelegate;
import com.epam.brest.service.downloadzip.DownloadZipService;
import com.epam.brest.service.xml.BandExportXmlService;
import com.epam.brest.service.xml.TrackExportXmlService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class DownloadZipFileDelegateImpl implements DownloadZipFileApiDelegate {

    private static final Logger LOGGER = LoggerFactory.getLogger(DownloadZipFileDelegateImpl.class);

    private final DownloadZipService downloadZipService;
    private final BandExportXmlService bandExportXmlService;
    private final TrackExportXmlService trackExportXmlService;


    public DownloadZipFileDelegateImpl(DownloadZipService downloadZipService, BandExportXmlService bandExportXmlService,
                                       TrackExportXmlService trackExportXmlService) {
        this.downloadZipService = downloadZipService;
        this.bandExportXmlService = bandExportXmlService;
        this.trackExportXmlService = trackExportXmlService;
    }

    @Override
    public ResponseEntity<Resource> downloadZipFile() {
        LOGGER.debug("exportToXmlAllBands()");
        HttpHeaders headers = new HttpHeaders();
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpServletResponse response = ((ServletRequestAttributes) Objects.requireNonNull(requestAttributes)).getResponse();
        Objects.requireNonNull(response).setContentType("application/zip");
        response.setHeader("Content-Disposition", "attachment; filename=download.zip");
        Map<String, String> listOfFiles = new HashMap<>();
        try {
            listOfFiles.put("Bands.xml", bandExportXmlService.exportBandsXmlAsString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            listOfFiles.put("Tracks.xml", trackExportXmlService.exportTracksXmlAsString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        downloadZipService.downloadZipFile(response ,listOfFiles);
        return new ResponseEntity<>(headers, HttpStatus.OK);
    }
}
