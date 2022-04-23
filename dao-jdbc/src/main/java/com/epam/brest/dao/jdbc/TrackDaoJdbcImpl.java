package com.epam.brest.dao.jdbc;

import com.epam.brest.dao.TrackDao;
import com.epam.brest.dao.annotation.InjectSql;
import com.epam.brest.model.Track;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@ComponentScan("com.epam.brest.dao.annotation")
public class TrackDaoJdbcImpl implements TrackDao {

    private final Logger logger = LogManager.getLogger(TrackDaoJdbcImpl.class);

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final RowMapper<Track> trackRowMapper = BeanPropertyRowMapper.newInstance(Track.class);

    @InjectSql("/sql/track/allTracks.sql")
    private String sqlAllTracks;

    @InjectSql("/sql/track/selectCountFromTrack.sql")
    private String sqlSelectCountFromTrack;

    @InjectSql("/sql/track/deleteTrackById.sql")
    private String sqlDeleteTrackById;

    @InjectSql("/sql/track/trackById.sql")
    private String sqlTrackById;

    @InjectSql("/sql/track/updateTrackById.sql")
    private String sqlUpdateTrackById;

    @InjectSql("/sql/track/createTrack.sql")
    private String sqlCreateTrack;

    public TrackDaoJdbcImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<Track> findAll() {
        return namedParameterJdbcTemplate.query(sqlAllTracks, trackRowMapper);
    }

    @Override
    public Track getTrackById(Integer trackId) {
        logger.debug("Get track by id = {}", trackId);
        SqlParameterSource sqlParameterSource =
                new MapSqlParameterSource("trackId", trackId);
        return namedParameterJdbcTemplate.queryForObject(sqlTrackById, sqlParameterSource, trackRowMapper);
    }

    @Override
    public Integer create(Track track) {
        logger.debug("Start: create({})", track);
        SqlParameterSource sqlParameterSource =
                new MapSqlParameterSource("trackName", track.getTrackName())
                        .addValue("trackDetails", track.getTrackDetails())
                        .addValue("trackTempo", track.getTrackTempo())
                        .addValue("trackDuration", track.getTrackDuration())
                        .addValue("trackLink", track.getTrackLink())
                        .addValue("trackReleaseDate", track.getTrackReleaseDate())
                        .addValue("trackBandId", track.getTrackBandId());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sqlCreateTrack, sqlParameterSource, keyHolder, new String[] {"track_id"});
        return (Integer) keyHolder.getKey();
    }

    @Override
    public Integer update(Track track) {
        logger.debug("Update track: {}", track);
        SqlParameterSource sqlParameterSource =
                new MapSqlParameterSource("trackName", track.getTrackName())
                        .addValue("trackId", track.getTrackId())
                        .addValue("trackDetails", track.getTrackDetails())
                        .addValue("trackTempo", track.getTrackTempo())
                        .addValue("trackDuration", track.getTrackDuration())
                        .addValue("trackLink", track.getTrackLink())
                        .addValue("trackReleaseDate", track.getTrackReleaseDate())
                        .addValue("trackBandId", track.getTrackBandId());
        return namedParameterJdbcTemplate.update(sqlUpdateTrackById, sqlParameterSource);
    }

    @Override
    public Integer delete(Integer trackId) {
        logger.debug("Delete track: {}", trackId);
        SqlParameterSource sqlParameterSource =
                new MapSqlParameterSource("trackId", trackId);
        return namedParameterJdbcTemplate.update(sqlDeleteTrackById, sqlParameterSource);
    }

    @Override
    public Integer count() {
        logger.debug("Track count()");
        return namedParameterJdbcTemplate
                .queryForObject(sqlSelectCountFromTrack, new MapSqlParameterSource(), Integer.class);
    }

}
