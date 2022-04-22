package com.epam.brest.service.downloadzip;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public interface DownloadZipService {

    Integer downloadZipFile(HttpServletResponse response, Map<String, String> listOfFiles);
}
