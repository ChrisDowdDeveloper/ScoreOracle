package com.chrisdowd.scoreoracle.controllers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    @GetMapping("/sports")
    public List<SportDto> listSports() {
        List<SportEntity> sports = sportService.findAll();
        return sports.stream().map(sportMapper::mapTo).collect(Collectors.toList());
    }

    @GetMapping("/sports/{sport_id}")
    public ResponseEntity<SportDto> getSportById(@PathVariable("sport_id") Long sport_id) {
        Optional<SportEntity> foundSport = sportService.findOne(sport_id);
        return foundSport.map(sportEntity -> {
            SportDto sportDto = sportMapper.mapTo(sportEntity);
            return new ResponseEntity<>(sportDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/sports/{sport_id}")
    public ResponseEntity<SportDto> updateSport(@PathVariable("sport_id") Long sport_id, @RequestBody SportDto sportDto) {

        if(!sportService.isExists(sport_id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        sportDto.setSport_id(sport_id);
        SportEntity sportEntity = sportMapper.mapFrom(sportDto);
        SportEntity savedSportEntity = sportService.save(sportEntity);
        return new ResponseEntity<>(sportMapper.mapTo(savedSportEntity), HttpStatus.OK);
    }

    @PatchMapping("/sports/{sport_id}")
    public ResponseEntity<SportDto> partialUpdate(@PathVariable("sport_id") Long sport_id, @RequestBody SportDto sportDto) {

        if(!sportService.isExists(sport_id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        SportEntity sportEntity = sportMapper.mapFrom(sportDto);
        SportEntity savedSportEntity = sportService.partialUpdate(sport_id, sportEntity);
        return new ResponseEntity<>(sportMapper.mapTo(savedSportEntity), HttpStatus.OK);
    }

}
