package com.chrisdowd.scoreoracle.repositories;

import org.springframework.data.repository.CrudRepository;

import com.chrisdowd.scoreoracle.domain.entities.UserEntity;

public interface UserRepository extends CrudRepository<UserEntity, Long>{

}
