package com.chrisdowd.scoreoracle.services.impl;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Override
    public Page<TeamEntity> findAll(Pageable pageable) {
        return teamRepository.findAll(pageable);
    }

    @Override
    public Optional<TeamEntity> findOne(Long teamId) {
        return teamRepository.findById(teamId);
    }
    
    @Override
    public TeamEntity partialUpdate(Long teamId, TeamEntity teamEntity) {
        teamEntity.setTeamId(teamId);

        return teamRepository.findById(teamId).map(existingTeam -> {
            Optional.ofNullable(teamEntity.getTeamCity()).ifPresent(existingTeam::setTeamCity);
            Optional.ofNullable(teamEntity.getTeamName()).ifPresent(existingTeam::setTeamName);
            Optional.ofNullable(teamEntity.getLogoUrl()).ifPresent(existingTeam::setLogoUrl);
            return teamRepository.save(existingTeam);
        }).orElseThrow(() -> new RuntimeException("Team does not exist"));
    }

    @Override
    public void deleteTeam(Long teamId) {
        teamRepository.deleteById(teamId);
    }

}
