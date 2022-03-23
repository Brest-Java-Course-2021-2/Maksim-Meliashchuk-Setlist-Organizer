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
public class TrackImportExcelServiceServiceImpl implements TrackImportExcelService {

    private final Logger logger = LogManager.getLogger(TrackImportExcelServiceServiceImpl.class);

    private final TrackService trackService;

    public TrackImportExcelServiceServiceImpl(TrackService trackService) {
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
                Track track = Track.builder()
                        .trackName(row.getCell(1).getStringCellValue())
                        .trackBandId(Integer.valueOf(row.getCell(2).getStringCellValue()))
                        .trackTempo(Integer.valueOf(row.getCell(3).getStringCellValue()))
                        .trackDuration(Integer.valueOf(row.getCell(4).getStringCellValue()))
                        .trackDetails(row.getCell(5).getStringCellValue())
                        .trackLink(row.getCell(6).getStringCellValue())
                        .trackReleaseDate(LocalDate.parse(formatter.formatCellValue(row.getCell(7))))
                        .build();
                trackService.create(track);
                tracks.add(track);
            }
        }
        return tracks;
    }
}
