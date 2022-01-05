package com.epam.brest.dao;

import com.epam.brest.model.dto.TrackDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class TrackDtoDaoJdbcImpl implements TrackDtoDao{

    private final Logger logger = LogManager.getLogger(TrackDtoDaoJdbcImpl.class);

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Value("${SQL_FIND_ALL_TRACKS_WITH_BAND_NAME}")
    private String sqlFindAllTracksWithBandName;

    @Value("${SQL_FIND_ALL_TRACKS_WITH_BAND_NAME_BY_BAND_ID}")
    private String sqlFindAllTracksWithBandNameByBandId;

    @Value("${SQL_FIND_ALL_TRACKS_WITH_RELEASE_DATE_FILTER}")
    private String sqlFindAllTracksWithReleaseDateFilter;

    @Value("${SQL_FIND_ALL_TRACKS_WITH_RELEASE_DATE_FROM}")
    private String sqlFindAllTracksWithReleaseDateFrom;

    @Value("${SQL_FIND_ALL_TRACKS_WITH_RELEASE_DATE_TO}")
    private String sqlFindAllTracksWithReleaseDateTo;

    public TrackDtoDaoJdbcImpl() {
    }

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
    public List<TrackDto> findAllTracksWithBandNameByBandId(Integer bandId) {
        logger.debug("Start: findAllTracksWithBandNameByBandId {}()", bandId);
        SqlParameterSource sqlParameterSource =
                new MapSqlParameterSource("bandId", bandId);
        return namedParameterJdbcTemplate.query(sqlFindAllTracksWithBandNameByBandId, sqlParameterSource,
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
            sqlParameterSource.addValue("toDate", toDate);
            return namedParameterJdbcTemplate.query(sqlFindAllTracksWithReleaseDateTo, sqlParameterSource,
                    BeanPropertyRowMapper.newInstance(TrackDto.class));
        }else if (toDate == null){
            sqlParameterSource.addValue("fromDate", fromDate);
            return namedParameterJdbcTemplate.query(sqlFindAllTracksWithReleaseDateFrom, sqlParameterSource,
                    BeanPropertyRowMapper.newInstance(TrackDto.class));
        }else {
            sqlParameterSource.addValue("fromDate", fromDate);
            sqlParameterSource.addValue("toDate", toDate);
        }
        return namedParameterJdbcTemplate.query(sqlFindAllTracksWithReleaseDateFilter, sqlParameterSource,
                BeanPropertyRowMapper.newInstance(TrackDto.class));
    }
}
