package com.epam.brest.dao.jpa.repository;

import com.epam.brest.dao.jpa.entity.BandEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface BandRepository extends JpaRepository<BandEntity, Integer> {
    @Modifying
    Integer deleteByBandId(Integer bandId);

    @Query("select (count(b) > 0) from BandEntity b where b.bandName = ?1")
    boolean existsByBandNameEquals(String bandName);

    @Modifying
    @Query(value="ALTER TABLE band ALTER COLUMN band_id RESTART WITH 1", nativeQuery=true)
    void resetStartBandId();

}
