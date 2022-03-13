package com.epam.brest.service.excel;

import com.epam.brest.model.Track;
import com.epam.brest.service.TrackService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrackExportExcelServiceImpl implements TrackExportExcelService {

    private final TrackService trackService;

    private final Logger logger = LogManager.getLogger(TrackExportExcelServiceImpl.class);

    public TrackExportExcelServiceImpl(TrackService trackService) {
        this.trackService = trackService;
    }

    @Override
    public List<Track> exportTracksExcel() {
        logger.debug("exportTracksExcel()");
        List<Track> trackList = trackService.findAllTracks();
        ExportExcelUtil.exportToExcel(Track.class, trackList);
        return trackList;
    }
}
