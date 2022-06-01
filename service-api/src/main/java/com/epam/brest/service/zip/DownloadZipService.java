package com.epam.brest.service.zip;

import org.springframework.security.access.prepost.PreAuthorize;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;
@PreAuthorize("hasAnyRole('admin')")
public interface DownloadZipService {

    Integer downloadZipFile(HttpServletResponse response, Map<String, String> listOfFiles);
}
