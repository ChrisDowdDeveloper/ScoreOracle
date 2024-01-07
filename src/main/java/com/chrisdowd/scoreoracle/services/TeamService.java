package com.chrisdowd.scoreoracle.services;

import com.chrisdowd.scoreoracle.domain.entities.TeamEntity;

public interface TeamService {
    
    boolean isExists(Long teamId);

    TeamEntity save(TeamEntity teamEntity);
    
}
