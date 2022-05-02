package com.epam.brest.delegateimpl;

import com.epam.brest.api.DownloadZipFileApiDelegate;
import com.epam.brest.service.export_import_db.DataBaseZipRestoreService;
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
import java.util.Objects;

@Service
public class DownloadZipFileDelegateImpl implements DownloadZipFileApiDelegate {

    private static final Logger LOGGER = LoggerFactory.getLogger(DownloadZipFileDelegateImpl.class);

    private final DataBaseZipRestoreService dataBaseZipRestoreService;

    public DownloadZipFileDelegateImpl(DataBaseZipRestoreService dataBaseZipRestoreService) {
        this.dataBaseZipRestoreService = dataBaseZipRestoreService;
    }

    @Override
    public ResponseEntity<Resource> downloadZipFile() {
        LOGGER.debug("exportToXmlAllBands()");
        HttpHeaders headers = new HttpHeaders();
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpServletResponse response = ((ServletRequestAttributes) Objects.requireNonNull(requestAttributes)).getResponse();
        Objects.requireNonNull(response).setContentType("application/zip");
        response.setHeader("Content-Disposition", "attachment; filename=download.zip");
        try {
            dataBaseZipRestoreService.exportData(response);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new ResponseEntity<>(headers, HttpStatus.OK);
    }



}
