package com.epam.brest.service.excel;

import com.epam.brest.model.Track;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface TrackImportExcelService {

    List<Track> importTrackExcel(MultipartFile files) throws IOException;
}
