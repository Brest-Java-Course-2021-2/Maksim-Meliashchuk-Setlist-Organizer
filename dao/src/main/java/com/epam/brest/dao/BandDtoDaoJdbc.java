package com.epam.brest.dao;
import com.epam.brest.model.dto.BandDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 *  Band DTO DAO implementation.
 */
@Component
public class BandDtoDaoJdbc implements BandDtoDao{

    private final Logger logger = LogManager.getLogger(BandDtoDaoJdbc.class);

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Value("${findAllWithCountTrackSql}")
    private String findAllWithCountTrackSql;

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
