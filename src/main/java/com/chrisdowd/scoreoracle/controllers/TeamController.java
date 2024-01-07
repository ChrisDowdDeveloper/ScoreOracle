package com.chrisdowd.scoreoracle.controllers;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    @GetMapping("/teams")
    public Page<TeamDto> listTeams(Pageable pageable) {
        Page<TeamEntity> teams = teamService.findAll(pageable);
        return teams.map(teamMapper::mapTo);
    }

    @GetMapping(path = "/teams/{teamId}")
    public ResponseEntity<TeamDto> getTeam(@PathVariable("teamId") Long teamId) {
        Optional<TeamEntity> foundTeam = teamService.findOne(teamId);
        return foundTeam.map(teamEntity -> {
            TeamDto teamDto = teamMapper.mapTo(teamEntity);
            return new ResponseEntity<>(teamDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping(path = "/teams/{teamId}")
    public ResponseEntity<TeamDto> fullUpdateArtist(@PathVariable("teamId") Long teamId, @RequestBody TeamDto teamDto) {

        if(!teamService.isExists(teamId)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        teamDto.setTeamId(teamId);
        TeamEntity teamEntity = teamMapper.mapFrom(teamDto);
        TeamEntity savedTeamEntity = teamService.save(teamEntity);
        return new ResponseEntity<>(teamMapper.mapTo(savedTeamEntity), HttpStatus.OK);
    }

    @PatchMapping(path = "/teams/{teamId}")
    public ResponseEntity<TeamDto> partialUpdate(@PathVariable("teamId") Long teamId, @RequestBody TeamDto teamDto) {
        
        if(!teamService.isExists(teamId)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        TeamEntity teamEntity = teamMapper.mapFrom(teamDto);
        TeamEntity updatedTeamEntity = teamService.partialUpdate(teamId, teamEntity);
        return new ResponseEntity<>(teamMapper.mapTo(updatedTeamEntity), HttpStatus.OK);
    }

    @DeleteMapping(path = "/teams/{teamId}")
    public ResponseEntity<Void> delete(@PathVariable("teamId") Long teamId) {
        teamService.deleteTeam(teamId);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

}
