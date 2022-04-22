package com.epam.brest.web_app.controller;

import com.epam.brest.ApiException;
import com.epam.brest.web_app.condition.ApiClientCondition;
import io.swagger.client.api.DownloadZipControllerApi;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Slf4j
@RequiredArgsConstructor
@Controller
@Conditional(ApiClientCondition.class)
public class DownloadZipControllerApiClient {

    private final DownloadZipControllerApi downloadZipControllerApi;

    @GetMapping(value = "/downloadZipFile")
    public void downloadZipFile(HttpServletResponse response) throws ApiException, IOException {
        log.debug("downloadZipFile()");
        InputStream is = new FileInputStream(downloadZipControllerApi.downloadZipFile());
        response.setContentType("application/zip");
        response.setHeader("Content-Disposition", "attachment; filename=database.zip");
        IOUtils.copy(is, response.getOutputStream());
        response.flushBuffer();
    }
}
