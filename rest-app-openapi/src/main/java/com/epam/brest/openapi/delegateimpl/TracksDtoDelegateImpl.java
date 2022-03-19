package com.epam.brest.openapi.delegateimpl;

import com.epam.brest.model.TrackDto;
import com.epam.brest.openapi.api.TracksDtoApiDelegate;
import com.epam.brest.service.TrackDtoService;
import com.epam.brest.service.excel.TrackDtoExportExcelService;
import com.epam.brest.service.faker.TrackDtoFakerService;
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
public class TracksDtoDelegateImpl implements TracksDtoApiDelegate {

    private static final Logger LOGGER = LoggerFactory.getLogger(TracksDtoDelegateImpl.class);


    private final TrackDtoService trackDtoService;
    private final TrackDtoFakerService trackDtoFakerService;

    private final TrackDtoExportExcelService trackDtoExportExcelService;

    public TracksDtoDelegateImpl(TrackDtoService trackDtoService,
                                 TrackDtoFakerService trackDtoFakerService,
                                 TrackDtoExportExcelService trackDtoExportExcelService) {
        this.trackDtoService = trackDtoService;
        this.trackDtoFakerService = trackDtoFakerService;
        this.trackDtoExportExcelService = trackDtoExportExcelService;
    }

    @Override
    public ResponseEntity<List<TrackDto>> findAllTracksWithBandName() {
        LOGGER.debug("findAllTracksWithBandName()");
        List<TrackDto> resultList = trackDtoService.findAllTracksWithBandName();
        return new ResponseEntity<>(resultList, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<TrackDto>> fillTracksDtoFake(Integer size, String language) {
        LOGGER.debug("fillTracksDtoFake()");
        List<TrackDto> resultList = trackDtoFakerService.fillFakeTracksDto(size, language);
        return new ResponseEntity<>(resultList, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Resource> exportToExcelAllTracksWithBandName () {
        LOGGER.debug("exportToExcelAllTracksWithBandName()");
        HttpHeaders headers = new HttpHeaders();
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpServletResponse response = ((ServletRequestAttributes)requestAttributes).getResponse();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        if (response != null) {
            response.setHeader("Content-Disposition", "attachment; filename=TracksDto.xlsx");
        }
        //TODO fix Resource
        Resource resource = (Resource) trackDtoExportExcelService.exportTracksDtoExcel(response);
        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }


}
