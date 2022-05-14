package com.epam.brest.service.excel;

import com.epam.brest.model.Band;
import org.springframework.security.access.prepost.PreAuthorize;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@PreAuthorize("hasAnyRole('user', 'admin')")
public interface BandExportExcelService {

    List<Band> exportBandsExcel(HttpServletResponse response);

}
