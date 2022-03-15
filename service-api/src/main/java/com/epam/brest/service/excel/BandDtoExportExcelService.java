package com.epam.brest.service.excel;

import com.epam.brest.model.BandDto;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface BandDtoExportExcelService {

    List<BandDto> exportBandsDtoExcel(HttpServletResponse response);
}
