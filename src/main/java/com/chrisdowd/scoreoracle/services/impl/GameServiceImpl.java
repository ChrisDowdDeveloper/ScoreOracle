package com.chrisdowd.scoreoracle.services.impl;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Override
    public Page<GameEntity> findAll(Pageable pageable) {
        return gameRepository.findAll(pageable);
    }

    @Override
    public Optional<GameEntity> findOne(Long gameId) {
        return gameRepository.findById(gameId);
    }

    @Override
    public boolean isExists(Long gameId) {
        return gameRepository.existsById(gameId);
    }

    @Override
    public GameEntity partialUpdate(Long gameId, GameEntity gameEntity) {
        gameEntity.setGameId(gameId);

        return gameRepository.findById(gameId).map(existingGame -> {
            Optional.ofNullable(gameEntity.getHomeTeam()).ifPresent(existingGame::setHomeTeam);
            Optional.ofNullable(gameEntity.getAwayTeam()).ifPresent(existingGame::setAwayTeam);
            Optional.ofNullable(gameEntity.getGameDate()).ifPresent(existingGame::setGameDate);
            Optional.ofNullable(gameEntity.getSport()).ifPresent(existingGame::setSport);
            Optional.ofNullable(gameEntity.getHomeTeamScore()).ifPresent(existingGame::setHomeTeamScore);
            Optional.ofNullable(gameEntity.getAwayTeamScore()).ifPresent(existingGame::setAwayTeamScore);
            Optional.ofNullable(gameEntity.getStatus()).ifPresent(existingGame::setStatus);
            return gameRepository.save(existingGame);
        }).orElseThrow(() -> new RuntimeException("Game does not exist"));
    }

    @Override
    public void deleteGame(Long gameId) {
        gameRepository.deleteById(gameId);
    }

}
