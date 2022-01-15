package com.epam.brest.dao;

import com.epam.brest.dao.exception.NotUniqueException;
import com.epam.brest.model.Band;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class BandDaoJdbcImpl implements BandDao{

    private final Logger logger = LogManager.getLogger(BandDaoJdbcImpl.class);

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Value("${SQL_SELECT_COUNT_FROM_BAND}")
    private String sqlSelectCountFromBand;
    @Value("${SQL_ALL_BANDS}")
    private String sqlAllBands;
    @Value("${SQL_CREATE_BAND}")
    private String sqlCreateBand;
    @Value("${SQL_CHECK_BAND}")
    private String sqlCheckBand;
    @Value("${SQL_BAND_BY_ID}")
    private String sqlBandById;
    @Value("${SQL_UPDATE_BAND_BY_ID}")
    private String sqlUpdateBandById;
    @Value("${SQL_DELETE_BAND_BY_ID}")
    private String sqlDeleteBandById;

    public BandDaoJdbcImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public Band getBandById(Integer bandId) {
        logger.debug("Get band by id = {}", bandId);
        SqlParameterSource sqlParameterSource =
                new MapSqlParameterSource("bandId", bandId);
        return namedParameterJdbcTemplate.queryForObject(sqlBandById, sqlParameterSource, new BandRowMapper());
    }

    @Override
    public List<Band> findAll() {
        logger.debug("Start: findAll()");
        return namedParameterJdbcTemplate.query(sqlAllBands, new BandRowMapper());
    }

    @Override
    public Integer create(Band band) {
        logger.debug("Start: create({})", band);
        if (!isBandNameUnique(band)) {
            logger.warn("Band {} already exists in DB!", band.getBandName().toUpperCase());
            throw new NotUniqueException("Band '" + band.getBandName().toUpperCase() +"' already exists in Data Base!");
        }

        SqlParameterSource sqlParameterSource =
                new MapSqlParameterSource("bandName", band.getBandName().toUpperCase())
                        .addValue("bandDetails", band.getBandDetails());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sqlCreateBand, sqlParameterSource, keyHolder);
        return (Integer) keyHolder.getKey();
    }

    @Override
    public Integer update(Band band) {
        logger.debug("Update band: {}", band);

        if (!isBandNameUnique(band)) {
            logger.warn("Band {} already exists in DB!", band.getBandName().toUpperCase());
            throw new NotUniqueException("Band '" + band.getBandName().toUpperCase() +"' already exists in Data Base!");
        }
        SqlParameterSource sqlParameterSource =
                new MapSqlParameterSource("bandName", band.getBandName().toUpperCase())
                        .addValue("bandId", band.getBandId())
                        .addValue("bandDetails", band.getBandDetails());

        return namedParameterJdbcTemplate.update(sqlUpdateBandById, sqlParameterSource);
    }

    @Override
    public Integer delete(Integer bandId) {
        logger.debug("Delete band: {}", bandId);
        SqlParameterSource sqlParameterSource =
                new MapSqlParameterSource("bandId", bandId);
        return namedParameterJdbcTemplate.update(sqlDeleteBandById, sqlParameterSource);
    }

    @Override
    public Integer count() {
        logger.debug("Band count()");
        return namedParameterJdbcTemplate
                .queryForObject(sqlSelectCountFromBand, new MapSqlParameterSource(), Integer.class);
    }

    private class BandRowMapper implements RowMapper<Band> {
        @Override
        public Band mapRow(ResultSet resultSet, int i) throws SQLException {
            logger.debug("Start: band mapRow");
            Band band = new Band();
            band.setBandId(resultSet.getInt("band_id"));
            band.setBandName(resultSet.getString("band_name"));
            band.setBandDetails(resultSet.getString("band_details"));
            return band;
        }
    }

    private boolean isBandNameUnique(Band band) {
        logger.debug("Start: isBandNameUnique({})", band);
        SqlParameterSource sqlParameterSource =
                new MapSqlParameterSource("bandName", band.getBandName().toUpperCase());
        return  namedParameterJdbcTemplate.query(sqlCheckBand, sqlParameterSource, new BandRowMapper()).isEmpty();

    }

}
