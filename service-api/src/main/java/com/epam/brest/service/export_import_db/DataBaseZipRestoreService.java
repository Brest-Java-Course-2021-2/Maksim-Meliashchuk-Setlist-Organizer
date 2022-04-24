package com.epam.brest.service.export_import_db;

import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public interface DataBaseZipRestoreService {

    void exportData(ZipOutputStream zipOutputStream);
    void deleteAllData();
    void importData(ZipInputStream zipInputStream);

}
