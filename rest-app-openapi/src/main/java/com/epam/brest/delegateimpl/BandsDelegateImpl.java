package com.epam.brest.delegateimpl;

import com.epam.brest.model.Band;
import com.epam.brest.api.BandsApiDelegate;
import com.epam.brest.service.BandService;
import com.epam.brest.service.excel.BandExportExcelService;
import com.epam.brest.service.excel.BandImportExcelService;
import com.epam.brest.service.faker.BandFakerService;
import com.epam.brest.service.xml.BandExportXmlService;
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
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Service
public class BandsDelegateImpl implements BandsApiDelegate {

    private static final Logger LOGGER = LoggerFactory.getLogger(BandsDelegateImpl.class);

    private final BandService bandService;
    private final BandFakerService bandFakerService;
    private final BandExportExcelService bandExportExcelService;
    private final BandImportExcelService bandImportExcelService;
    private final BandExportXmlService bandExportXmlService;

    public BandsDelegateImpl(BandService bandService, BandFakerService bandFakerService,
                             BandExportExcelService bandExportExcelService, BandImportExcelService bandImportExcelService,
                             BandExportXmlService bandExportXmlService) {
        this.bandService = bandService;
        this.bandFakerService = bandFakerService;
        this.bandExportExcelService = bandExportExcelService;
        this.bandImportExcelService = bandImportExcelService;
        this.bandExportXmlService = bandExportXmlService;
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
        headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
        if (response != null) {
            response.setHeader("Content-Disposition", "attachment; filename=Bands.xlsx");
        }
        bandExportExcelService.exportBandsExcel(response);
        return new ResponseEntity<>(headers, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Integer> importBandFromExcel(MultipartFile file) {
        LOGGER.debug("importBandFromExcel({})", file.getName());
        int result = 0;
        try {
            result = bandImportExcelService.importBandsExcel(file).size();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Resource> exportToXmlAllBands() {
        LOGGER.debug("exportToXmlAllBands()");
        HttpHeaders headers = new HttpHeaders();
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpServletResponse response = ((ServletRequestAttributes)requestAttributes).getResponse();
        headers.setContentType(MediaType.parseMediaType("application/xml"));
        if (response != null) {
            response.setHeader("Content-Disposition", "attachment; filename=Bands.xml");
        }
        try {
            bandExportXmlService.exportBandsXml(response);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new ResponseEntity<>(headers, HttpStatus.OK);
    }
}
