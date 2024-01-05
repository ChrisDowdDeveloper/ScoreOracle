package com.chrisdowd.scoreoracle.mappers.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.chrisdowd.scoreoracle.domain.dto.UserDto;
import com.chrisdowd.scoreoracle.domain.entities.UserEntity;
import com.chrisdowd.scoreoracle.mappers.Mapper;

/* 
  * Defines class named 'UserMapperImpl' that implements Mapper interface
  * Handles object mapping between UserEntity and UserDto
  * Important for reducing risk of exposing sensitive information to a user.
*/
@Component
public class UserMapperImpl implements Mapper<UserEntity, UserDto> {
    
    private ModelMapper modelMapper;

    public UserMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public UserDto mapTo(UserEntity userEntity) {
        return modelMapper.map(userEntity, UserDto.class);
    }

    @Override
    public UserEntity mapFrom(UserDto userDto) {
        return modelMapper.map(userDto, UserEntity.class);
    }

}
