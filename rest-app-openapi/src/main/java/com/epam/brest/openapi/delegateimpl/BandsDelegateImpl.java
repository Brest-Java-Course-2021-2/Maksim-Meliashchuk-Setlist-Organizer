package com.epam.brest.openapi.delegateimpl;

import com.epam.brest.model.Band;
import com.epam.brest.openapi.api.BandsApiDelegate;
import com.epam.brest.service.excel.BandExportExcelService;
import com.epam.brest.service.faker.BandFakerService;
import com.epam.brest.service.BandService;
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
public class BandsDelegateImpl implements BandsApiDelegate {

    private static final Logger LOGGER = LoggerFactory.getLogger(BandsDelegateImpl.class);

    private final BandService bandService;

    private final BandFakerService bandFakerService;

    private final BandExportExcelService bandExportExcelService;

    public BandsDelegateImpl(BandService bandService, BandFakerService bandFakerService,
                             BandExportExcelService bandExportExcelService) {
        this.bandService = bandService;
        this.bandFakerService = bandFakerService;
        this.bandExportExcelService = bandExportExcelService;
    }

    @Override
    public ResponseEntity<List<Band>> bandsFake(Integer size, String language) {
        LOGGER.debug("bandsFake()");
        List<Band> resultList = bandFakerService.fillFakeBands(size, language);
        return new ResponseEntity<>(resultList, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Band> getBandById(Integer id) {
        LOGGER.debug("getBandById({})", id);
        Band result = bandService.getBandById(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Band>> bands() {
        LOGGER.debug("bands()");
        List<Band> resultList = bandService.findAllBands();
        return new ResponseEntity<>(resultList, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Integer> deleteBand(Integer id) {
        LOGGER.debug("deleteBand({})", id);
        int result =  bandService.delete(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Integer> createBand(Band band) {
        LOGGER.debug("createBand({})", band);
        Integer id = bandService.create(band);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Integer> updateBand(Band band) {
        LOGGER.debug("updateBand({})", band);
        int result = bandService.update(band);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Resource> exportToExcelAllBands() {
        LOGGER.debug("exportToExcelAllBands()");
        HttpHeaders headers = new HttpHeaders();
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpServletResponse response = ((ServletRequestAttributes)requestAttributes).getResponse();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        if (response != null) {
            response.setHeader("Content-Disposition", "attachment; filename=Bands.xlsx");
        }
        Resource resource = (Resource) bandExportExcelService.exportBandsExcel(response);
        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }
}
