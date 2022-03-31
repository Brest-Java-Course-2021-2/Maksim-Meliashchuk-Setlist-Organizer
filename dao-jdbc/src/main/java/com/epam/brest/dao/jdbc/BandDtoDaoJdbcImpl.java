package com.epam.brest.dao.jdbc;

import com.epam.brest.dao.BandDtoDao;
import com.epam.brest.dao.annotation.InjectSql;
import com.epam.brest.model.BandDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *  Band DTO DAO implementation.
 */
@Repository
@ComponentScan("com.epam.brest.dao.annotation")
public class BandDtoDaoJdbcImpl implements BandDtoDao {

    private final Logger logger = LogManager.getLogger(BandDtoDaoJdbcImpl.class);

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @InjectSql("/sql/band/findAllWithCountTrack.sql")
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
