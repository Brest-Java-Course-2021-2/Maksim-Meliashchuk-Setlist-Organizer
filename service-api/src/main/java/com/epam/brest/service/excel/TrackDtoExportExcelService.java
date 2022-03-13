package com.epam.brest.service.excel;

import com.epam.brest.model.TrackDto;

import java.util.List;

public interface TrackDtoExportExcelService {

    List<TrackDto> exportTracksDtoExcel();
}
