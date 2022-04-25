package com.epam.brest.web_app.controller;

import com.epam.brest.ApiException;
import com.epam.brest.web_app.condition.ApiClientCondition;
import io.swagger.client.api.ImportExportDatabaseApi;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

@Slf4j
@RequiredArgsConstructor
@Controller
@Conditional(ApiClientCondition.class)
public class ImportExportDbControllerApiClient {

    private final ImportExportDatabaseApi importExportDatabaseApi;

    @GetMapping(value = "/downloadZipFile")
    public void downloadZipFile(HttpServletResponse response) throws ApiException, IOException {
        log.debug("downloadZipFile()");
        InputStream is = new FileInputStream(importExportDatabaseApi.downloadZipFile());
        response.setContentType("application/zip");
        response.setHeader("Content-Disposition", "attachment; filename=database.zip");
        IOUtils.copy(is, response.getOutputStream());
        response.flushBuffer();
    }
    @PostMapping(value = "/uploadZipFile")
    public final String uploadZipFile(@RequestParam("uploadfile") final MultipartFile file) throws ApiException, IOException {
        log.debug("uploadZipFile()");
        File convertFile = new File ("database.zip");
        FileOutputStream fos = new FileOutputStream(convertFile);
        fos.write(file.getBytes());
        fos.close();
        importExportDatabaseApi.uploadingZipFile(convertFile);
        convertFile.delete();
        return "redirect:/bands";
    }
}
