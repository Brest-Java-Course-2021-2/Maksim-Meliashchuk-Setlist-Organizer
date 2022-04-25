package com.epam.brest.dao.jdbc;

import com.epam.brest.dao.BandDao;
import com.epam.brest.dao.annotation.InjectSql;
import com.epam.brest.dao.exception.NotUniqueException;
import com.epam.brest.model.Band;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.ComponentScan;
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
@ComponentScan("com.epam.brest.dao.annotation")
public class BandDaoJdbcImpl implements BandDao {

    private final Logger logger = LogManager.getLogger(BandDaoJdbcImpl.class);

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @InjectSql("/sql/band/selectCountFromBand.sql")
    private String sqlSelectCountFromBand;
    @InjectSql("/sql/band/allBands.sql")
    private String sqlAllBands;
    @InjectSql("/sql/band/createBand.sql")
    private String sqlCreateBand;
    @InjectSql("/sql/band/checkBand.sql")
    private String sqlCheckBand;
    @InjectSql("/sql/band/bandById.sql")
    private String sqlBandById;
    @InjectSql("/sql/band/updateBandById.sql")
    private String sqlUpdateBandById;
    @InjectSql("/sql/band/deleteBandById.sql")
    private String sqlDeleteBandById;
    @InjectSql("/sql/band/deleteAllBands.sql")
    private String sqlDeleteAllBands;
    @InjectSql("/sql/band/resetStartBandId.sql")
    private String sqlResetStartBandId;


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
        namedParameterJdbcTemplate.update(sqlCreateBand, sqlParameterSource, keyHolder, new String[] {"band_id"});
        return (Integer) keyHolder.getKey();
    }

    @Override
    public Integer update(Band band) {
        logger.debug("Update band: {}", band);

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
    public Integer deleteAllBands() {
        logger.debug("Delete all Bands()");
        Integer count = namedParameterJdbcTemplate.getJdbcTemplate().update(sqlDeleteAllBands);
        namedParameterJdbcTemplate.getJdbcTemplate().execute(sqlResetStartBandId);
        return count;
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
