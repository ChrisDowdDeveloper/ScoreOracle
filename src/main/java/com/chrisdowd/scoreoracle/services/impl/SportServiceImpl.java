package com.chrisdowd.scoreoracle.services.impl;

import org.springframework.stereotype.Service;

import com.chrisdowd.scoreoracle.domain.entities.SportEntity;
import com.chrisdowd.scoreoracle.repositories.SportRespository;
import com.chrisdowd.scoreoracle.services.SportService;

@Service
public class SportServiceImpl implements SportService{
    
    private SportRespository sportRespository;

    public SportServiceImpl(SportRespository sportRespository) {
        this.sportRespository = sportRespository;
    }

    @Override
    public SportEntity save(SportEntity sportEntity) {
        return sportRespository.save(sportEntity);
    }
}
