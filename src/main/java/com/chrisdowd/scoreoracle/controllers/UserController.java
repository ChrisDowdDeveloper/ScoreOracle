package com.chrisdowd.scoreoracle.controllers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

import com.chrisdowd.scoreoracle.domain.dto.UserDto;
import com.chrisdowd.scoreoracle.domain.entities.UserEntity;
import com.chrisdowd.scoreoracle.mappers.Mapper;
import com.chrisdowd.scoreoracle.services.UserService;

@RestController
public class UserController {
    
    private UserService userService;

    private Mapper<UserEntity, UserDto> userMapper;
    
    public UserController(UserService userService, Mapper<UserEntity, UserDto> userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @PostMapping("/users")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        UserEntity userEntity = userMapper.mapFrom(userDto);
        UserEntity savedUserEntity = userService.save(userEntity);
        return new ResponseEntity<>(userMapper.mapTo(savedUserEntity), HttpStatus.CREATED);
    }

    @GetMapping("/users")
    public List<UserDto> listAllUsers() {
        List<UserEntity> users = userService.findAll();
        return users.stream().map(userMapper::mapTo).collect(Collectors.toList());
    }
 
    @GetMapping("/users/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable("id") Long id) {
        Optional<UserEntity> foundUser = userService.findOne(id);
        return foundUser.map(userEntity -> {
            UserDto userDto = userMapper.mapTo(userEntity);
            return new ResponseEntity<>(userDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @PutMapping("/users/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable("id") Long id, @RequestBody UserDto userDto) {
        
        if(!userService.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        userDto.setUserId(id);
        UserEntity userEntity = userMapper.mapFrom(userDto);
        UserEntity savedUserEntity = userService.save(userEntity);
        return new ResponseEntity<>(userMapper.mapTo(savedUserEntity), HttpStatus.OK);
    }

    @PatchMapping("/users/{id}")
    public ResponseEntity<UserDto> partialUpdate(@PathVariable("id") Long id, @RequestBody UserDto userDto) {
        
        if(!userService.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        UserEntity userEntity = userMapper.mapFrom(userDto);
        UserEntity savedUserEntity = userService.partialUpdate(id, userEntity);
        return new ResponseEntity<>(userMapper.mapTo(savedUserEntity), HttpStatus.OK);
    }


    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) {
        userService.delete(id);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }
}
