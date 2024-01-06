package com.chrisdowd.scoreoracle.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.chrisdowd.scoreoracle.domain.dto.SportDto;
import com.chrisdowd.scoreoracle.domain.entities.SportEntity;
import com.chrisdowd.scoreoracle.mappers.Mapper;
import com.chrisdowd.scoreoracle.services.SportService;

@RestController
public class SportController {
    
    private SportService sportService;

    private Mapper<SportEntity, SportDto> sportMapper;

    public SportController(SportService sportService, Mapper<SportEntity, SportDto> sportMapper) {
        this.sportService = sportService;
        this.sportMapper = sportMapper;
    }

    @PostMapping("/sports")
    public ResponseEntity<SportDto> createSport(@RequestBody SportDto sportDto) {
        SportEntity sportEntity = sportMapper.mapFrom(sportDto);
        SportEntity savedSportEntity = sportService.save(sportEntity);
        return new ResponseEntity<>(sportMapper.mapTo(savedSportEntity), HttpStatus.CREATED);
    }
}
