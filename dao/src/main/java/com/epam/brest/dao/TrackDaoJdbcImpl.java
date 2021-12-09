package com.epam.brest.dao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import com.epam.brest.model.Track;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.util.List;

public class TrackDaoJdbcImpl implements TrackDao{

    private final Logger logger = LogManager.getLogger(TrackDaoJdbcImpl.class);

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final RowMapper<Track> trackRowMapper = BeanPropertyRowMapper.newInstance(Track.class);

    @Value("${SQL_ALL_TRACKS}")
    private String sqlAllTracks;

    @Value("${SQL_SELECT_COUNT_FROM_TRACK}")
    private String selectCountFromTrack;

    @Value("${SQL_DELETE_TRACK_BY_ID}")
    private String sqlDeleteTrackById;

    @Value("${SQL_TRACK_BY_ID}")
    private String sqlTrackById = "SELECT * FROM track WHERE track_id = :trackId";

    @Value("${SQL_UPDATE_TRACK_BY_ID}")
    private String sqlUpdateTrackById;

    @Value("${SQL_CREATE_TRACK}")
    private String sqlCreateTrack = "INSERT INTO track(track_name, track_details, track_tempo, track_duration, " +
            "track_link, track_release_date, track_band_id) " +
            "values(:trackName, :trackDetails, :trackTempo, :trackDuration, :trackLink, :trackReleaseDate, :trackBandId)";

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
        namedParameterJdbcTemplate.update(sqlCreateTrack, sqlParameterSource, keyHolder);
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
                .queryForObject(selectCountFromTrack, new MapSqlParameterSource(), Integer.class);
    }

}