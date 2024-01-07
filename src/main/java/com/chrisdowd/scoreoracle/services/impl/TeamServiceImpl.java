package com.chrisdowd.scoreoracle.services.impl;

import org.springframework.stereotype.Service;

import com.chrisdowd.scoreoracle.domain.entities.TeamEntity;
import com.chrisdowd.scoreoracle.repositories.TeamRepository;
import com.chrisdowd.scoreoracle.services.TeamService;

@Service
public class TeamServiceImpl implements TeamService{
    
    private TeamRepository teamRepository;

    public TeamServiceImpl(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    @Override
    public TeamEntity save(TeamEntity teamEntity) {
        return teamRepository.save(teamEntity);
    }

    @Override
    public boolean isExists(Long teamId) {
        return teamRepository.existsById(teamId);
    }

}
