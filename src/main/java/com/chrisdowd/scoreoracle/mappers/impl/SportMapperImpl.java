package com.chrisdowd.scoreoracle.mappers.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.chrisdowd.scoreoracle.domain.dto.SportDto;
import com.chrisdowd.scoreoracle.domain.entities.SportEntity;
import com.chrisdowd.scoreoracle.mappers.Mapper;

@Component
public class SportMapperImpl implements Mapper<SportEntity, SportDto> {
    
    private ModelMapper modelMapper;

    public SportMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public SportDto mapTo(SportEntity sportEntity) {
        return modelMapper.map(sportEntity, SportDto.class);
    }

    @Override
    public SportEntity mapFrom(SportDto sportDto) {
        return modelMapper.map(sportDto, SportEntity.class);
    }
}
