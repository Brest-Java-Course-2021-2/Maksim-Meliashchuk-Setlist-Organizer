package com.epam.brest.service.excel;

import com.epam.brest.model.Band;
import com.epam.brest.service.BandService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Service
public class BandExportExcelServiceImpl implements BandExportExcelService{

    private final Logger logger = LogManager.getLogger(BandExportExcelServiceImpl.class);

    private final BandService bandService;

    public BandExportExcelServiceImpl(BandService bandService) {
        this.bandService = bandService;
    }

    @Override
    public List<Band> exportBandsExcel(HttpServletResponse response) {
        logger.debug("exportBandsDtoExcel()");
        List<Band> bandList = bandService.findAllBands();
        try {
            new ExportExcelUtil().exportToExcel(response, bandList);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bandList;
    }

}
