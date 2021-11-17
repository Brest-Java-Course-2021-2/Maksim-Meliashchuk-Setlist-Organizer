package com.epam.brest.dao;

import com.epam.brest.dao.exception.NotUniqueException;
import com.epam.brest.model.Band;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class BandDaoJDBCImpl implements BandDao{

    private final Logger logger = LogManager.getLogger(BandDaoJDBCImpl.class);

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    public static final String SELECT_COUNT_FROM_BAND = "SELECT COUNT(*) FROM band";
    private final String SQL_ALL_BANDS = "SELECT * FROM band";
    private final String SQL_CREATE_BAND = "INSERT INTO band(band_name, band_details) values(:bandName, :bandDetails)";
    private final String SQL_CHECK_BAND =" SELECT * FROM band WHERE band_name = :bandName";
    private final String SQL_BAND_BY_ID = "SELECT* FROM band WHERE band_id = :bandId";
    private final String SQL_UPDATE_BAND_NAME = "UPDATE band set band_name = :bandName WHERE band_id = :bandId";
    private final String SQL_DELETE_BAND_BY_ID = "DELETE FROM band WHERE band_id = :bandId";

    @Deprecated
    public BandDaoJDBCImpl(DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public BandDaoJDBCImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public Band getBandById(Integer bandId) {
        logger.debug("Get band by id = {}", bandId);
        SqlParameterSource sqlParameterSource =
                new MapSqlParameterSource("bandId", bandId);
        return namedParameterJdbcTemplate.queryForObject(SQL_BAND_BY_ID, sqlParameterSource, new BandRowMapper());
    }

    @Override
    public List<Band> findAll() {
        logger.debug("Start: findAll()");
        return namedParameterJdbcTemplate.query(SQL_ALL_BANDS, new BandRowMapper());
    }

    @Override
    public Integer create(Band band) {
        logger.debug("Start: create({})", band);
        if (!isBandNameUnique(band)) {
            logger.warn("Band {} already exists in DB!", band.getBandName());
            throw new NotUniqueException("Band '" + band.getBandName() +"' already exists in Data Base!");
        }

        SqlParameterSource sqlParameterSource =
                new MapSqlParameterSource("bandName", band.getBandName().toUpperCase())
                        .addValue("bandDetails", band.getBandDetails());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(SQL_CREATE_BAND, sqlParameterSource, keyHolder);
        return (Integer) keyHolder.getKey();
    }

    @Override
    public Integer update(Band band) {
        logger.debug("Update band: {}", band);
        SqlParameterSource sqlParameterSource =
                new MapSqlParameterSource("bandName", band.getBandName())
                        .addValue("bandId", band.getBandId());
        return namedParameterJdbcTemplate.update(SQL_UPDATE_BAND_NAME, sqlParameterSource);
    }

    @Override
    public Integer delete(Integer bandId) {
        logger.debug("Delete band: {}", bandId);
        SqlParameterSource sqlParameterSource =
                new MapSqlParameterSource("bandId", bandId);
        return namedParameterJdbcTemplate.update(SQL_DELETE_BAND_BY_ID, sqlParameterSource);
    }

    @Override
    public Integer count() {
        logger.debug("count()");
        return namedParameterJdbcTemplate
                .queryForObject(SELECT_COUNT_FROM_BAND, new MapSqlParameterSource(), Integer.class);
    }

    private class BandRowMapper implements RowMapper<Band> {


        @Override
        public Band mapRow(ResultSet resultSet, int i) throws SQLException {
            logger.debug("Start: mapRow");
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
        return  namedParameterJdbcTemplate.query(SQL_CHECK_BAND, sqlParameterSource, new BandRowMapper()).isEmpty();

    }

}
