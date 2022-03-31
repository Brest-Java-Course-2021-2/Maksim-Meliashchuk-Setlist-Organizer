package com.epam.brest.dao.jpa.repository;

import com.epam.brest.dao.jpa.entity.BandEntity;
import org.springframework.data.repository.CrudRepository;

public interface BandRepository extends CrudRepository<BandEntity, Integer> {
}
