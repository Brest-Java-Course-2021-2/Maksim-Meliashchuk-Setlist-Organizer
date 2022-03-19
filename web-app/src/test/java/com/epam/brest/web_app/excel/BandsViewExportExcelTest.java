package com.epam.brest.web_app.excel;

import com.epam.brest.model.BandDto;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.servlet.ModelAndView;

import java.io.ByteArrayInputStream;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BandsViewExportExcelTest {

    private final MockHttpServletRequest request = new MockHttpServletRequest();
    private final MockHttpServletResponse response = new MockHttpServletResponse();

    @Test
    @SuppressWarnings("resource")
    void buildExcelDocument() throws Exception {
        ModelAndView mav = new ModelAndView();
        mav.setView(new BandsViewExportExcel());
        mav.addObject("bands", Arrays.asList(create(0), create(1)));
        mav.getView().render(mav.getModelMap(), request, response);
        Workbook wb = new XSSFWorkbook(new ByteArrayInputStream(response.getContentAsByteArray()));
        assertEquals(wb.getSheetName(0), "Bands");
        Sheet sheet = wb.getSheet("Bands");
        Row row = sheet.getRow(0);
        assertEquals(row.getCell(0).getStringCellValue(), "ID");
        assertEquals(row.getCell(4).getStringCellValue(), "DURATION OF THE REPERTOIRE");
        row = sheet.getRow(1);
        assertEquals(row.getCell(1).getStringCellValue(), "band0");
        assertEquals(row.getCell(2).getStringCellValue(), "band0details0");
    }

    private BandDto create(int index) {
        BandDto bandDto = new BandDto();
        bandDto.setBandId(index);
        bandDto.setBandName("band" + index);
        bandDto.setBandCountTrack(100 + index);
        bandDto.setBandRepertoireDuration(1000 + index);
        bandDto.setBandDetails(bandDto.getBandName() + "details" + index);
        return bandDto;
    }
}