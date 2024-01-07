package com.chrisdowd.scoreoracle.controllers;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    @GetMapping("/games")
    public Page<GameDto> listGames(Pageable pageable) {
        Page<GameEntity> games = gameService.findAll(pageable);
        return games.map(gameMapper::mapTo);
    }

    @GetMapping("/games/{gameId}")
    public ResponseEntity<GameDto> getGame(@PathVariable("gameId") Long gameId) {
        Optional<GameEntity> foundGame = gameService.findOne(gameId);
        return foundGame.map(gameEntity -> {
            GameDto gameDto = gameMapper.mapTo(gameEntity);
            return new ResponseEntity<>(gameDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/games/{gameId}")
    public ResponseEntity<GameDto> fullUpdateGame(@PathVariable("gameId") Long gameId, @RequestBody GameDto gameDto) {

        if(!gameService.isExists(gameId)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        gameDto.setGameId(gameId);
        GameEntity gameEntity = gameMapper.mapFrom(gameDto);
        GameEntity savedGame = gameService.save(gameEntity);
        return new ResponseEntity<>(gameMapper.mapTo(savedGame), HttpStatus.OK);
    }


}
