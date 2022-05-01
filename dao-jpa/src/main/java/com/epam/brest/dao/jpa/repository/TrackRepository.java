package com.epam.brest.dao.jpa.repository;

import com.epam.brest.dao.jpa.entity.TrackEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface TrackRepository extends JpaRepository<TrackEntity, Integer> {

    List<TrackEntity> findByBand_BandIdEquals(Integer bandId);

    List<TrackEntity> findByTrackReleaseDateBetween(LocalDate trackReleaseDateStart, LocalDate trackReleaseDateEnd);

    List<TrackEntity> findByTrackReleaseDateBefore(LocalDate trackReleaseDate);

    List<TrackEntity> findByTrackReleaseDateAfter(LocalDate trackReleaseDate);

    @Modifying
    Integer deleteByTrackId(Integer trackId);

    @Modifying
    @Query(value="ALTER TABLE track ALTER COLUMN track_id RESTART WITH 1", nativeQuery=true)
    void resetStartTrackId();

}
