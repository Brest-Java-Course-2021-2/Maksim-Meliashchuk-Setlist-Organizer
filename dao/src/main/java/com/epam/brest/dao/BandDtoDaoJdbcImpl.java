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
public class BandDtoDaoJdbcImpl implements BandDtoDao{

    private final Logger logger = LogManager.getLogger(BandDtoDaoJdbcImpl.class);

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Value("${SQL_FIND_ALL_WITH_COUNT_TRACK}")
    private String sqlFindAllWithCountTrack;

    public BandDtoDaoJdbcImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<BandDto> findAllWithCountTrack() {
        logger.debug("Start: findAllWithCountTrack(");
        return namedParameterJdbcTemplate.query(sqlFindAllWithCountTrack,
                BeanPropertyRowMapper.newInstance(BandDto.class));
    }

}
