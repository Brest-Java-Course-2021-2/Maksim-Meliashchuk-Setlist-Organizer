package com.epam.brest.dao.jpa.repository;

import com.epam.brest.dao.jpa.entity.TrackEntity;
import org.springframework.data.repository.CrudRepository;

public interface TrackRepository extends CrudRepository<TrackEntity, Integer> {
}
