package com.chrisdowd.scoreoracle.services;

import com.chrisdowd.scoreoracle.domain.entities.GameEntity;

public interface GameService {

    GameEntity save(GameEntity gameEntity);
    
}
