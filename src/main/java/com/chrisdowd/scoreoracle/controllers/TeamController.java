package com.chrisdowd.scoreoracle.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.chrisdowd.scoreoracle.domain.dto.TeamDto;
import com.chrisdowd.scoreoracle.domain.entities.TeamEntity;
import com.chrisdowd.scoreoracle.mappers.Mapper;
import com.chrisdowd.scoreoracle.services.TeamService;

@RestController
public class TeamController {
    
    private TeamService teamService;

    private Mapper<TeamEntity, TeamDto> teamMapper;
    
    public TeamController(TeamService teamService, Mapper<TeamEntity, TeamDto> teamMapper) {
        this.teamService = teamService;
        this.teamMapper = teamMapper;
    }

    @PostMapping("/teams")
    public ResponseEntity<TeamDto> createUpdateSong(@RequestBody TeamDto teamDto) {

        TeamEntity teamEntity = teamMapper.mapFrom(teamDto);
        TeamEntity savedTeamEntity = teamService.save(teamEntity);
        return new ResponseEntity<>(teamMapper.mapTo(savedTeamEntity), HttpStatus.CREATED);

    }

}
