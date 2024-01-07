package com.chrisdowd.scoreoracle.services;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import com.chrisdowd.scoreoracle.domain.entities.TeamEntity;

public interface TeamService {
    
    boolean isExists(Long teamId);

    TeamEntity save(TeamEntity teamEntity);

    Page<TeamEntity> findAll(Pageable pageable);

    Optional<TeamEntity> findOne(Long teamId);

    void deleteTeam(Long teamId);

    TeamEntity partialUpdate(Long teamId, TeamEntity teamEntity);
    
}
