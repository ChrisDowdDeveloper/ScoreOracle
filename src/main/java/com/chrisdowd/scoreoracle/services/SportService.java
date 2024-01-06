package com.chrisdowd.scoreoracle.services;

import java.util.List;
import java.util.Optional;

import com.chrisdowd.scoreoracle.domain.entities.SportEntity;

public interface SportService {

    SportEntity save(SportEntity sportEntity);

    List<SportEntity> findAll();

    Optional<SportEntity> findOne(Long sport_id);

    boolean isExists(Long sport_id);

    SportEntity partialUpdate(Long sport_id, SportEntity sportEntity);

    void delete(Long sport_id);

}
