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
        if(sportEntity.getSport_id() != null) {
            return sportRepository.findById(sportEntity.getSport_id()).map(existingSport -> {
                existingSport.setSport_name(sportEntity.getSport_name());
                existingSport.setLeague(sportEntity.getLeague());
                existingSport.setLogo_url(sportEntity.getLogo_url());
                return sportRepository.save(existingSport);
            }).orElseThrow(() -> new RuntimeException("Sport not found with id: " + sportEntity.getSport_id()));
        } else {
            return sportRepository.save(sportEntity);
        }
    }

    @Override
    public List<SportEntity> findAll() {
        return StreamSupport.stream(sportRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    @Override
    public Optional<SportEntity> findOne(Long sport_id) {
        return sportRepository.findById(sport_id);
    }

    @Override
    public boolean isExists(Long sport_id) {
        return sportRepository.existsById(sport_id);
    }

    @Override
    public SportEntity partialUpdate(Long sport_id, SportEntity sportEntity) {
        sportEntity.setSport_id(sport_id);
        
        return sportRepository.findById(sport_id).map(existingSport -> {
            Optional.ofNullable(sportEntity.getSport_name()).ifPresent(existingSport::setSport_name);
            Optional.ofNullable(sportEntity.getLeague()).ifPresent(existingSport::setLeague);
            Optional.ofNullable(sportEntity.getLogo_url()).ifPresent(existingSport::setLogo_url);
            return sportRepository.save(existingSport);
        }).orElseThrow(() -> new RuntimeException("Sport does not exist"));
    }


}
