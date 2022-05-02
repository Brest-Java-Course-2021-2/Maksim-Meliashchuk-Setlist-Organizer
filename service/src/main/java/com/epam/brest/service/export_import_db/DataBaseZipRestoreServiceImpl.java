package com.epam.brest.service.export_import_db;

import com.epam.brest.service.BandService;
import com.epam.brest.service.TrackService;
import com.epam.brest.service.xml.BandXmlService;
import com.epam.brest.service.xml.TrackXmlService;
import com.epam.brest.service.zip.DownloadZipService;
import com.epam.brest.service.zip.UploadZipService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class DataBaseZipRestoreServiceImpl implements DataBaseZipRestoreService{

    private final BandXmlService bandXmlService;
    private final TrackXmlService trackXmlService;
    private final DownloadZipService downloadZipService;
    private final UploadZipService uploadZipService;
    private final BandService bandService;
    private final TrackService trackService;

    @Override
    public void exportData(HttpServletResponse response) throws IOException {
        log.debug("exportData()");
        Map<String, String> listOfFiles = new HashMap<>();
        listOfFiles.put("Bands.xml", bandXmlService.exportBandsXmlAsString());
        listOfFiles.put("Tracks.xml", trackXmlService.exportTracksXmlAsString());
        downloadZipService.downloadZipFile(response, listOfFiles);
    }

    @Override
    public void deleteAllData() {
        log.debug("deleteAllData()");
        trackService.deleteAllTracks();
        bandService.deleteAllBands();
    }

    @Override
    public void importData(MultipartFile file) throws IOException, SAXException {
        log.debug("importData()");
        this.deleteAllData();
        Map<String, String> listOfFiles = uploadZipService.uploadZipFile(file);
        bandXmlService.importBandsAsXml(listOfFiles.get("Bands.xml"));
        trackXmlService.importTracksAsXml(listOfFiles.get("Tracks.xml"));
    }
}
