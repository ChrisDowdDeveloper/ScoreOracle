package com.chrisdowd.scoreoracle.mappers.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.chrisdowd.scoreoracle.domain.dto.GameDto;
import com.chrisdowd.scoreoracle.domain.entities.GameEntity;
import com.chrisdowd.scoreoracle.mappers.Mapper;

@Component
public class GameMapperImpl implements Mapper<GameEntity, GameDto> {
    
    private ModelMapper modelMapper;

    public GameMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public GameDto mapTo(GameEntity gameEntity) {
        return modelMapper.map(gameEntity, GameDto.class);
    }

    @Override
    public GameEntity mapFrom(GameDto gameDto) {
        return modelMapper.map(gameDto, GameEntity.class);
    }
    
}
