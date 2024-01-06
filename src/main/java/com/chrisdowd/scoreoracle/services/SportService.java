package com.chrisdowd.scoreoracle.services;

import java.util.List;

import com.chrisdowd.scoreoracle.domain.entities.SportEntity;

public interface SportService {

    SportEntity save(SportEntity sportEntity);

    List<SportEntity> findAll();
    
}
