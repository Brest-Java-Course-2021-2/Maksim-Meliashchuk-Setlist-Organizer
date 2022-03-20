package com.epam.brest.openapi.delegateimpl;

import com.epam.brest.model.BandDto;
import com.epam.brest.openapi.api.BandsDtoApiDelegate;
import com.epam.brest.service.excel.BandDtoExportExcelService;
import com.epam.brest.service.faker.BandDtoFakerService;
import com.epam.brest.service.BandDtoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Service
public class BandsDtoDelegateImpl implements BandsDtoApiDelegate {

    private static final Logger LOGGER = LoggerFactory.getLogger(BandsDtoDelegateImpl.class);

    private final BandDtoService bandDtoService;

    private final BandDtoFakerService bandDtoFakerService;

    private final BandDtoExportExcelService bandDtoExportExcelService;

    public BandsDtoDelegateImpl(BandDtoService bandDtoService, BandDtoFakerService bandDtoFakerService,
                                BandDtoExportExcelService bandDtoExportExcelService) {
        this.bandDtoService = bandDtoService;
        this.bandDtoFakerService = bandDtoFakerService;
        this.bandDtoExportExcelService = bandDtoExportExcelService;
    }

    @Override
    public ResponseEntity<List<BandDto>> bandsDto() {
        LOGGER.debug("bandsDto()");
        List<BandDto> resultList = bandDtoService.findAllWithCountTrack();
        return new ResponseEntity<>(resultList, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<BandDto>> fillBandsDtoFake(Integer size, String language) {
        LOGGER.debug("fillBandsDtoFake()");
        List<BandDto> resultList = bandDtoFakerService.fillFakeBandsDto(size, language);
        return new ResponseEntity<>(resultList, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Resource> exportToExcelAllBandsDto() {
        LOGGER.debug("exportToExcelAllBandsDto()");
        HttpHeaders headers = new HttpHeaders();
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpServletResponse response = ((ServletRequestAttributes)requestAttributes).getResponse();
        headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
        if (response != null) {
            response.setHeader("Content-Disposition", "attachment; filename=BandsDto.xlsx");
        }
        bandDtoExportExcelService.exportBandsDtoExcel(response);
        return new ResponseEntity<>(headers, HttpStatus.OK);
    }

}
