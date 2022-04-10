package com.epam.brest.web_app.excel;

import com.epam.brest.model.TrackDto;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.servlet.ModelAndView;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RepertoireViewExportExcelTest {

    private final MockHttpServletRequest request = new MockHttpServletRequest();
    private final MockHttpServletResponse response = new MockHttpServletResponse();

    @Test
    @SuppressWarnings("resource")
    void buildExcelDocument() throws Exception {
        ModelAndView mav = new ModelAndView();
        mav.setView(new RepertoireViewExportExcel());
        mav.addObject("tracks", Arrays.asList(create(0), create(1)));
        Objects.requireNonNull(mav.getView()).render(mav.getModelMap(), request, response);
        Workbook wb = new XSSFWorkbook(new ByteArrayInputStream(response.getContentAsByteArray()));
        assertEquals(wb.getSheetName(0), "Repertoire");
        Sheet sheet = wb.getSheet("Repertoire");
        Row row = sheet.getRow(0);
        assertEquals(row.getCell(0).getStringCellValue(), "ID");
        assertEquals(row.getCell(8).getStringCellValue(), "LINK");
        row = sheet.getRow(1);
        assertEquals(row.getCell(2).getStringCellValue(), "track0details0");
        assertEquals(row.getCell(4).getStringCellValue(), "band0");
    }

    private TrackDto create(int index) {
        TrackDto trackDto = new TrackDto();
        LocalDate releaseDate = LocalDate.parse("2012-03-12");
        trackDto.setTrackId(index);
        trackDto.setTrackName("track" + index);
        trackDto.setTrackDuration(10000 + index);
        trackDto.setTrackBandName("band" + index);
        trackDto.setTrackReleaseDate(releaseDate.plusYears(index));
        trackDto.setTrackLink("link" + index);
        trackDto.setTrackDetails(trackDto.getTrackName() + "details" + index);
        return trackDto;
    }
}