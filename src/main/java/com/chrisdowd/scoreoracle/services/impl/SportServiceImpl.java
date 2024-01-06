package com.chrisdowd.scoreoracle.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Service;

import com.chrisdowd.scoreoracle.domain.entities.SportEntity;
import com.chrisdowd.scoreoracle.repositories.SportRepository;
import com.chrisdowd.scoreoracle.services.SportService;

@Service
public class SportServiceImpl implements SportService{
    
    private SportRepository sportRepository;

    public SportServiceImpl(SportRepository sportRepository) {
        this.sportRepository = sportRepository;
    }

    @Override
    public SportEntity save(SportEntity sportEntity) {
        if(sportEntity.getSportId() != null) {
            return sportRepository.findById(sportEntity.getSportId()).map(existingSport -> {
                existingSport.setSportName(sportEntity.getSportName());
                existingSport.setLeague(sportEntity.getLeague());
                existingSport.setLogoUrl(sportEntity.getLogoUrl());
                return sportRepository.save(existingSport);
            }).orElseThrow(() -> new RuntimeException("Sport not found with id: " + sportEntity.getSportId()));
        } else {
            return sportRepository.save(sportEntity);
        }
    }

    @Override
    public List<SportEntity> findAll() {
        return StreamSupport.stream(sportRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    @Override
    public Optional<SportEntity> findOne(Long sportId) {
        return sportRepository.findById(sportId);
    }

    @Override
    public boolean isExists(Long sportId) {
        return sportRepository.existsById(sportId);
    }

    @Override
    public SportEntity partialUpdate(Long sportId, SportEntity sportEntity) {
        sportEntity.setSportId(sportId);
        
        return sportRepository.findById(sportId).map(existingSport -> {
            Optional.ofNullable(sportEntity.getSportName()).ifPresent(existingSport::setSportName);
            Optional.ofNullable(sportEntity.getLeague()).ifPresent(existingSport::setLeague);
            Optional.ofNullable(sportEntity.getLogoUrl()).ifPresent(existingSport::setLogoUrl);
            return sportRepository.save(existingSport);
        }).orElseThrow(() -> new RuntimeException("Sport does not exist"));
    }

    @Override
    public void delete(Long sportId) {
        sportRepository.deleteById(sportId);
    }


}
