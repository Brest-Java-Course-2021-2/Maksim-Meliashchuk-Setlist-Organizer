package com.epam.brest.dao;
import com.epam.brest.model.dto.BandDto;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 *  Band DTO DAO implementation.
 */
@Component
public class BandDtoDaoJdbc implements BandDtoDao{

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

   private String findAllWithCountTrackSql = "SELECT band_id AS bandId, band_name AS bandName, " +
                                            "count(track.track_band_id) AS CountTrack " +
                                            "FROM band LEFT JOIN track ON band.band_id=track.track_band_id " +
                                            "GROUP BY band_id, band_name";

    public BandDtoDaoJdbc(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<BandDto> findAllWithCountTrack() {
        return namedParameterJdbcTemplate.query(findAllWithCountTrackSql,
                BeanPropertyRowMapper.newInstance(BandDto.class));
    }

}
