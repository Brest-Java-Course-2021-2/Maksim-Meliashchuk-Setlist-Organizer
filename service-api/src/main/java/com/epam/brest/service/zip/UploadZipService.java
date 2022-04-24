package com.epam.brest.service.zip;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

public interface UploadZipService {

    Map<String, String> uploadZipFile (MultipartFile files) throws IOException;
}
