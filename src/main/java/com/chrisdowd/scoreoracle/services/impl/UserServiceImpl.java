package com.chrisdowd.scoreoracle.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Service;

import com.chrisdowd.scoreoracle.domain.entities.UserEntity;
import com.chrisdowd.scoreoracle.repositories.UserRepository;
import com.chrisdowd.scoreoracle.services.UserService;

@Service
public class UserServiceImpl implements UserService{
    
    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserEntity save(UserEntity userEntity) {
        if(userEntity.getUser_id() != null) {
            return userRepository.findById(userEntity.getUser_id()).map(existingUser -> {
                existingUser.setUsername(userEntity.getUsername());
                existingUser.setEmail(userEntity.getEmail());
                existingUser.setPassword(userEntity.getPassword());
                return userRepository.save(existingUser);
            }).orElseThrow(() -> new RuntimeException("User not found with id: " + userEntity.getUser_id()));
        } else {
            return userRepository.save(userEntity);
        }
    }

    @Override
    public List<UserEntity> findAll() {
        return StreamSupport.stream(userRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    @Override
    public Optional<UserEntity>findOne(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public boolean isExists(Long id) {
        return userRepository.existsById(id);
    }

    @Override
    public UserEntity partialUpdate(Long id, UserEntity userEntity) {
        userEntity.setUser_id(id);
        userEntity.setCreatedAt(userEntity.getCreatedAt());

        return userRepository.findById(id).map(existingUser -> {
            Optional.ofNullable(userEntity.getUsername()).ifPresent(existingUser::setUsername);
            Optional.ofNullable(userEntity.getEmail()).ifPresent(existingUser::setEmail);
            Optional.ofNullable(userEntity.getPassword()).ifPresent(existingUser::setPassword);
            return userRepository.save(existingUser);
        }).orElseThrow(() -> new RuntimeException("User does not exist"));
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

}
