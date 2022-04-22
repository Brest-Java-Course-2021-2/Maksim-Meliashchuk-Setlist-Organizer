package com.epam.brest.service.downloadzip;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Slf4j
@Service
@RequiredArgsConstructor
public class DownloadZipServiceImpl implements DownloadZipService {

    @Override
    public Integer downloadZipFile(HttpServletResponse response, Map<String, String> listOfFiles) {
        log.debug("downloadZipFile()");
        try (ZipOutputStream zipOutputStream = new ZipOutputStream(response.getOutputStream())) {
            for (String fileName : listOfFiles.keySet()) {
                ZipEntry zipEntry = new ZipEntry(fileName);
                zipOutputStream.putNextEntry(zipEntry);
                zipEntry.setTime(System.currentTimeMillis());
                StreamUtils.copy(IOUtils.toInputStream(listOfFiles.get(fileName), StandardCharsets.UTF_8), zipOutputStream);
                zipOutputStream.closeEntry();
            }
            zipOutputStream.finish();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return listOfFiles.keySet().size();
    }
}
