package com.epam.brest.service.excel;

import com.epam.brest.model.Track;
import com.epam.brest.service.TrackService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Service
public class TrackExportExcelServiceImpl implements TrackExportExcelService {

    private final TrackService trackService;

    private final Logger logger = LogManager.getLogger(TrackExportExcelServiceImpl.class);

    public TrackExportExcelServiceImpl(TrackService trackService) {
        this.trackService = trackService;
    }

    @Override
    public List<Track> exportTracksExcel(HttpServletResponse response){
        logger.debug("exportTracksExcel()");
        List<Track> trackList = trackService.findAllTracks();
        try {
            new ExportExcelUtil().exportToExcel(response, trackList);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return trackList;
    }
}
