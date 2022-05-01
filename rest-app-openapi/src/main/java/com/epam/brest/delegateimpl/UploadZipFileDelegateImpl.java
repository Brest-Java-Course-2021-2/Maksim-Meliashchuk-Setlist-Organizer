package com.epam.brest.delegateimpl;

import com.epam.brest.api.UploadZipFileApiDelegate;
import com.epam.brest.service.xml.BandXmlService;
import com.epam.brest.service.xml.TrackXmlService;
import com.epam.brest.service.zip.DownloadZipService;
import com.epam.brest.service.zip.UploadZipService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class UploadZipFileDelegateImpl implements UploadZipFileApiDelegate {

    private static final Logger LOGGER = LoggerFactory.getLogger(DownloadZipFileDelegateImpl.class);

    private final UploadZipService uploadZipService;

    public UploadZipFileDelegateImpl(UploadZipService uploadZipService) {
        this.uploadZipService = uploadZipService;
    }

    @Override
    public ResponseEntity<Void> uploadingZipFile(MultipartFile file) {
        LOGGER.debug("uploadingZipFile({})", file.getName());
        try {
            uploadZipService.uploadZipFile(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new ResponseEntity<>(HttpStatus.OK);

    }
}
