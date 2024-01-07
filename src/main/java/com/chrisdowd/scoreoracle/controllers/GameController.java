package com.chrisdowd.scoreoracle.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.chrisdowd.scoreoracle.domain.dto.GameDto;
import com.chrisdowd.scoreoracle.domain.entities.GameEntity;
import com.chrisdowd.scoreoracle.mappers.Mapper;
import com.chrisdowd.scoreoracle.services.GameService;

@RestController
public class GameController {
    
    private GameService gameService;

    private Mapper<GameEntity, GameDto> gameMapper;

    public GameController(Mapper<GameEntity, GameDto> gameMapper, GameService gameService) {
        this.gameMapper = gameMapper;
        this.gameService = gameService;
    }

    @PostMapping("/games")
    public ResponseEntity<GameDto> createGame(@RequestBody GameDto gameDto) {
        GameEntity gameEntity = gameMapper.mapFrom(gameDto);
        GameEntity savedGame = gameService.save(gameEntity);
        return new ResponseEntity<>(gameMapper.mapTo(savedGame), HttpStatus.CREATED);
    }

}
