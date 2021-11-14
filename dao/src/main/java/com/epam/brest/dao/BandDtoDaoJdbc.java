package com.epam.brest.dao;
import com.epam.brest.model.dto.BandDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 *  Band DTO DAO implementation.
 */
@Component
public class BandDtoDaoJdbc implements BandDtoDao{

    private final Logger logger = LogManager.getLogger(BandDaoJDBCImpl.class);

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final String findAllWithCountTrackSql = "SELECT band_id AS bandId, band_name AS bandName, band_details AS bandDetails, " +
                                            "count(track.track_band_id) AS CountTrack " +
                                            "FROM band LEFT JOIN track ON band.band_id=track.track_band_id " +
                                            "GROUP BY band_id, band_name, band_details";

    public BandDtoDaoJdbc(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<BandDto> findAllWithCountTrack() {
        logger.debug("Start: findAllWithCountTrack(");
        return namedParameterJdbcTemplate.query(findAllWithCountTrackSql,
                BeanPropertyRowMapper.newInstance(BandDto.class));
    }

}
