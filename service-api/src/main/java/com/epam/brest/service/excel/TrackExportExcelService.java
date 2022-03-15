package com.epam.brest.service.excel;

import com.epam.brest.model.Track;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface TrackExportExcelService {

    List<Track> exportTracksExcel(HttpServletResponse response);

}
