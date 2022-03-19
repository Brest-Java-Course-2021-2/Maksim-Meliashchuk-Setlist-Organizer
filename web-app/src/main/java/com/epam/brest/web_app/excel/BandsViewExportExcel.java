package com.epam.brest.web_app.excel;

import com.epam.brest.model.BandDto;
import org.apache.commons.lang3.time.DurationFormatUtils;
import org.apache.poi.ss.usermodel.*;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public class BandsViewExportExcel extends AbstractXlsxView {
    @Override
    protected void buildExcelDocument(Map<String, Object> model, Workbook workbook,
                                      HttpServletRequest request, HttpServletResponse response) throws Exception {

        response.addHeader("Content-Disposition", "attachment;fileName=Bands.xlsx");
        @SuppressWarnings("unchecked")
        List<BandDto> list = (List<BandDto>) model.get("bands");
        Sheet sheet = workbook.createSheet("Bands");
        Row header = sheet.createRow(0);

        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        header.setRowStyle(headerStyle);

        CellStyle rowStyle = workbook.createCellStyle();
        rowStyle.setWrapText(true);

        header.createCell(0).setCellValue("ID");
        header.createCell(1).setCellValue("NAME");
        header.createCell(2).setCellValue("DETAILS");
        header.createCell(3).setCellValue("COUNT OF TRACKS");
        header.createCell(4).setCellValue("DURATION OF THE REPERTOIRE");

        int rowNum = 1;
        for(BandDto bandDto : list) {
            Row row = sheet.createRow(rowNum++);
            row.setRowStyle(rowStyle);
            if (bandDto.getBandId() != null) {
                row.createCell(0).setCellValue(bandDto.getBandId());
            }
            if (bandDto.getBandName() != null) {
                row.createCell(1).setCellValue(bandDto.getBandName());
            }
            if (bandDto.getBandDetails() != null) {
                row.createCell(2).setCellValue(bandDto.getBandDetails());

            }
            if (bandDto.getBandCountTrack() != null) {
                row.createCell(3).setCellValue(bandDto.getBandCountTrack());
            }
            if (bandDto.getBandRepertoireDuration() != null) {
                row.createCell(4).setCellValue(
                        DurationFormatUtils.formatDuration(Long.parseLong(bandDto.getBandRepertoireDuration().toString()),
                                "HH:mm:ss", true));
            }

        }
    }
}
