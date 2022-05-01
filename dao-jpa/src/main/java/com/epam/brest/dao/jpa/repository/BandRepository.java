package com.epam.brest.dao.jpa.repository;

import com.epam.brest.dao.jpa.entity.BandEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BandRepository extends JpaRepository<BandEntity, Integer> {
    @Modifying
    Integer deleteByBandId(Integer bandId);

    @Modifying
    @Query(value="ALTER TABLE band ALTER COLUMN band_id RESTART WITH 1", nativeQuery=true)
    void resetStartBandId();

    @Modifying
    @Query(value = "insert into band (band_id,band_name, band_details) VALUES(:id,:name,:details)", nativeQuery = true)
    void saveWithId(@Param("id") Integer id, @Param("name") String name, @Param("details") String details);

}
