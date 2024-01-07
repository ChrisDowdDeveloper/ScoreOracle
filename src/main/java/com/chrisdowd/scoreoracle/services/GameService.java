package com.chrisdowd.scoreoracle.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.chrisdowd.scoreoracle.domain.entities.GameEntity;

public interface GameService {

    GameEntity save(GameEntity gameEntity);

    Page<GameEntity> findAll(Pageable pageable);
    
}
