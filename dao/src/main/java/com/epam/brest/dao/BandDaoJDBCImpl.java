package com.epam.brest.dao;

import com.epam.brest.model.Band;
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

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final String SQL_ALL_BANDS = "SELECT * FROM band";
    private final String SQL_CREATE_BAND = "INSERT INTO band(band_name) values(:bandName)";
    private final String SQL_CHECK_BAND =" SELECT * FROM band WHERE band_name = :bandName";

    public BandDaoJDBCImpl(DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public List<Band> findAll() {
        return namedParameterJdbcTemplate.query(SQL_ALL_BANDS, new BandRowMapper());
    }

    @Override
    public Integer create(Band band) {

        if (!isBandNameUnique(band)) {
            throw new NotUniqueException("Band already exists in DB");
        }

        SqlParameterSource sqlParameterSource =
                new MapSqlParameterSource("bandName", band.getBandName().toUpperCase());
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

    private static class BandRowMapper implements RowMapper<Band> {

        @Override
        public Band mapRow(ResultSet resultSet, int i) throws SQLException {
            Band band = new Band();
            band.setBandId(resultSet.getInt("band_id"));
            band.setBandName(resultSet.getString("band_name"));
            band.setBandDetails(resultSet.getString("band_details"));
            return band;
        }
    }

    private boolean isBandNameUnique(Band band) {
        SqlParameterSource sqlParameterSource =
                new MapSqlParameterSource("bandName", band.getBandName().toUpperCase());
        return  namedParameterJdbcTemplate.query(SQL_CHECK_BAND, sqlParameterSource, new BandRowMapper()).isEmpty();

    }

}
