package com.epam.brest.service.excel;

import com.epam.brest.model.Band;
import com.epam.brest.service.BandService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class BandImportExcelServiceImpl implements BandImportExcelService{

    private final Logger logger = LogManager.getLogger(BandImportExcelServiceImpl.class);

    private final BandService bandService;

    public BandImportExcelServiceImpl(BandService bandService) {
        this.bandService = bandService;
    }

    @Override
    public List<Band> importBandsExcel(MultipartFile files) throws IOException {
        logger.debug("importBandsExcel({})", files.getName());
        List<Band> bands = new ArrayList<>();
        XSSFWorkbook workbook = new XSSFWorkbook(files.getInputStream());
        XSSFSheet worksheet = workbook.getSheetAt(0);
        DataFormatter formatter = new DataFormatter();
        for (int index = 0; index < worksheet.getPhysicalNumberOfRows(); index++) {
            if (index > 0) {
                XSSFRow row = worksheet.getRow(index);
                try {
                    Band band = Band.builder()
                            .bandName(formatter.formatCellValue(row.getCell(1)))
                            .bandDetails(formatter.formatCellValue(row.getCell(2)))
                            .build();
                    bandService.create(band);
                    bands.add(band);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return bands;
    }
}
