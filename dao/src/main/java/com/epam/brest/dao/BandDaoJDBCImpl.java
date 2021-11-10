package com.epam.brest.dao;

import com.epam.brest.model.Band;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class BandDaoJDBCImpl implements BandDao{

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final String SQL_ALL_BANDS="select * from band";

    public BandDaoJDBCImpl(DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public List<Band> findAll() {
        return namedParameterJdbcTemplate.query(SQL_ALL_BANDS, new BandRowMapper());
    }

    @Override
    public Integer create(Band band) {
        return null;
    }

    @Override
    public Integer update(Band band) {
        return null;
    }

    @Override
    public Integer delete(Integer bandId) {
        return null;
    }

    private class BandRowMapper implements RowMapper<Band> {

        @Override
        public Band mapRow(ResultSet resultSet, int i) throws SQLException {
            Band band = new Band();
            band.setBandId(resultSet.getInt("band_id"));
            band.setBandName(resultSet.getString("band_name"));
            band.setBandDetails(resultSet.getString("band_details"));
            return band;
        }
    }

}
