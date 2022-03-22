package com.epam.brest.service.excel;

import com.epam.brest.model.Track;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface TrackImportExcel {

    List<Track> importTrackExcel(MultipartFile files);
}
