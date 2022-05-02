package com.epam.brest.delegateimpl;

import com.epam.brest.api.UploadZipFileApiDelegate;
import com.epam.brest.service.export_import_db.DataBaseZipRestoreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import java.io.IOException;

@Service
public class UploadZipFileDelegateImpl implements UploadZipFileApiDelegate {

    private static final Logger LOGGER = LoggerFactory.getLogger(DownloadZipFileDelegateImpl.class);

    private final DataBaseZipRestoreService dataBaseZipRestoreService;

    public UploadZipFileDelegateImpl(DataBaseZipRestoreService dataBaseZipRestoreService) {
        this.dataBaseZipRestoreService = dataBaseZipRestoreService;
    }

    @Override
    public ResponseEntity<Void> uploadingZipFile(MultipartFile file) {
        LOGGER.debug("uploadingZipFile({})", file.getName());
        try {
            dataBaseZipRestoreService.importData(file);
        } catch (IOException | SAXException e) {
            throw new RuntimeException(e);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
