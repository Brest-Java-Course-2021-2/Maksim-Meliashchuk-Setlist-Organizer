package com.epam.brest.service.excel;

import com.epam.brest.model.Track;
import com.epam.brest.service.TrackService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class TrackImportExcelServiceImpl implements TrackImportExcelService {

    private final Logger logger = LogManager.getLogger(TrackImportExcelServiceImpl.class);

    private final TrackService trackService;

    public TrackImportExcelServiceImpl(TrackService trackService) {
        this.trackService = trackService;
    }

    @Override
    public List<Track> importTrackExcel(MultipartFile files) throws IOException {
        logger.debug("importTrackExcel({})", files.getName());
        List<Track> tracks = new ArrayList<>();
        XSSFWorkbook workbook = new XSSFWorkbook(files.getInputStream());
        XSSFSheet worksheet = workbook.getSheetAt(0);
        DataFormatter formatter = new DataFormatter();
        for (int index = 0; index < worksheet.getPhysicalNumberOfRows(); index++) {
            if (index > 0) {
                XSSFRow row = worksheet.getRow(index);
                try {
                    Track track = Track.builder()
                            .trackName(formatter.formatCellValue(row.getCell(1)))
                            .trackBandId(Integer.valueOf(formatter.formatCellValue(row.getCell(2))))
                            .trackTempo(Integer.valueOf(formatter.formatCellValue(row.getCell(3))))
                            .trackDuration(Integer.valueOf(formatter.formatCellValue(row.getCell(4))))
                            .trackDetails(formatter.formatCellValue(row.getCell(5)))
                            .trackLink(formatter.formatCellValue(row.getCell(6)))
                            .trackReleaseDate(LocalDate.parse(formatter.formatCellValue(row.getCell(7))))
                            .build();
                    trackService.create(track);
                    tracks.add(track);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return tracks;
    }
}
