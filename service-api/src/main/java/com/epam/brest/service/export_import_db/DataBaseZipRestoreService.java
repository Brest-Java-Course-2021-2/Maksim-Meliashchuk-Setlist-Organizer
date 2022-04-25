package com.epam.brest.service.export_import_db;

import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface DataBaseZipRestoreService {

    void exportData(HttpServletResponse response) throws IOException;
    void deleteAllData();
    void importData(MultipartFile file) throws IOException, SAXException;

}
