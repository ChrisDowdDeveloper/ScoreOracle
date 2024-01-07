package com.chrisdowd.scoreoracle.services;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.chrisdowd.scoreoracle.domain.entities.GameEntity;

public interface GameService {

    GameEntity save(GameEntity gameEntity);

    Page<GameEntity> findAll(Pageable pageable);

    Optional<GameEntity> findOne(Long gameId);

    boolean isExists(Long gameId);

    GameEntity partialUpdate(Long gameId, GameEntity gameEntity);

    void deleteGame(Long gameId);
    
}
