package com.epam.brest.web_app.excel;

import com.epam.brest.model.TrackDto;
import org.apache.commons.lang3.time.DurationFormatUtils;
import org.apache.poi.ss.usermodel.*;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

public class RepertoireViewExportExcel extends AbstractXlsxView {
    @Override
    protected void buildExcelDocument(Map<String, Object> model, Workbook workbook,
                                      HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.addHeader("Content-Disposition", "attachment;fileName=Repertoire.xlsx");
        @SuppressWarnings("unchecked")
        List<TrackDto> list = (List<TrackDto>) model.get("tracks");
        Sheet sheet = workbook.createSheet("Repertoire");
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
        header.createCell(3).setCellValue("BAND ID");
        header.createCell(4).setCellValue("BAND NAME");
        header.createCell(5).setCellValue("TEMPO");
        header.createCell(6).setCellValue("DURATION");
        header.createCell(7).setCellValue("RELEASE DATE");
        header.createCell(8).setCellValue("LINK");

        int rowNum = 1;
        for(TrackDto trackDto : list) {
            Row row = sheet.createRow(rowNum++);
            row.setRowStyle(rowStyle);
            if (trackDto.getTrackId() != null) {
                row.createCell(0).setCellValue(trackDto.getTrackId());
            }
            if (trackDto.getTrackName() != null) {
                row.createCell(1).setCellValue(trackDto.getTrackName());
            }
            if (trackDto.getTrackDetails() != null) {
                row.createCell(2).setCellValue(trackDto.getTrackDetails());
            }
            if (trackDto.getTrackBandId() != null) {
                row.createCell(3).setCellValue(trackDto.getTrackBandId());
            }
            if (trackDto.getTrackBandName() != null) {
                row.createCell(4).setCellValue(trackDto.getTrackBandName());
            }
            if (trackDto.getTrackTempo()!= null) {
                row.createCell(5).setCellValue(trackDto.getTrackTempo());
            }
            if (trackDto.getTrackDuration()!= null) {
                row.createCell(6).setCellValue(
                        DurationFormatUtils.formatDuration(Long.parseLong(trackDto.getTrackDuration().toString()),
                                "HH:mm:ss", true));
            }
            if (trackDto.getTrackReleaseDate()!= null) {
                row.createCell(7).setCellValue(trackDto.getTrackReleaseDate()
                        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            }
            if (trackDto.getTrackLink()!= null) {
                row.createCell(8).setCellValue(trackDto.getTrackLink());
            }

        }
    }

}
