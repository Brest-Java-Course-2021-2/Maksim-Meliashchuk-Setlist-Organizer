package com.epam.brest.service.excel;

import com.epam.brest.model.Band;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface BandExportExcelService {

    List<Band> exportBandsExcel(HttpServletResponse response);

}
