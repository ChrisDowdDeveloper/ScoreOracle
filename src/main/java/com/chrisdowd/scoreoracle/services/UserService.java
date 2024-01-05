package com.chrisdowd.scoreoracle.services;

import java.util.List;
import java.util.Optional;

import com.chrisdowd.scoreoracle.domain.entities.UserEntity;

public interface UserService {

    // Creating and Fully updating User Entities in the database
    UserEntity save(UserEntity userEntity);

    // Retrieves a list of all UserEntities in the database
    List<UserEntity> findAll();

    // Attempts to find a UserEntity in the datbase based on the id
    // Returns optional, indicating a user might not exist
    Optional<UserEntity> findOne(Long id);

    // Checks if a user with the id exists in the database
    boolean isExists(Long id);

    // Updates only selected fields of a UserEntity, identified by the id
    UserEntity partialUpdate(Long id, UserEntity userEntity);

    // Removes a UserEntity from the database based on the id
    void delete(Long id);

}
