package com.epam.brest.service.excel;

import com.epam.brest.model.Band;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface BandImportExcelService {

    List<Band> importBandsExcel(MultipartFile files) throws IOException;

}
