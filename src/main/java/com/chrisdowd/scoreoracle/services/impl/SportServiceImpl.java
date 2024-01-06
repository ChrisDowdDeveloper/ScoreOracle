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
    
    private SportRepository sportRespository;

    public SportServiceImpl(SportRepository sportRespository) {
        this.sportRespository = sportRespository;
    }

    @Override
    public SportEntity save(SportEntity sportEntity) {
        return sportRespository.save(sportEntity);
    }

    @Override
    public List<SportEntity> findAll() {
        return StreamSupport.stream(sportRespository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    @Override
    public Optional<SportEntity> findOne(Long sport_id) {
        return sportRespository.findById(sport_id);
    }
}
