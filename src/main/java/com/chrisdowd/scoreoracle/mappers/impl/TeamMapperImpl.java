package com.chrisdowd.scoreoracle.mappers.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.chrisdowd.scoreoracle.domain.dto.TeamDto;
import com.chrisdowd.scoreoracle.domain.entities.TeamEntity;
import com.chrisdowd.scoreoracle.mappers.Mapper;

@Component
public class TeamMapperImpl implements Mapper<TeamEntity, TeamDto>{
    
    private ModelMapper modelMapper;

    public TeamMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public TeamDto mapTo(TeamEntity teamEntity) {
        return modelMapper.map(teamEntity, TeamDto.class);
    }

    @Override
    public TeamEntity mapFrom(TeamDto teamDto) {
        return modelMapper.map(teamDto, TeamEntity.class);
    }

}
