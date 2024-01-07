package com.chrisdowd.scoreoracle.services.impl;

import org.springframework.stereotype.Service;

import com.chrisdowd.scoreoracle.domain.entities.GameEntity;
import com.chrisdowd.scoreoracle.repositories.GameRepository;
import com.chrisdowd.scoreoracle.services.GameService;

@Service
public class GameServiceImpl implements GameService {
    
    private GameRepository gameRepository;

    public GameServiceImpl(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @Override
    public GameEntity save(GameEntity gameEntity) {
        return gameRepository.save(gameEntity);
    }

}
