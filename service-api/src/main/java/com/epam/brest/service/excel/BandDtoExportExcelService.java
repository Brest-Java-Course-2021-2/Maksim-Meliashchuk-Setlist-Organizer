package com.epam.brest.service.excel;

import com.epam.brest.model.BandDto;
import org.springframework.security.access.prepost.PreAuthorize;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@PreAuthorize("hasAnyRole('user', 'admin')")
public interface BandDtoExportExcelService {

    List<BandDto> exportBandsDtoExcel(HttpServletResponse response);
}
