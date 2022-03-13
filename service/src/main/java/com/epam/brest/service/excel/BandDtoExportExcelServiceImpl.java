package com.epam.brest.service.excel;

import com.epam.brest.model.BandDto;
import com.epam.brest.service.BandDtoService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BandDtoExportExcelServiceImpl implements BandDtoExportExcelService{

    private final Logger logger = LogManager.getLogger(BandDtoExportExcelServiceImpl.class);

    private final BandDtoService bandService;

    public BandDtoExportExcelServiceImpl(BandDtoService bandService) {
        this.bandService = bandService;
    }

    @Override
    public List<BandDto> exportBandsDtoExcel() {
        logger.debug("exportBandsDtoExcel()");
        List<BandDto> bandList = bandService.findAllWithCountTrack();
        ExportExcelUtil.exportToExcel(BandDto.class, bandList);
        return bandList;
    }
}
