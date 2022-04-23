package com.epam.brest.dao.jdbc;

import com.epam.brest.dao.TrackDtoDao;
import com.epam.brest.dao.annotation.InjectSql;
import com.epam.brest.model.TrackDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
@ComponentScan("com.epam.brest.dao.annotation")
public class TrackDtoDaoJdbcImpl implements TrackDtoDao {

    private final Logger logger = LogManager.getLogger(TrackDtoDaoJdbcImpl.class);

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @InjectSql("/sql/track/findAllTracksWithBandName.sql")
    private String sqlFindAllTracksWithBandName;

    @InjectSql("/sql/track/findAllTracksWithBandNameByBandId.sql")
    private String sqlFindAllTracksWithBandNameByBandId;

    @InjectSql("/sql/track/findAllTracksWithReleaseDateFilter.sql")
    private String sqlFindAllTracksWithReleaseDateFilter;

    @InjectSql("/sql/track/findAllTracksWithReleaseDateFrom.sql")
    private String sqlFindAllTracksWithReleaseDateFrom;

    @InjectSql("/sql/track/findAllTracksWithReleaseDateTo.sql")
    private String sqlFindAllTracksWithReleaseDateTo;

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
