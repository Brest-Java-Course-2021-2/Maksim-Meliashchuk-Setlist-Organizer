package com.epam.brest.dao;

import com.epam.brest.model.dto.TrackDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class TrackDtoDaoJdbcImpl implements TrackDtoDao{

    private final Logger logger = LogManager.getLogger(TrackDtoDaoJdbcImpl.class);

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Value("${SQL_FIND_ALL_TRACKS_WITH_BAND_NAME}")
    private String sqlFindAllTracksWithBandName;

    @Value("${SQL_FIND_ALL_TRACKS_WITH_RELEASE_DATE_FILTER}")
    private String sqlFindAllTracksWithReleaseDateFilter;

    public TrackDtoDaoJdbcImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<TrackDto> findAllTracksWithBandName() {
        logger.debug("Start: findAllTracksWithBandName()");
        return namedParameterJdbcTemplate.query(sqlFindAllTracksWithBandName,
                BeanPropertyRowMapper.newInstance(TrackDto.class));
    }

    @Override
    public List<TrackDto> findAllTracksWithReleaseDateFilter(LocalDate fromDate, LocalDate toDate) {
        logger.debug("Start: findAllTracksWithReleaseDateFilter()");
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
        if (fromDate == null && toDate == null) {
            return namedParameterJdbcTemplate.query(sqlFindAllTracksWithBandName,
                    BeanPropertyRowMapper.newInstance(TrackDto.class));
        }
        if (fromDate == null){
            sqlParameterSource.addValue("fromDate", toDate);
            sqlParameterSource.addValue("toDate", toDate);
        }else if (toDate == null){
            sqlParameterSource.addValue("fromDate", fromDate);
            sqlParameterSource.addValue("toDate", fromDate);
        }else {
            sqlParameterSource.addValue("fromDate", fromDate);
            sqlParameterSource.addValue("toDate", toDate);
        }
        return namedParameterJdbcTemplate.query(sqlFindAllTracksWithReleaseDateFilter, sqlParameterSource,
                BeanPropertyRowMapper.newInstance(TrackDto.class));
    }
}
