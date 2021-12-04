package com.epam.brest.dao;

import com.epam.brest.model.dto.TrackDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TrackDtoDaoJdbcImpl implements TrackDtoDao{

    private final Logger logger = LogManager.getLogger(TrackDtoDaoJdbcImpl.class);

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Value("${SQL_FIND_ALL_TRACKS_WITH_BAND_NAME}")
    private String sqlFindAllTracksWithBandName;

    public TrackDtoDaoJdbcImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<TrackDto> findAllTracksWithBandName() {
        logger.debug("Start: findAllTracksWithBandName()");
        return namedParameterJdbcTemplate.query(sqlFindAllTracksWithBandName,
                BeanPropertyRowMapper.newInstance(TrackDto.class));
    }
}
