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

    private final String SQL_ALL_BANDS = "SELECT * FROM band";
    private final String SQL_CREATE_BAND = "INSERT INTO band(band_name, band_details) values(:bandName, :bandDetails)";
    private final String SQL_CHECK_BAND =" SELECT * FROM band WHERE band_name = :bandName";

    @Deprecated
    public BandDaoJDBCImpl(DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public BandDaoJDBCImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
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
        return null;
    }

    @Override
    public Integer delete(Integer bandId) {
        return null;
    }

    @Override
    public Integer count() {
        logger.debug("count()");
        return namedParameterJdbcTemplate
                .queryForObject("select count(*) from band", new MapSqlParameterSource(), Integer.class);
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
