package com.epam.brest.dao.jpa.repository;

import com.epam.brest.dao.jpa.entity.BandEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface BandRepository extends CrudRepository<BandEntity, Integer> {
    @Modifying
    @Transactional
    Integer deleteByBandId(Integer bandId);

    @Modifying
    @Query(value="ALTER TABLE band ALTER COLUMN band_id RESTART WITH 1", nativeQuery=true)
    void resetStartBandId();

}
