package com.epam.brest.service.excel;

import com.epam.brest.model.TrackDto;
import com.epam.brest.service.TrackDtoService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Service
public class TrackDtoExportExcelServiceImpl implements TrackDtoExportExcelService {

    private final Logger logger = LogManager.getLogger(TrackDtoExportExcelServiceImpl.class);

    private final TrackDtoService trackService;

    public TrackDtoExportExcelServiceImpl(TrackDtoService trackService) {
        this.trackService = trackService;
    }

    @Override
    public List<TrackDto> exportTracksDtoExcel(HttpServletResponse response) {
        logger.debug("exportTracksDtoExcel()");
        List<TrackDto> trackList = trackService.findAllTracksWithBandName();
        try {
            new ExportExcelUtil().exportToExcel(response, trackList);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return trackList;
    }
}
