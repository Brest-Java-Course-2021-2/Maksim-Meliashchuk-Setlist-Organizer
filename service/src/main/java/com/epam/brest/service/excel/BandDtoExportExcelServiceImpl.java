package com.epam.brest.service.excel;

import com.epam.brest.model.BandDto;
import com.epam.brest.service.BandDtoService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Service
public class BandDtoExportExcelServiceImpl implements BandDtoExportExcelService{

    private final Logger logger = LogManager.getLogger(BandDtoExportExcelServiceImpl.class);

    private final BandDtoService bandService;

    public BandDtoExportExcelServiceImpl(BandDtoService bandService) {
        this.bandService = bandService;
    }

    @Override
    public List<BandDto> exportBandsDtoExcel(HttpServletResponse response) {
        logger.debug("exportBandsDtoExcel()");
        List<BandDto> bandList = bandService.findAllWithCountTrack();
        try {
            new ExportExcelUtil().exportToExcel(response, bandList);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bandList;
    }
}
